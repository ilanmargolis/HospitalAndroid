package com.example.hospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.controller.PacienteCtrl;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Paciente;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;
import com.example.hospital.util.ValidaCpf;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternarDadosActivity extends AppCompatActivity {

    private TextView tvInternarLeitoCodigo, tvInternarLeitoUnidade, tvInternarLeitoSetor;
    private EditText etInternarPacienteCpf, etInternarPacienteNome;
    private Button btInternarOk, btInternarCancelar;
    private ImageButton ibInternarPesquisar, ibInternarPaciente;
    private Leito leito;
    private Internado internado;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internar_dados);

        tvInternarLeitoCodigo = (TextView) findViewById(R.id.tvInternarLeitoCodigo);
        tvInternarLeitoUnidade = (TextView) findViewById(R.id.tvInternarLeitoUnidade);
        tvInternarLeitoSetor = (TextView) findViewById(R.id.tvInternarLeitoSetor);
        etInternarPacienteCpf = (EditText) findViewById(R.id.etInternarPacienteCpf);
        etInternarPacienteNome = (EditText) findViewById(R.id.etInternarPacienteNome);
        ibInternarPesquisar = (ImageButton) findViewById(R.id.ibInternarPesquisar);
        ibInternarPaciente = (ImageButton) findViewById(R.id.ibInternarPaciente);
        btInternarOk = (Button) findViewById(R.id.btInternarOk);
        btInternarCancelar = (Button) findViewById(R.id.btInternarCancelar);

        etInternarPacienteCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, etInternarPacienteCpf));
        etInternarPacienteCpf.addTextChangedListener(new ValidaCpf(this, ibInternarPesquisar, etInternarPacienteCpf));

        preparaDados();

        btInternarOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String codigoLeito = tvInternarLeitoCodigo.getText().toString().trim();

                if (leito.getId() == 0) { // inclusão
                    if (!codigoLeito.equals("")) {
                        leito.setCodigo(codigoLeito);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(InternarDadosActivity.this, "É necessário informar o código do leito!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (false) {
//                    if (!leito.getCodigo().equalsIgnoreCase(etLeitoCodigo.getText().toString().trim()) ||
//                            !leito.getUnidade().toString().equals(spLeitoUnidade.toString())||
//                            !leito.getSetor().toString().equals(spLeitoSetor.toString())) {

                        leito.setCodigo(codigoLeito);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(InternarDadosActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btInternarCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(InternarDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        ibInternarPesquisar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Paciente paciente = new PacienteCtrl(InternarDadosActivity.this).getByCpf(etInternarPacienteCpf.getText().toString());

                if (paciente != null) {
                    etInternarPacienteNome.setText(paciente.getNome().toString());
                }
            }
        });

        ibInternarPaciente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InternarDadosActivity.this, PacienteDadosActivity.class);
                intent.putExtra("paciente", (Serializable) new Paciente());
                startActivityForResult(intent, MenuRecepcaoActivity.TELA_PACIENTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MenuRecepcaoActivity.TELA_PACIENTE && resultCode == RESULT_OK) {
            String resultado = data.getStringExtra("cpf");
            //Coloque no EditText
            etInternarPacienteCpf.setText(resultado);
            ibInternarPesquisar.callOnClick();
        }
    }

    private void preparaDados() {

        leito = (Leito) getIntent().getSerializableExtra("internar");

        if (leito.getId() == 0) { // inclusão
//            spUnidade.setSelection(0);
        } else { // alteração e exclusão
            tvInternarLeitoCodigo.setText(leito.getCodigo());
            tvInternarLeitoUnidade.setText(leito.getUnidade().getNome());
            tvInternarLeitoSetor.setText(leito.getSetor().getNome());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (leito.getId() == 0) { // inclusão
//            btInternarOk.setText("Incluir");
            menu.clear();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_del:
                opcaoCrud(CRUD_DEL);

                finish();

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
                    msg = "Leito " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new LeitoCtrl(InternarDadosActivity.this).insert(leito);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new LeitoCtrl(InternarDadosActivity.this).update(leito);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new LeitoCtrl(InternarDadosActivity.this).delete(leito);
            }
        }

        Toast.makeText(InternarDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Leito> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getLeitoService().create(leito);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getLeitoService().update(leito.getId(), leito);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getLeitoService().delete(leito.getId());

        call.enqueue(new Callback<Leito>() {
            @Override
            public void onResponse(Call<Leito> call, Response<Leito> response) {
                leito = response.body();

                resultEvent.onResult(leito);
            }

            @Override
            public void onFailure(Call<Leito> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " leito");
            }
        });
    }
}