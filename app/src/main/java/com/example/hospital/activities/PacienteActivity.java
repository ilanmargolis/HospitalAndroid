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
import com.example.hospital.adapter.PacienteAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.PacienteCtrl;
import com.example.hospital.model.Paciente;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteActivity extends AppCompatActivity {

    private RecyclerView rv;
    private PacienteAdapter pacienteAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        rv = (RecyclerView) findViewById(R.id.rvPaciente);
        rv.setLayoutManager(new LinearLayoutManager(PacienteActivity.this));
    }

    @Override
    public void onResume() {

        List<Paciente> pacienteList = null;

        super.onResume();

        if (Utils.hasInternet(this)) {
//        getAllPacientes(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                pacienteList = (List<Paciente>) result;
//            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(PacienteActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
        } else {
            pacienteList = new PacienteCtrl(PacienteActivity.this).getAll();
        }

        pacienteAdapter = new PacienteAdapter(PacienteActivity.this, pacienteList);
        rv.setAdapter(pacienteAdapter);
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
                intent = new Intent(PacienteActivity.this, PacienteDadosActivity.class);
                intent.putExtra("funcionario", (Serializable) new Paciente());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllPacientes(ResultEvent resultEvent) {

        Call<List<Paciente>> call = new RetrofitConfig().getPacienteService().getAll();

        call.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                List<Paciente> pacienteList = response.body();

                RoomConfig.getInstance(PacienteActivity.this).pacienteDao().insertAll(pacienteList);

                resultEvent.onResult(pacienteList);
            }

            @Override
            public void onFailure(Call<List<Paciente>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}