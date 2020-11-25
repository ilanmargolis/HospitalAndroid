package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hospital.R;
import com.example.hospital.adapter.UnidadeAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeActivity extends AppCompatActivity {

    private RecyclerView rv;
    private UnidadeAdapter unidadeAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuAdministrativoActivity.TELA_MENU, getIntent());

        rv = (RecyclerView) findViewById(R.id.rvUnidade);
        rv.setLayoutManager(new LinearLayoutManager(UnidadeActivity.this));
    }

    @Override
    public void onResume() {

        List<Unidade> unidadeList = null;

        super.onResume();

        if (Utils.hasInternet(this)) {
//        getAllUnidades(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                unidadeList = (List<Unidade>) result;
////            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(UnidadeActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
        } else {
            unidadeList = new UnidadeCtrl(UnidadeActivity.this).getAll();
        }

        unidadeAdapter = new UnidadeAdapter(UnidadeActivity.this, unidadeList);
        rv.setAdapter(unidadeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_admin, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_admin_unidade).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_admin_add:
                intent = new Intent(UnidadeActivity.this, UnidadeDadosActivity.class);
                intent.putExtra("unidade", (Serializable) new Unidade());
                startActivity(intent);

                break;

            case R.id.action_admin_refresh:
                onResume();

                break;

            case R.id.action_admin_setor:
                setResult(MenuAdministrativoActivity.TELA_SETOR, getIntent());
                finish();

                break;

            case R.id.action_admin_leito:
                setResult(MenuAdministrativoActivity.TELA_LEITO, getIntent());
                finish();

                break;

            case R.id.action_admin_medicamento:
                setResult(MenuAdministrativoActivity.TELA_MEDICAMENTO, getIntent());
                finish();

                break;

            case R.id.action_admin_funcionario:
                setResult(MenuAdministrativoActivity.TELA_FUNCIONARIO, getIntent());
                finish();

                break;

            case R.id.action_admin_medico:
                setResult(MenuAdministrativoActivity.TELA_MEDICO, getIntent());
                finish();

                break;

            case R.id.action_admin_menu:
                finish();

                break;

            case R.id.action_admin_logoff:
                setResult(MenuAdministrativoActivity.TELA_LOGIN, getIntent());
                finish();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }

    private void getAllUnidades(ResultEvent resultEvent) {

        Call<List<Unidade>> call = new RetrofitConfig().getUnidadeService().getAll();

        call.enqueue(new Callback<List<Unidade>>() {
            @Override
            public void onResponse(Call<List<Unidade>> call, Response<List<Unidade>> response) {
                List<Unidade> unidadeList = response.body();

                RoomConfig.getInstance(UnidadeActivity.this).unidadeDao().insertAll(unidadeList);

                resultEvent.onResult(unidadeList);
            }

            @Override
            public void onFailure(Call<List<Unidade>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}