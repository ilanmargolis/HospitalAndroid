package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.InternadoAdapter;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.config.RoomConfig;
import com.example.hospital.controller.InternarCtrl;
import com.example.hospital.controller.SetorCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternadoActivity extends AppCompatActivity {

    private RecyclerView rv;
    private Spinner spInternadoUnidade, spInternadoSetor;
    private InternadoAdapter internadoAdapter;
    private Unidade unidade;
    private Setor setor;
    private List<Unidade> unidadeList = null;
    private List<Setor> setorList = null;
    private List<Internado> internadoList = null;
    private Medico medico;
    private String textoTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internado);

        // Altera o título da activity
        textoTela = getIntent().getExtras().getString("tela");

        setTitle("Pacientes internados (" + textoTela + ")");

        // Caso não seja escolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuMedicoActivity.TELA_MENU, getIntent());

        medico = (Medico) getIntent().getSerializableExtra("medico");

        spInternadoUnidade = (Spinner) findViewById(R.id.spInternadoUnidade);
        spInternadoSetor = (Spinner) findViewById(R.id.spInternadoSetor);

        if (!carregaSpinner()) {
            Toast.makeText(this, "É necessário incluir unidades de atendimento e/ou setor!", Toast.LENGTH_LONG).show();

            finish();
        };

        rv = (RecyclerView) findViewById(R.id.rvInternadoLeito);
        rv.setLayoutManager(new LinearLayoutManager(InternadoActivity.this));
    }

    private boolean carregaSpinner() {

        if (Utils.hasInternet(this)) {

        } else {
            unidadeList = new UnidadeCtrl(this).getAll();
        }

        if (unidadeList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(InternadoActivity.this, android.R.layout.simple_spinner_item, unidadeList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spInternadoUnidade.setAdapter(aa);
        } else {
            return false;
        }

        spInternadoUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unidade = (Unidade) spInternadoUnidade.getItemAtPosition(position);

                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spInternadoSetor.setEnabled(false);
            }
        });

        if (Utils.hasInternet(this)) {

        } else {
            setorList = new SetorCtrl(this).getGeraLeitos();
        }

        if (setorList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(InternadoActivity.this, android.R.layout.simple_spinner_item, setorList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spInternadoSetor.setAdapter(aa);
        } else {
            return false;
        }

        spInternadoSetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setor = (Setor) spInternadoSetor.getItemAtPosition(position);

                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (unidade != null && setor != null) {
            if (Utils.hasInternet(InternadoActivity.this)) {
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
                internadoList = new InternarCtrl(InternadoActivity.this).getInternadoPacientes(unidade.getId(), setor.getId());
            }

            internadoAdapter = new InternadoAdapter(InternadoActivity.this, internadoList, medico, textoTela);
            rv.setAdapter(internadoAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        if (medico != null) {
            menuInflater.inflate(R.menu.menu_medico, menu);

            // Esconder do menu a atual tela
            menu.findItem(R.id.action_medico_add).setVisible(false);

            if (textoTela.equals(this.getResources().getString(R.string.tela_alta))) {
                menu.findItem(R.id.action_medico_alta).setVisible(false);
            } else {
                menu.findItem(R.id.action_medico_prescrever).setVisible(false);
            }

            menu.findItem(R.id.action_medico_medicamento).setVisible(false);
        } else {
            menuInflater.inflate(R.menu.menu_recep, menu);

            // Esconder do menu a atual tela
            menu.findItem(R.id.action_recep_add).setVisible(false);
            menu.findItem(R.id.action_recep_transferencia).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_medico_refresh:
                onResume();

                break;

            case R.id.action_medico_alta:
                setResult(MenuMedicoActivity.TELA_ALTA, getIntent());
                finish();

                break;

            case R.id.action_medico_prescrever:
                setResult(MenuMedicoActivity.TELA_PRESCREVE, getIntent());
                finish();

                break;

            case R.id.action_medico_medicamento:
                setResult(MenuMedicoActivity.TELA_MEDICAMENTO, getIntent());
                finish();

                break;

            case R.id.action_medico_menu:
                finish();

                break;

            case R.id.action_medico_logoff:
                setResult(MenuMedicoActivity.TELA_LOGIN, getIntent());
                finish();

                break;

            case R.id.action_recep_refresh:
                onResume();

                break;

            case R.id.action_recep_internamento:
                setResult(MenuRecepcaoActivity.TELA_INTERNAR, getIntent());
                finish();

                break;

            case R.id.action_recep_transferencia:
                setResult(MenuRecepcaoActivity.TELA_TRANSFERENCIA, getIntent());
                finish();

                break;

            case R.id.action_recep_menu:
                finish();

                break;

            case R.id.action_recep_logoff:
                setResult(MenuRecepcaoActivity.TELA_LOGIN, getIntent());
                finish();

                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }

    private void getAllLeitos(ResultEvent resultEvent) {

        Call<List<Leito>> call = new RetrofitConfig().getLeitoService().getAll();

        call.enqueue(new Callback<List<Leito>>() {
            @Override
            public void onResponse(Call<List<Leito>> call, Response<List<Leito>> response) {
                List<Leito> leitoList = response.body();

                RoomConfig.getInstance(InternadoActivity.this).leitoDao().insertAll(leitoList);

                resultEvent.onResult(leitoList);
            }

            @Override
            public void onFailure(Call<List<Leito>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}