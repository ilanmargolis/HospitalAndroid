package com.example.hospital.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.controller.PrescreveCtrl;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Prescreve;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import retrofit2.Call;

public class PrescreverAlterarActivity extends AppCompatActivity {

    private EditText etAlterarDosagem, etAlterarHorario;
    private TextView tvAlterarTerminologia, tvAlterarMedico;
    private CheckBox cbAlterarSuspender;
    private Button btAlterarOk, btAlterarCancelar;
    private Prescreve prescreve;
    private Medico medico;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescrever_alterar);

        etAlterarDosagem = (EditText) findViewById(R.id.etAlterarDosagem);
        etAlterarHorario = (EditText) findViewById(R.id.etAlterarHorario);
        tvAlterarTerminologia = (TextView) findViewById(R.id.tvAlterarTerminologia);
        tvAlterarMedico = (TextView) findViewById(R.id.tvAlterarMedico);
        cbAlterarSuspender = (CheckBox) findViewById(R.id.cbAlterarSuspender);
        btAlterarOk = (Button) findViewById(R.id.btAlterarOk);
        btAlterarCancelar = (Button) findViewById(R.id.btAlterarCancelar);

        preparaDados();

        btAlterarOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                byte dosagem = Byte.parseByte(etAlterarDosagem.getText().toString());

                if (dosagem <= 0) {
                    Toast.makeText(PrescreverAlterarActivity.this, "É necessário informar o código!", Toast.LENGTH_SHORT).show();
                    etAlterarDosagem.requestFocus();
                } else {
                    if (prescreve.getId() == 0) { // inclusão
                        prescreve.setDosagem(dosagem);

                        opcaoCrud(CRUD_INC);
                    } else { // alteração
                        if (prescreve.getDosagem() != dosagem ||
                                !prescreve.getHorario().toString().equals(etAlterarHorario.toString())) {

                            prescreve.setDosagem(dosagem);
                            prescreve.setHorario(etAlterarHorario.getText().toString());
                            prescreve.setSuspender(cbAlterarSuspender.isChecked());

                            opcaoCrud(CRUD_UPD);
                        } else {
                            Toast.makeText(PrescreverAlterarActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    finish();
                }
            }
        });

        btAlterarCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(PrescreverAlterarActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void preparaDados() {

        prescreve = (Prescreve) getIntent().getSerializableExtra("prescreve");
        medico = (Medico) getIntent().getSerializableExtra("medico");

        if (prescreve.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etAlterarDosagem.setText(String.valueOf(prescreve.getDosagem()));
            etAlterarHorario.setText(prescreve.getHorario());
            tvAlterarTerminologia.setText(prescreve.getMedicamento().getTerminologia().getDescricao());
            tvAlterarMedico.setText(prescreve.getMedico().getNome());
            cbAlterarSuspender.setChecked(prescreve.isSuspender());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (prescreve.getId() == 0) { // inclusão
            btAlterarOk.setText("Incluir");
            menu.clear();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_del:
                if (!medico.getEmail().equals(prescreve.getMedico().getEmail())) {
                    Toast.makeText(this, "Apenas o médico que prescreveu pode excluir!", Toast.LENGTH_LONG).show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Exclusão de prescrição")
                            .setMessage("Tem certeza que deseja excluir esse prescrição?")
                            .setPositiveButton("sim", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    opcaoCrud(CRUD_DEL);

                                    finish();
                                }
                            })
                            .setNegativeButton("não", null)
                            .show();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void opcaoCrud(byte tipoCrud) {

        if (Utils.hasInternet(this)) {
            crudDados(tipoCrud, new ResultEvent() {
                @Override
                public <T> void onResult(T result) {
                    msg = "Prescreve " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new PrescreveCtrl(PrescreverAlterarActivity.this).insert(prescreve);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new PrescreveCtrl(PrescreverAlterarActivity.this).update(prescreve);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new PrescreveCtrl(PrescreverAlterarActivity.this).delete(prescreve);
            }
        }

        Toast.makeText(PrescreverAlterarActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Prescreve> call = null;

//        if (tipoCrud == CRUD_INC)
//            call = new RetrofitConfig().getPrescreveService().create(prescreve);
//        else if (tipoCrud == CRUD_UPD)
//            call = new RetrofitConfig().getPrescreveService().update(prescreve.getId(), prescreve);
//        else if (tipoCrud == CRUD_DEL)
//            call = new RetrofitConfig().getPrescreveService().delete(prescreve.getId());
//
//        call.enqueue(new Callback<Prescreve>() {
//            @Override
//            public void onResponse(Call<Prescreve> call, Response<Leito> response) {
//                prescreve = response.body();
//
//                resultEvent.onResult(prescreve);
//            }
//
//            @Override
//            public void onFailure(Call<Prescreve> call, Throwable t) {
//                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " prescreve");
//            }
//        });
    }
}