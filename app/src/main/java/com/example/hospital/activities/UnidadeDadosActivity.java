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
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeDadosActivity extends AppCompatActivity {

    private EditText etUnidadeNome, etUnidadeLogradouro, etUnidadeInscricao;
    private Button btUnidadeOk, btUnidadeCancelar;
    private Unidade unidade;

    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade_dados);

        etUnidadeNome = (EditText) findViewById(R.id.etUnidadeNome);
        etUnidadeLogradouro = (EditText) findViewById(R.id.etUnidadeLogradouro);
        etUnidadeInscricao = (EditText) findViewById(R.id.etUnidadeInscricao);
        btUnidadeOk = (Button) findViewById(R.id.btUnidadeOk);
        btUnidadeCancelar = (Button) findViewById(R.id.btUnidadeCancelar);

        etUnidadeInscricao.addTextChangedListener(Mask.insert(Mask.IE_MASK, etUnidadeInscricao));

        unidade = (Unidade) getIntent().getSerializableExtra("unidade");

        if (unidade.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etUnidadeNome.setText(unidade.getNome());
        }

        btUnidadeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUnidade = etUnidadeNome.getText().toString().trim();

                if (unidade.getId() == 0) { // inclusão
                    if (!nomeUnidade.equals("")) {
                        unidade.setNome(nomeUnidade);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(UnidadeDadosActivity.this, "É necessário informar o nome da unidade!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!unidade.getNome().equalsIgnoreCase(etUnidadeNome.getText().toString().trim())) {
                        unidade.setNome(nomeUnidade);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(UnidadeDadosActivity.this, "O nome da unidade tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
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
        crudDados(tipoCrud, new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                if (tipoCrud == CRUD_INC)
                    //RoomConfig.getInstance(CourseDadosActivity.this).courseDao().insert(course);
                    Toast.makeText(UnidadeDadosActivity.this, "Unidade incluída com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_UPD)
                    Toast.makeText(UnidadeDadosActivity.this, "Unidade alterada com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_DEL)
                    Toast.makeText(UnidadeDadosActivity.this, "Unidade excluída com sucesso", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail(String message) {
                Toast.makeText(UnidadeDadosActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
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
                if (tipoCrud == CRUD_INC)
                    resultEvent.onFail("Erro ao incluir unidade");
                else if (tipoCrud == CRUD_UPD)
                    resultEvent.onFail("Erro ao alterar unidade");
                else if (tipoCrud == CRUD_DEL)
                    resultEvent.onFail("Erro ao excluir unidade");

            }
        });
    }
}