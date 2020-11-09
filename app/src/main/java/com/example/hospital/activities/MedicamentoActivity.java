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
import com.example.hospital.adapter.MedicamentoAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.MedicamentoCtrl;
import com.example.hospital.model.Medicamento;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MedicamentoAdapter medicamentoAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        rv = (RecyclerView) findViewById(R.id.rvMedicamento);
        rv.setLayoutManager(new LinearLayoutManager(MedicamentoActivity.this));
    }

    @Override
    public void onResume() {

        List<Medicamento> medicamentoList = null;

        super.onResume();

        if (Utils.hasInternet(this)) {
//        getAllMedicamentos(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                medicamentoList = (List<Medicamento>) result;
//            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(MedicamentoActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
        } else {
            medicamentoList = new MedicamentoCtrl(MedicamentoActivity.this).getAll();
        }

        medicamentoAdapter = new com.example.hospital.adapter.MedicamentoAdapter(MedicamentoActivity.this, medicamentoList);
        rv.setAdapter(medicamentoAdapter);
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
                intent = new Intent(MedicamentoActivity.this, MedicamentoDadosActivity.class);
                intent.putExtra("medicamento", (Serializable) new Medicamento());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllMedicamentos(ResultEvent resultEvent) {

        Call<List<Medicamento>> call = new RetrofitConfig().getMedicamentoService().getAll();

        call.enqueue(new Callback<List<Medicamento>>() {
            @Override
            public void onResponse(Call<List<Medicamento>> call, Response<List<Medicamento>> response) {
                List<Medicamento> medicamentoList = response.body();

                RoomConfig.getInstance(MedicamentoActivity.this).medicamentoDao().insertAll(medicamentoList);

                resultEvent.onResult(medicamentoList);
            }

            @Override
            public void onFailure(Call<List<Medicamento>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}