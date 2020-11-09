package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeitoDadosActivity extends AppCompatActivity {

    private EditText etLeitoCodigo;
    private Spinner spLeitoUnidade;
    private Button btLeitoOk, btLeitoCancelar;
    private Spinner spUnidade;
    private Leito leito;
    private Unidade unidade;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluída", "alterada", "excluída"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito_dados);

        etLeitoCodigo = (EditText) findViewById(R.id.etLeitoCodigo);
        spLeitoUnidade = (Spinner) findViewById(R.id.spLeitoUnidade);
        btLeitoOk = (Button) findViewById(R.id.btLeitoOk);
        btLeitoCancelar = (Button) findViewById(R.id.btLeitoCancelar);

        carregaSpinner();

        preparaDados();

        btLeitoOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String codigoLeito = etLeitoCodigo.getText().toString().trim();

                if (leito.getId() == 0) { // inclusão
                    if (!codigoLeito.equals("")) {
                        leito.setCodigo(codigoLeito);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(LeitoDadosActivity.this, "É necessário informar o código do leito!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!leito.getCodigo().equalsIgnoreCase(etLeitoCodigo.getText().toString().trim())) {
                        leito.setCodigo(codigoLeito);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(LeitoDadosActivity.this, "O código do leito tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btLeitoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(LeitoDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void preparaDados() {

        leito = (Leito) getIntent().getSerializableExtra("leito");

        if (leito.getId() == 0) { // inclusão
//            spUnidade.setSelection(0);
        } else { // alteração e exclusão
            etLeitoCodigo.setText(leito.getCodigo());

            unidade = RoomConfig.getInstance(LeitoDadosActivity.this).unidadeDao().getUnidade(leito.getUnidade().getId());

            // posiciona spinner na unidade do leito
            if (unidade != null) {
                spUnidade.setSelection(Utils.getIndex(spUnidade, unidade.getNome().toString().trim()));
            }
        }
    }

    private void carregaSpinner() {

        // Carrega todos os departamentos no spinner
        if (Utils.hasInternet(this)) {

        } else {
            List<Unidade> unidadeList = new UnidadeCtrl(LeitoDadosActivity.this).getAll();
        }

        ArrayList<Unidade> unidadeList = new ArrayList<>();
        unidadeList.add(new Unidade("Boa Vista", "Rua X", "343433434344"));
        unidadeList.add(new Unidade("Encruzilhada", "Rua Y", "72782366326"));

        ArrayAdapter aa = new ArrayAdapter<>(LeitoDadosActivity.this, android.R.layout.simple_spinner_item, unidadeList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLeitoUnidade.setAdapter(aa);

        spLeitoUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // pega o departamento selecionado no spinner e atribui ao professor
                leito.setUnidade((Unidade) spLeitoUnidade.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                leito.setUnidade(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (leito.getId() == 0) { // inclusão
            btLeitoOk.setText("Incluir");
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
                msg = new LeitoCtrl(LeitoDadosActivity.this).insert(leito);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new LeitoCtrl(LeitoDadosActivity.this).update(leito);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new LeitoCtrl(LeitoDadosActivity.this).delete(leito);
            }
        }

        Toast.makeText(LeitoDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
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