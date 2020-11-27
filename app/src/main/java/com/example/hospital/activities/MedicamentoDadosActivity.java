package com.example.hospital.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoDadosActivity extends AppCompatActivity {

    private TextView tvMedicamentoValidade;
    private EditText etMedicamentoNome;
    private Button btMedicamentoOk, btMedicamentoCancelar;
    private ImageButton ibDataValidade;
    private Spinner spMedicamentoTerminologia;
    private Calendar calendario = Calendar.getInstance();
    private Medicamento medicamento;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_dados);

        etMedicamentoNome = (EditText) findViewById(R.id.etMedicamentoNome);
        tvMedicamentoValidade = (TextView) findViewById(R.id.tvMedicamentoValidade);
        spMedicamentoTerminologia = (Spinner) findViewById(R.id.spMedicamentoTerminologia);
        ibDataValidade = (ImageButton) findViewById(R.id.ibDataValidade);
        btMedicamentoOk = (Button) findViewById(R.id.btMedicamentoOk);
        btMedicamentoCancelar = (Button) findViewById(R.id.btMedicamentoCancelar);

        carregaSpinner();

        preparaDados();

        btMedicamentoOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomeMedicamento = etMedicamentoNome.getText().toString().trim();

                if (nomeMedicamento.equals("")) {
                    Toast.makeText(MedicamentoDadosActivity.this, "É necessário informar o nome!", Toast.LENGTH_SHORT).show();
                    etMedicamentoNome.requestFocus();
                } else {
                    Date validade = Utils.stringToDate(tvMedicamentoValidade.getText().toString(), "dd/MM/yyyy");

                    if (medicamento.getId() == 0) { // inclusão

                        medicamento.setNome(nomeMedicamento);
                        medicamento.setDataValidade(validade);

                        opcaoCrud(CRUD_INC);
                    } else { // alteração
                        if (!medicamento.getNome().equalsIgnoreCase(etMedicamentoNome.getText().toString().trim()) ||
                                !medicamento.getDataValidade().equals(validade) ||
                                !medicamento.getTerminologia().toString().equals(spMedicamentoTerminologia.toString())) {

                            medicamento.setNome(nomeMedicamento);
                            medicamento.setDataValidade(validade);

                            opcaoCrud(CRUD_UPD);
                        } else {
                            Toast.makeText(MedicamentoDadosActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    finish();
                }
            }
        });

        btMedicamentoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(MedicamentoDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        ibDataValidade.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

    }

    private void preparaDados() {

        medicamento = (Medicamento) getIntent().getSerializableExtra("medicamento");

        if (medicamento.getId() == 0) { // inclusão
//            spMedicamentoTerminologia.setSelection(0);
        } else { // alteração e exclusão
            etMedicamentoNome.setText(medicamento.getNome());
            tvMedicamentoValidade.setText(Utils.dateToString(medicamento.getDataValidade(), "dd/MM/yyyy"));

            spMedicamentoTerminologia.setSelection(Utils.getIndex(spMedicamentoTerminologia, medicamento.getTerminologia().toString()));
        }
    }

    private void carregaSpinner() {

        List<Terminologia> terminologiaList = null;

        // Carrega todos os departamentos no spinner
        if (Utils.hasInternet(this)) {

        } else {
            terminologiaList = new TerminologiaCtrl(MedicamentoDadosActivity.this).getAll();
        }

        ArrayAdapter aa = new ArrayAdapter<>(MedicamentoDadosActivity.this, android.R.layout.simple_spinner_item, terminologiaList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMedicamentoTerminologia.setAdapter(aa);

        spMedicamentoTerminologia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medicamento.setTerminologia((Terminologia) spMedicamentoTerminologia.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                medicamento.setTerminologia(null);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, mDateSetListener, ano, mes, dia);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    String data = String.format("%02d", dayOfMonth) + "/"
                            + String.format("%02d", monthOfYear + 1) + "/" +
                            String.valueOf(year);

                    tvMedicamentoValidade.setText(data);

                    btMedicamentoOk.setEnabled(!tvMedicamentoValidade.toString().trim().equals(""));
                }
            };

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
                new AlertDialog.Builder(this)
                        .setTitle("Exclusão de medicamento")
                        .setMessage("Tem certeza que deseja excluir esse medicamento?")
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                opcaoCrud(CRUD_DEL);

                                finish();
                            }
                        })
                        .setNegativeButton("não", null)
                        .show();

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