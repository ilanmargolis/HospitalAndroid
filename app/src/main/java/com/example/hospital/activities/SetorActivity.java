package com.example.hospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.SetorAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.SetorCtrl;
import com.example.hospital.model.Setor;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetorActivity extends AppCompatActivity {

    private RecyclerView rv;
    private SetorAdapter setorAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setor);

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuAdministrativoActivity.TELA_MENU, getIntent());

        rv = (RecyclerView) findViewById(R.id.rvSetor);
        rv.setLayoutManager(new LinearLayoutManager(SetorActivity.this));
    }

    @Override
    public void onResume() {

        List<Setor> setorList = null;

        super.onResume();

        if (Utils.hasInternet(this)) {
//        getAllSetores(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                setorList = (List<Setor>) result;
////            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(SetorActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
        } else {
            setorList = new SetorCtrl(SetorActivity.this).getAll();
        }

        setorAdapter = new SetorAdapter(SetorActivity.this, setorList);
        rv.setAdapter(setorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_admin, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_setor).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(SetorActivity.this, SetorDadosActivity.class);
                intent.putExtra("setor", (Serializable) new Setor());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            case R.id.action_unidade:
                setResult(MenuAdministrativoActivity.TELA_UNIDADE, getIntent());
                finish();

            case R.id.action_leito:
                setResult(MenuAdministrativoActivity.TELA_LEITO, getIntent());
                finish();

            case R.id.action_medicamento:
                setResult(MenuAdministrativoActivity.TELA_MEDICAMENTO, getIntent());
                finish();

            case R.id.action_funcionario:
                setResult(MenuAdministrativoActivity.TELA_FUNCIONARIO, getIntent());
                finish();

            case R.id.action_medico:
                setResult(MenuAdministrativoActivity.TELA_MEDICO, getIntent());
                finish();

            case R.id.action_logoff:
                setResult(MenuAdministrativoActivity.TELA_LOGIN, getIntent());
                finish();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllSetores(ResultEvent resultEvent) {

        Call<List<Setor>> call = new RetrofitConfig().getSetorService().getAll();

        call.enqueue(new Callback<List<Setor>>() {
            @Override
            public void onResponse(Call<List<Setor>> call, Response<List<Setor>> response) {
                List<Setor> setorList = response.body();

                RoomConfig.getInstance(SetorActivity.this).setorDao().insertAll(setorList);

                resultEvent.onResult(setorList);
            }

            @Override
            public void onFailure(Call<List<Setor>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}