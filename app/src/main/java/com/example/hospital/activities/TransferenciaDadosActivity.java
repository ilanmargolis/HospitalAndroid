package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.controller.InternarCtrl;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.controller.SetorCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Alta;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Unidade;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.util.List;

import retrofit2.Call;

public class TransferenciaDadosActivity extends AppCompatActivity {

    private TextView tvTransfLeitoCodigo, tvTransfLeitoUnidade, tvTransfLeitoSetor, tvTransfPacienteNome;
    private Spinner spTransfUnidade, spTransfSetor, spTransfLeito;
    private Button btTransfOk, btTransfCancelar;
    private Internado internado;
    private Medico medico;
    private Unidade unidade;
    private Setor setor;
    private Leito leito;
    private List<Unidade> unidadeList = null;
    private List<Setor> setorList = null;
    private List<Leito> leitoList = null;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia_dados);

        tvTransfLeitoCodigo = (TextView) findViewById(R.id.tvTransfLeitoCodigo);
        tvTransfLeitoUnidade = (TextView) findViewById(R.id.tvTransfLeitoUnidade);
        tvTransfLeitoSetor = (TextView) findViewById(R.id.tvTransfLeitoSetor);
        tvTransfPacienteNome = (TextView) findViewById(R.id.tvTransfPacienteNome);
        spTransfUnidade = (Spinner) findViewById(R.id.spTransfUnidade);
        spTransfSetor = (Spinner) findViewById(R.id.spTransfSetor);
        spTransfLeito = (Spinner) findViewById(R.id.spTransfLeito);
        btTransfOk = (Button) findViewById(R.id.btTransfOk);
        btTransfCancelar = (Button) findViewById(R.id.btTransfCancelar);

        // Caso não seja escolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuRecepcaoActivity.TELA_MENU, getIntent());

        if (!carregaSpinner()) {
            Toast.makeText(this, "É necessário incluir unidades de atendimento e/ou setor!", Toast.LENGTH_LONG).show();

            finish();
        };

        preparaDados();

        btTransfOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                internado.setLeito(leito);

                opcaoCrud(CRUD_UPD);

                finish();
            }
        });

        btTransfCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(TransferenciaDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

    private boolean carregaSpinner() {

        if (Utils.hasInternet(this)) {

        } else {
            unidadeList = new UnidadeCtrl(this).getAll();
        }

        if (unidadeList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(TransferenciaDadosActivity.this, android.R.layout.simple_spinner_item, unidadeList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTransfUnidade.setAdapter(aa);

            unidade = unidadeList.get(0);
        } else {
            return false;
        }

        spTransfUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unidade = (Unidade) spTransfUnidade.getItemAtPosition(position);
                CarregaLeitos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spTransfSetor.setEnabled(false);
            }
        });

        if (Utils.hasInternet(this)) {

        } else {
            setorList = new SetorCtrl(this).getGeraLeitos();
        }

        if (setorList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(TransferenciaDadosActivity.this, android.R.layout.simple_spinner_item, setorList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTransfSetor.setAdapter(aa);

            setor = setorList.get(0);
        } else {
            return false;
        }

        spTransfSetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setor = (Setor) spTransfSetor.getItemAtPosition(position);

                CarregaLeitos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spTransfLeito.setEnabled(false);
            }
        });

        spTransfLeito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leito = (Leito) spTransfLeito.getItemAtPosition(position);

                onResume();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    private void CarregaLeitos() {
        if (unidade != null && setor != null) {
            if (Utils.hasInternet(this)) {

            } else {
                leitoList = new LeitoCtrl(this).getLeitosDisponiveis(unidade.getId(), setor.getId());
            }

            if (leitoList.size() > 0) {
                ArrayAdapter aa = new ArrayAdapter<>(TransferenciaDadosActivity.this, android.R.layout.simple_spinner_item, leitoList);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spTransfLeito.setAdapter(aa);
            } else {
                Toast.makeText(this, "Não existe leito disponível nesta unidade e/ou setor. Aguarde disponibilidade ou selecione outra unidade e/ou setor!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void preparaDados() {

        internado = (Internado) getIntent().getSerializableExtra("internado");
        medico = (Medico) getIntent().getSerializableExtra("medico");

        Leito leito = new LeitoCtrl(this).getById(internado.getLeito().getId());

        tvTransfLeitoCodigo.setText(leito.getCodigo());
        tvTransfLeitoUnidade.setText(leito.getUnidade().getNome());
        tvTransfLeitoSetor.setText(leito.getSetor().getNome());
        tvTransfPacienteNome.setText(internado.getPaciente().getNome());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        menu.clear();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_del:
                opcaoCrud(CRUD_DEL);

                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void opcaoCrud(byte tipoCrud) {

        if (Utils.hasInternet(this)) {
            crudDados(tipoCrud, new ResultEvent() {
                @Override
                public <T> void onResult(T result) {
                    msg = "Leito " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new InternarCtrl(TransferenciaDadosActivity.this).insert(internado);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new InternarCtrl(TransferenciaDadosActivity.this).update(internado);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new InternarCtrl(TransferenciaDadosActivity.this).delete(internado);
            }
        }

        Toast.makeText(TransferenciaDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Alta> call = null;

//        if (tipoCrud == CRUD_INC)
//            call = new RetrofitConfig().getAltaService().create(alta);
//        else if (tipoCrud == CRUD_UPD)
//            call = new RetrofitConfig().getAltaService().update(alta.getId(), alta);
//        else if (tipoCrud == CRUD_DEL)
//            call = new RetrofitConfig().getAltaService().delete(alta.getId());
//
//        call.enqueue(new Callback<Alta>() {
//            @Override
//            public void onResponse(Call<Alta> call, Response<Alta> response) {
//                alta = response.body();
//
//                resultEvent.onResult(alta);
//            }
//
//            @Override
//            public void onFailure(Call<Alta> call, Throwable t) {
//                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " alta");
//            }
//        });
    }
}