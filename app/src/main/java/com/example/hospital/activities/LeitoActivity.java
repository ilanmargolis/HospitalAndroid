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
import com.example.hospital.model.Leito;
import com.example.hospital.repository.ResultEvent;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeitoActivity extends AppCompatActivity {

    private RecyclerView rvLeito;
    private LeitoAdapter leitoAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leito);

        rvLeito = (RecyclerView) findViewById(R.id.rvLeito);
        rvLeito.setLayoutManager(new LinearLayoutManager(LeitoActivity.this));
    }

    @Override
    public void onResume() {
        super.onResume();

//        getAllLeitos(new ResultEvent() {
//            @Override
//            public <T> void onResult(T result) {
//                // Quando houver resultado mostre os valores na tela
//                List<Leito> leitoList = (List<Leito>) result;
//
//                leitoAdapter = new com.example.hospital.adapter.LeitoAdapter(LeitoActivity.this, leitoList);
//                rvLeito.setAdapter(leitoAdapter);
//            }
//
//            @Override
//            public void onFail(String message) {
//                // Quando houver falha exiba uma mensagem de erro
//                Toast.makeText(LeitoActivity.this, message, Toast.LENGTH_LONG).show();
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
                intent = new Intent(LeitoActivity.this, LeitoDadosActivity.class);
                intent.putExtra("leito", (Serializable) new Leito());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllLeitos(ResultEvent resultEvent) {

        Call<List<Leito>> call = new RetrofitConfig().getLeitoService().getAllLeito();

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