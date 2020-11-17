package com.example.hospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.LeitoAdapter;
import com.example.hospital.adapter.UnidadeAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.model.Leito;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeitoActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LeitoAdapter leitoAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito);

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuAdministrativoActivity.TELA_MENU, getIntent());

        rv = (RecyclerView) findViewById(R.id.rvLeito);
        rv.setLayoutManager(new LinearLayoutManager(LeitoActivity.this));
    }

    @Override
    public void onResume() {

        List<Leito> leitoList = null;

        super.onResume();

        if (Utils.hasInternet(this)) {
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
            leitoList = new LeitoCtrl(LeitoActivity.this).getAll();
        }

        leitoAdapter = new com.example.hospital.adapter.LeitoAdapter(LeitoActivity.this, leitoList);
        rv.setAdapter(leitoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_admin, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_admin_leito).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_admin_add:
                intent = new Intent(LeitoActivity.this, LeitoDadosActivity.class);
                intent.putExtra("leito", (Serializable) new Leito());
                startActivity(intent);

                return true;

            case R.id.action_admin_refresh:
                onResume();

            case R.id.action_admin_unidade:
                setResult(MenuAdministrativoActivity.TELA_UNIDADE, getIntent());
                finish();

            case R.id.action_admin_setor:
                setResult(MenuAdministrativoActivity.TELA_SETOR, getIntent());
                finish();

            case R.id.action_admin_medicamento:
                setResult(MenuAdministrativoActivity.TELA_MEDICAMENTO, getIntent());
                finish();

            case R.id.action_admin_funcionario:
                setResult(MenuAdministrativoActivity.TELA_FUNCIONARIO, getIntent());
                finish();

            case R.id.action_admin_medico:
                setResult(MenuAdministrativoActivity.TELA_MEDICO, getIntent());
                finish();

            case R.id.action_admin_logoff:
                setResult(MenuAdministrativoActivity.TELA_LOGIN, getIntent());
                finish();

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

                RoomConfig.getInstance(LeitoActivity.this).leitoDao().insertAll(leitoList);

                resultEvent.onResult(leitoList);
            }

            @Override
            public void onFailure(Call<List<Leito>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}