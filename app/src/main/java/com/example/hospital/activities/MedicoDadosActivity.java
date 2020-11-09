package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.MedicoCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicoDadosActivity extends AppCompatActivity {

    private EditText etMedicoNome, etMedicoEmail, etMedicoCbos;
    private Button btMedicoOk, btMedicoCancelar;
    private Medico medico;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_dados);

        etMedicoNome = (EditText) findViewById(R.id.etMedicoNome);
        etMedicoEmail = (EditText) findViewById(R.id.etMedicoEmail);
        etMedicoCbos = (EditText) findViewById(R.id.etMedicoCbos);
        btMedicoOk = (Button) findViewById(R.id.btMedicoOk);
        btMedicoCancelar = (Button) findViewById(R.id.btMedicoCancelar);

        etMedicoCbos.addTextChangedListener(Mask.insert(Mask.CBOS_MASK, etMedicoCbos));

        medico = (Medico) getIntent().getSerializableExtra("medico");

        if (medico.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etMedicoNome.setText(medico.getNome());
        }

        btMedicoOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String nomeMedico = etMedicoNome.getText().toString().trim();

                if (medico.getId() == 0) { // inclusão
                    if (!nomeMedico.equals("")) {
                        medico.setNome(nomeMedico);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(MedicoDadosActivity.this, "É necessário informar o nome do médico!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!medico.getNome().equalsIgnoreCase(etMedicoNome.getText().toString().trim())) {
                        medico.setNome(nomeMedico);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(MedicoDadosActivity.this, "O nome do médico tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btMedicoCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(MedicoDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (medico.getId() == 0) { // inclusão
            btMedicoOk.setText("Incluir");
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
                    msg = "Médico " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new MedicoCtrl(MedicoDadosActivity.this).insert(medico);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new MedicoCtrl(MedicoDadosActivity.this).update(medico);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new MedicoCtrl(MedicoDadosActivity.this).delete(medico);
            }
        }

        Toast.makeText(MedicoDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Medico> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getMedicoService().create(medico);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getMedicoService().update(medico.getId(), medico);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getMedicoService().delete(medico.getId());

        call.enqueue(new Callback<Medico>() {
            @Override
            public void onResponse(Call<Medico> call, Response<Medico> response) {
                medico = response.body();

                resultEvent.onResult(medico);
            }

            @Override
            public void onFailure(Call<Medico> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " médico");
            }
        });
    }
}