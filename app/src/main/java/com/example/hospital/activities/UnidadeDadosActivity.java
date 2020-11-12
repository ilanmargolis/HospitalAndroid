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
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeDadosActivity extends AppCompatActivity {

    private EditText etUnidadeNome, etUnidadeLogradouro, etUnidadeTelefone, etUnidadeInscricao;
    private Button btUnidadeOk, btUnidadeCancelar;
    private Unidade unidade;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluída", "alterada", "excluída"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade_dados);

        etUnidadeNome = (EditText) findViewById(R.id.etUnidadeNome);
        etUnidadeLogradouro = (EditText) findViewById(R.id.etUnidadeLogradouro);
        etUnidadeTelefone = (EditText) findViewById(R.id.etUnidadeTelefone);
        etUnidadeInscricao = (EditText) findViewById(R.id.etUnidadeInscricao);
        btUnidadeOk = (Button) findViewById(R.id.btUnidadeOk);
        btUnidadeCancelar = (Button) findViewById(R.id.btUnidadeCancelar);

        etUnidadeTelefone.addTextChangedListener(Mask.insert(Mask.FONE_MASK, etUnidadeTelefone));
        etUnidadeInscricao.addTextChangedListener(Mask.insert(Mask.IE_MASK, etUnidadeInscricao));

        unidade = (Unidade) getIntent().getSerializableExtra("unidade");

        if (unidade.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            unidade = new UnidadeCtrl(UnidadeDadosActivity.this).get(unidade.getId());
            etUnidadeNome.setText(unidade.getNome());
            etUnidadeLogradouro.setText(unidade.getLogradouro());
            etUnidadeTelefone.setText(unidade.getTelefone());
            etUnidadeInscricao.setText(unidade.getInscricaoEstadual());
        }

        btUnidadeOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomeUnidade = etUnidadeNome.getText().toString().trim();

                if (unidade.getId() == 0) { // inclusão
                    if (!nomeUnidade.equals("")) {

                        unidade.setNome(nomeUnidade);
                        unidade.setLogradouro(etUnidadeLogradouro.getText().toString());
                        unidade.setTelefone(etUnidadeTelefone.getText().toString());
                        unidade.setInscricaoEstadual(etUnidadeInscricao.getText().toString());

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(UnidadeDadosActivity.this, "É necessário informar o nome da unidade!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!unidade.getNome().equalsIgnoreCase(etUnidadeNome.getText().toString().trim()) ||
                            !unidade.getLogradouro().equalsIgnoreCase(etUnidadeLogradouro.getText().toString().trim()) ||
                            !unidade.getTelefone().equalsIgnoreCase(etUnidadeTelefone.getText().toString().trim()) ||
                            !unidade.getInscricaoEstadual().equals(etUnidadeInscricao.getText().toString().trim())) {

                        unidade.setNome(nomeUnidade);
                        unidade.setLogradouro(etUnidadeLogradouro.getText().toString().trim());
                        unidade.setTelefone(etUnidadeTelefone.getText().toString());
                        unidade.setInscricaoEstadual(etUnidadeInscricao.getText().toString().trim());

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(UnidadeDadosActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btUnidadeCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(UnidadeDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (unidade.getId() == 0) { // inclusão
            btUnidadeOk.setText("Incluir");
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
                    msg = "Unidade " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new UnidadeCtrl(UnidadeDadosActivity.this).insert(unidade);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new UnidadeCtrl(UnidadeDadosActivity.this).update(unidade);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new UnidadeCtrl(UnidadeDadosActivity.this).delete(unidade);
            }
        }

        Toast.makeText(UnidadeDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Unidade> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getUnidadeService().create(unidade);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getUnidadeService().update(unidade.getId(), unidade);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getUnidadeService().delete(unidade.getId());

        call.enqueue(new Callback<Unidade>() {
            @Override
            public void onResponse(Call<Unidade> call, Response<Unidade> response) {
                unidade = response.body();

                resultEvent.onResult(unidade);
            }

            @Override
            public void onFailure(Call<Unidade> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " unidade");
            }
        });
    }
}