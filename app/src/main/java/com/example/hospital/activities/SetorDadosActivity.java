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
import com.example.hospital.controller.SetorCtrl;
import com.example.hospital.model.Setor;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetorDadosActivity extends AppCompatActivity {

    private EditText etSetorNome, etSetorRamal;
    private Button btSetorOk, btSetorCancelar;
    private Setor setor;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor_dados);

        etSetorNome = (EditText) findViewById(R.id.etSetorNome);
        etSetorRamal = (EditText) findViewById(R.id.etSetorRamal);
        btSetorOk = (Button) findViewById(R.id.btSetorOk);
        btSetorCancelar = (Button) findViewById(R.id.btSetorCancelar);

        setor = (Setor) getIntent().getSerializableExtra("setor");

        if (setor.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            setor = new SetorCtrl(SetorDadosActivity.this).get(setor.getId());
            etSetorNome.setText(setor.getNome());
            etSetorRamal.setText(setor.getRamal());
        }

        btSetorOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomeUnidade = etSetorNome.getText().toString().trim();

                if (setor.getId() == 0) { // inclusão
                    if (!nomeUnidade.equals("")) {

                        setor.setNome(nomeUnidade);
                        setor.setRamal(etSetorRamal.getText().toString());

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(SetorDadosActivity.this, "É necessário informar o nome do setor!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!setor.getNome().equalsIgnoreCase(etSetorNome.getText().toString().trim()) ||
                            !setor.getRamal().equals(etSetorRamal.getText().toString().trim())) {

                        setor.setNome(nomeUnidade);
                        setor.setRamal(etSetorRamal.getText().toString().trim());

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(SetorDadosActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btSetorCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(SetorDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (setor.getId() == 0) { // inclusão
            btSetorOk.setText("Incluir");
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
                    msg = "Setor " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new SetorCtrl(SetorDadosActivity.this).insert(setor);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new SetorCtrl(SetorDadosActivity.this).update(setor);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new SetorCtrl(SetorDadosActivity.this).delete(setor);
            }
        }

        Toast.makeText(SetorDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Setor> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getSetorService().create(setor);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getSetorService().update(setor.getId(), setor);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getSetorService().delete(setor.getId());

        call.enqueue(new Callback<Setor>() {
            @Override
            public void onResponse(Call<Setor> call, Response<Setor> response) {
                setor = response.body();

                resultEvent.onResult(setor);
            }

            @Override
            public void onFailure(Call<Setor> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " setor");
            }
        });
    }
}