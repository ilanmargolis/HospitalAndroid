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
import com.example.hospital.adapter.FuncionarioAdapter;
import com.example.hospital.adapter.UnidadeAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Funcionario;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioActivity extends AppCompatActivity {

    private RecyclerView rv;
    private FuncionarioAdapter funcionarioAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        rv = (RecyclerView) findViewById(R.id.rvFuncionario);
        rv.setLayoutManager(new LinearLayoutManager(FuncionarioActivity.this));
    }

    @Override
    public void onResume() {

        List<Funcionario> funcionarioList = null;

        super.onResume();

        if (Utils.hasInternet(this)) {
//        getAllUnidades(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                funcionarioList = (List<Unidade>) result;
//            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(UnidadeActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
        } else {
            funcionarioList = new FuncionarioCtrl(FuncionarioActivity.this).getAll();
        }

        funcionarioAdapter = new com.example.hospital.adapter.FuncionarioAdapter(FuncionarioActivity.this, funcionarioList);
        rv.setAdapter(funcionarioAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_generic, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(FuncionarioActivity.this, FuncionarioDadosActivity.class);
                intent.putExtra("funcionario", (Serializable) new Funcionario());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllUnidades(ResultEvent resultEvent) {

        Call<List<Funcionario>> call = new RetrofitConfig().getFuncionarioService().getAll();

        call.enqueue(new Callback<List<Funcionario>>() {
            @Override
            public void onResponse(Call<List<Funcionario>> call, Response<List<Funcionario>> response) {
                List<Funcionario> funcionarioList = response.body();

                RoomConfig.getInstance(FuncionarioActivity.this).funcionarioDao().insertAll(funcionarioList);

                resultEvent.onResult(funcionarioList);
            }

            @Override
            public void onFailure(Call<List<Funcionario>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}