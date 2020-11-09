package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.MedicamentoCtrl;
import com.example.hospital.controller.TerminologiaCtrl;
import com.example.hospital.model.Medicamento;
import com.example.hospital.model.Terminologia;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoDadosActivity extends AppCompatActivity {

    private EditText etMedicamentoNome;
    private Button btMedicamentoOk, btMedicamentoCancelar;
    private Spinner spMedicamentoTerminologia;
    private Medicamento medicamento;
    private Terminologia terminologia;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_dados);

        etMedicamentoNome = (EditText) findViewById(R.id.etMedicamentoNome);
        spMedicamentoTerminologia = (Spinner) findViewById(R.id.spMedicamentoTerminologia);
        btMedicamentoOk = (Button) findViewById(R.id.btMedicamentoOk);
        btMedicamentoCancelar = (Button) findViewById(R.id.btMedicamentoCancelar);

        carregaSpinner();

        preparaDados();

        btMedicamentoOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomeMedicamento = etMedicamentoNome.getText().toString().trim();

                if (medicamento.getId() == 0) { // inclusão
                    if (!nomeMedicamento.equals("")) {
                        medicamento.setNome(nomeMedicamento);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(MedicamentoDadosActivity.this, "É necessário informar o nome do medicamento!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!medicamento.getNome().equalsIgnoreCase(etMedicamentoNome.getText().toString().trim())) {
                        medicamento.setNome(nomeMedicamento);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(MedicamentoDadosActivity.this, "O nome do medicamento tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btMedicamentoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(MedicamentoDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void preparaDados() {

        medicamento = (Medicamento) getIntent().getSerializableExtra("medicamento");

        if (medicamento.getId() == 0) { // inclusão
//            spMedicamentoTerminologia.setSelection(0);
        } else { // alteração e exclusão
            etMedicamentoNome.setText(medicamento.getNome());

//            terminologia = RoomConfig.getInstance(MedicamentoDadosActivity.this).terminologiaDao().getTerminologia(medicamento.getTerminologia().getId());
            terminologia = new TerminologiaCtrl(MedicamentoDadosActivity.this).get(medicamento.getTerminologia().getId());
            // posiciona spinner na unidade do leito
            if (terminologia != null) {
                spMedicamentoTerminologia.setSelection(Utils.getIndex(spMedicamentoTerminologia, terminologia.getDescricao().toString().trim()));
            }
        }
    }

    private void carregaSpinner() {

        // Carrega todos os departamentos no spinner
        if (Utils.hasInternet(this)) {

        } else {
            List<Terminologia> terminologiaList = new TerminologiaCtrl(MedicamentoDadosActivity.this).getAll();
        }

        ArrayList<Terminologia> terminologiaList = new ArrayList<>();
        terminologiaList.add(new Terminologia("ml", "mililitro"));
        terminologiaList.add(new Terminologia("cx", "caixa"));

        ArrayAdapter aa = new ArrayAdapter<>(MedicamentoDadosActivity.this, android.R.layout.simple_spinner_item, terminologiaList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMedicamentoTerminologia.setAdapter(aa);

        spMedicamentoTerminologia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // pega o departamento selecionado no spinner e atribui ao professor
                medicamento.setTerminologia((Terminologia) spMedicamentoTerminologia.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                medicamento.setTerminologia(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (medicamento.getId() == 0) { // inclusão
            btMedicamentoOk.setText("Incluir");
            menu.clear();
        }

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
                msg = new MedicamentoCtrl(MedicamentoDadosActivity.this).insert(medicamento);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new MedicamentoCtrl(MedicamentoDadosActivity.this).update(medicamento);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new MedicamentoCtrl(MedicamentoDadosActivity.this).delete(medicamento);
            }
        }

        Toast.makeText(MedicamentoDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Medicamento> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getMedicamentoService().create(medicamento);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getMedicamentoService().update(medicamento.getId(), medicamento);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getMedicamentoService().delete(medicamento.getId());

        call.enqueue(new Callback<Medicamento>() {
            @Override
            public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                medicamento = response.body();

                resultEvent.onResult(medicamento);
            }

            @Override
            public void onFailure(Call<Medicamento> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " medicamento");
            }
        });
    }
}