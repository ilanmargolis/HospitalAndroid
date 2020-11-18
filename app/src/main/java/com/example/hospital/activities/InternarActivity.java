package com.example.hospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.InternarAdapter;
import com.example.hospital.adapter.LeitoAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.controller.SetorCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternarActivity extends AppCompatActivity {

    private RecyclerView rv;
    private Spinner spInternarUnidade, spInternarSetor;
    private InternarAdapter internarAdapter;
    private Intent intent;
    private Unidade unidade;
    private Setor setor;
    private List<Unidade> unidadeList = null;
    private List<Setor> setorList = null;
    private List<Leito> leitoList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internar);

        spInternarUnidade = (Spinner) findViewById(R.id.spInternarUnidade);
        spInternarSetor = (Spinner) findViewById(R.id.spInternarSetor);

        if (!carregaSpinner()) {
            Toast.makeText(this, "É necessário incluir unidades de atendimento e/ou setor!", Toast.LENGTH_LONG).show();

            finish();
        }
        ;

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
//        setResult(MenuAdministrativoActivity.TELA_MENU, getIntent());

        rv = (RecyclerView) findViewById(R.id.rvInternarLeito);
        rv.setLayoutManager(new LinearLayoutManager(InternarActivity.this));
    }

    private boolean carregaSpinner() {

        // Carrega todos os departamentos no spinner
        if (Utils.hasInternet(this)) {

        } else {
            unidadeList = new UnidadeCtrl(this).getAll();
        }

        if (unidadeList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(InternarActivity.this, android.R.layout.simple_spinner_item, unidadeList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spInternarUnidade.setAdapter(aa);
        } else {
            return false;
        }

        spInternarUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unidade = (Unidade) spInternarUnidade.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spInternarSetor.setEnabled(false);
            }
        });

        if (Utils.hasInternet(this)) {

        } else {
            setorList = new SetorCtrl(this).getGeraLeito();
        }

        if (setorList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(InternarActivity.this, android.R.layout.simple_spinner_item, setorList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spInternarSetor.setAdapter(aa);
        } else {
            return false;
        }

        spInternarSetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setor = (Setor) spInternarSetor.getItemAtPosition(position);

                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (unidade != null && setor != null) {
            if (Utils.hasInternet(InternarActivity.this)) {
//        getAllLeitos(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                List<Leito> leitoList = (List<Leito>) result;
//            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(LeitoActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
            } else {
                leitoList = new LeitoCtrl(InternarActivity.this).getLeitoUnidadeSetor(unidade.getId(), setor.getId());
            }

            internarAdapter = new InternarAdapter(InternarActivity.this, leitoList);
            rv.setAdapter(internarAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_recep, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_recep_add).setVisible(false);
        menu.findItem(R.id.action_recep_internamento).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recep_refresh:
                onResume();

                return true;

            case R.id.action_recep_transferencia:
//                setResult(MenuAdministrativoActivity.TELA_UNIDADE, getIntent());
                finish();

                return true;

            case R.id.action_recep_logoff:
                setResult(MenuAdministrativoActivity.TELA_LOGIN, getIntent());
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllLeitos(ResultEvent resultEvent) {

        Call<List<Leito>> call = new RetrofitConfig().getLeitoService().getAll();

        call.enqueue(new Callback<List<Leito>>() {
            @Override
            public void onResponse(Call<List<Leito>> call, Response<List<Leito>> response) {
                List<Leito> leitoList = response.body();

                RoomConfig.getInstance(InternarActivity.this).leitoDao().insertAll(leitoList);

                resultEvent.onResult(leitoList);
            }

            @Override
            public void onFailure(Call<List<Leito>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}