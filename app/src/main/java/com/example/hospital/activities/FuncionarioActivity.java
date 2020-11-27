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
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.FuncionarioCtrl;
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

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuAdministrativoActivity.TELA_MENU, getIntent());

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
        menuInflater.inflate(R.menu.menu_admin, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_admin_funcionario).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_admin_add:
                intent = new Intent(FuncionarioActivity.this, FuncionarioDadosActivity.class);
                intent.putExtra("funcionario", (Serializable) new Funcionario());
                startActivity(intent);

                break;

            case R.id.action_admin_refresh:
                onResume();

                break;

            case R.id.action_admin_unidade:
                setResult(MenuAdministrativoActivity.TELA_UNIDADE, getIntent());
                finish();

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