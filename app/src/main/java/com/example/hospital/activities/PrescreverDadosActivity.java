package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.R;
import com.example.hospital.adapter.PrescreveAdapter;
import com.example.hospital.controller.MedicamentoCtrl;
import com.example.hospital.controller.PrescreveCtrl;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Medicamento;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Prescreve;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;

import java.util.List;

import retrofit2.Call;

public class PrescreverDadosActivity extends AppCompatActivity {

    private RecyclerView rv;
    private PrescreveAdapter prescreveAdapter;
    private TextView tvPrescreverPacienteNome, tvPrescreverTerminologia;
    private EditText etPrescreverDosagem, etPrescreverHorario;
    private LinearLayout llPrescreverAdd;
    private Spinner spPrescreverMedicamento;
    private List<Medicamento> medicamentoList;
    private Medicamento medicamento;
    private Prescreve prescreve;
    private Internado internado;
    private Medico medico;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescrever_dados);

        tvPrescreverPacienteNome = (TextView) findViewById(R.id.tvPrescreverPacienteNome);
        tvPrescreverTerminologia = (TextView) findViewById(R.id.tvPrescreverTerminologia);
        etPrescreverDosagem = (EditText) findViewById(R.id.etPrescreverDosagem);
        etPrescreverHorario = (EditText) findViewById(R.id.etPrescreverHorario);
        spPrescreverMedicamento = (Spinner) findViewById(R.id.spPrescreverMedicamento);
        llPrescreverAdd = (LinearLayout) findViewById(R.id.llPrescreverAdd);

        // Caso não seja escolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuMedicoActivity.TELA_MENU, getIntent());

        rv = (RecyclerView) findViewById(R.id.rvPrescreve);
        rv.setLayoutManager(new LinearLayoutManager(PrescreverDadosActivity.this));

        if (!carregaSpinner()) {
            Toast.makeText(this, "É necessário cadastrar medicamento!", Toast.LENGTH_LONG).show();

            finish();
        };

        preparaDados();

        atualizaPrescreve();

        llPrescreverAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                byte dosagem = Byte.parseByte(etPrescreverDosagem.getText().toString());
                String horario = etPrescreverHorario.getText().toString().trim();

                if (dosagem <= 0) {
                    Toast.makeText(PrescreverDadosActivity.this, "Dosagem deve ser informada!", Toast.LENGTH_SHORT).show();
                    etPrescreverDosagem.requestFocus();
                } else {
                    prescreve = new Prescreve();
                    prescreve.setDosagem(dosagem);
                    prescreve.setHorario(horario);
                    prescreve.setMedico(medico);
                    prescreve.setInternado(internado);
                    prescreve.setMedicamento(medicamento);

                    opcaoCrud(CRUD_INC);

                    limpaDados();

                    atualizaPrescreve();
                }
            }
        });
    }

    private boolean carregaSpinner() {

        if (Utils.hasInternet(this)) {

        } else {
            medicamentoList = new MedicamentoCtrl(this).getAll();
        }

        if (medicamentoList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(PrescreverDadosActivity.this, android.R.layout.simple_spinner_item, medicamentoList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPrescreverMedicamento.setAdapter(aa);
        } else {
            return false;
        }

        spPrescreverMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medicamento = (Medicamento) spPrescreverMedicamento.getItemAtPosition(position);
                tvPrescreverTerminologia.setText(medicamento.getTerminologia().getDescricao());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spPrescreverMedicamento.setEnabled(false);
            }
        });

        return true;
    }

    private void preparaDados() {

        internado = (Internado) getIntent().getSerializableExtra("internado");
        medico = (Medico) getIntent().getSerializableExtra("medico");

        tvPrescreverPacienteNome.setText(internado.getPaciente().getNome());
    }

    private void limpaDados() {
        etPrescreverDosagem.setText("");
        etPrescreverHorario.setText("");
    }

    private void atualizaPrescreve() {

        List<Prescreve> prescreveList = null;

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
            prescreveList = new PrescreveCtrl(PrescreverDadosActivity.this).getByInternado(internado.getId());
        }

        prescreveAdapter = new com.example.hospital.adapter.PrescreveAdapter(PrescreverDadosActivity.this, prescreveList, medico);
        rv.setAdapter(prescreveAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        atualizaPrescreve();
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
                    msg = "Alta médica " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new PrescreveCtrl(PrescreverDadosActivity.this).insert(prescreve);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new PrescreveCtrl(PrescreverDadosActivity.this).update(prescreve);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new PrescreveCtrl(PrescreverDadosActivity.this).delete(prescreve);
            }
        }

        Toast.makeText(PrescreverDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Prescreve> call = null;

//        if (tipoCrud == CRUD_INC)
//            call = new RetrofitConfig().getPrescreveService().create(alta);
//        else if (tipoCrud == CRUD_UPD)
//            call = new RetrofitConfig().getPrescreveService().update(alta.getId(), alta);
//        else if (tipoCrud == CRUD_DEL)
//            call = new RetrofitConfig().getPrescreveService().delete(alta.getId());
//
//        call.enqueue(new Callback<Prescreve>() {
//            @Override
//            public void onResponse(Call<Prescreve> call, Response<Prescreve> response) {
//                prescreve = response.body();
//
//                resultEvent.onResult(prescreve);
//            }
//
//            @Override
//            public void onFailure(Call<Alta> call, Throwable t) {
//                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " prescreve");
//            }
//        });
    }
}