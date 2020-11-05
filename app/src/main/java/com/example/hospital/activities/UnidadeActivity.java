package com.example.hospital.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnidadeActivity extends AppCompatActivity {

    private RecyclerView rvUnidade;
    private UnidadeAdapter unidadeAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade);

        rvUnidade = (RecyclerView) findViewById(R.id.rvUnidade);
        rvUnidade.setLayoutManager(new LinearLayoutManager(UnidadeActivity.this));
    }

    @Override
    public void onResume() {
        super.onResume();

//        getAllUnidades(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                List<Unidade> unidadeList = (List<Unidade>) result;
//
//                unidadeAdapter = new com.example.hospital.adapter.UnidadeAdapter(UnidadeActivity.this, unidadeList);
//                rvUnidade.setAdapter(unidadeAdapter);
//            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(UnidadeActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//        });
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
                intent = new Intent(UnidadeActivity.this, UnidadeDadosActivity.class);
                intent.putExtra("unidade", (Serializable) new Unidade());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllUnidades(ResultEvent resultEvent) {

        Call<List<Unidade>> call = new RetrofitConfig().getUnidadeService().getAllUnidade();

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