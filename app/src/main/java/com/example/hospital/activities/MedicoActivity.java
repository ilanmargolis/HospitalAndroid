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
import com.example.hospital.adapter.MedicoAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.MedicoCtrl;
import com.example.hospital.model.Medico;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicoActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MedicoAdapter medicoAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuAdministrativoActivity.TELA_MENU, getIntent());

        rv = (RecyclerView) findViewById(R.id.rvMedico);
        rv.setLayoutManager(new LinearLayoutManager(MedicoActivity.this));
    }

    @Override
    public void onResume() {

        List<Medico> medicoList = null;

        super.onResume();

        if (Utils.hasInternet(this)) {
//        getAllMedicos(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                medicoList = (List<Medico>) result;
//            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(MedicoActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
        } else {
            medicoList = new MedicoCtrl(MedicoActivity.this).getAll();
        }

        medicoAdapter = new com.example.hospital.adapter.MedicoAdapter(MedicoActivity.this, medicoList);
        rv.setAdapter(medicoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_admin, menu);

        // Esconder do menu a atual tela
        menu.findItem(R.id.action_admin_medico).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_admin_add:
                intent = new Intent(MedicoActivity.this, MedicoDadosActivity.class);
                intent.putExtra("medico", (Serializable) new Medico());
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

            case R.id.action_admin_funcionario:
                setResult(MenuAdministrativoActivity.TELA_FUNCIONARIO, getIntent());
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

    private void getAllMedicos(ResultEvent resultEvent) {

        Call<List<Medico>> call = new RetrofitConfig().getMedicoService().getAll();

        call.enqueue(new Callback<List<Medico>>() {
            @Override
            public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {
                List<Medico> medicoList = response.body();

                RoomConfig.getInstance(MedicoActivity.this).medicoDao().insertAll(medicoList);

                resultEvent.onResult(medicoList);
            }

            @Override
            public void onFailure(Call<List<Medico>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}