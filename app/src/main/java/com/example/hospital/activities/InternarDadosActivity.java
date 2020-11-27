package com.example.hospital.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.InternarCtrl;
import com.example.hospital.controller.PacienteCtrl;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Paciente;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;
import com.example.hospital.util.ValidaCpf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternarDadosActivity extends AppCompatActivity {

    private TextView tvInternarLeitoCodigo, tvInternarLeitoUnidade, tvInternarLeitoSetor,
            tvInternarPacienteNome, tvInternarDtInternacao, tvInternarDtPrevisao;
    private EditText etInternarPacienteCpf;
    private Button btInternarOk, btInternarCancelar;
    private ImageButton ibInternarPesquisar, ibInternarPaciente, ibDataPrevisao;
    private Calendar calendario = Calendar.getInstance();
    private Leito leito;
    private Internado internado = null;
    private Paciente paciente;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internar_dados);

        tvInternarLeitoCodigo = (TextView) findViewById(R.id.tvInternarLeitoCodigo);
        tvInternarLeitoUnidade = (TextView) findViewById(R.id.tvInternarLeitoUnidade);
        tvInternarLeitoSetor = (TextView) findViewById(R.id.tvInternarLeitoSetor);
        tvInternarPacienteNome = (TextView) findViewById(R.id.tvInternarPacienteNome);
        tvInternarDtInternacao = (TextView) findViewById(R.id.tvInternarDtInternacao);
        tvInternarDtPrevisao = (TextView) findViewById(R.id.tvInternarDtPrevisao);
        etInternarPacienteCpf = (EditText) findViewById(R.id.etInternarPacienteCpf);
        ibInternarPesquisar = (ImageButton) findViewById(R.id.ibInternarPesquisar);
        ibInternarPaciente = (ImageButton) findViewById(R.id.ibInternarPaciente);
        ibDataPrevisao = (ImageButton) findViewById(R.id.ibDataPrevisao);
        btInternarOk = (Button) findViewById(R.id.btInternarOk);
        btInternarCancelar = (Button) findViewById(R.id.btInternarCancelar);

        etInternarPacienteCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, etInternarPacienteCpf));
        etInternarPacienteCpf.addTextChangedListener(new ValidaCpf(this, ibInternarPesquisar, etInternarPacienteCpf));

        tvInternarDtInternacao.setText(Utils.dateToString(calendario.getTime(), "dd/MM/yyyy"));

        preparaDados();

        btInternarOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean estaInternado = false;

                if (paciente != null) {
                    estaInternado = new InternarCtrl(InternarDadosActivity.this).isInternadoPaciente(paciente.getId());
                }

                if (etInternarPacienteCpf.getText().toString().equals("") ||
                        (tvInternarPacienteNome.getText().toString().trim().equals("")) ||
                        paciente == null) {
                    Toast.makeText(InternarDadosActivity.this, "Paciente não informado!", Toast.LENGTH_SHORT).show();
                    etInternarPacienteCpf.requestFocus();
                } else if (tvInternarDtPrevisao.getText().toString().equals("")) {
                    Toast.makeText(InternarDadosActivity.this, "Data de previsão de alta não informada!", Toast.LENGTH_SHORT).show();
                } else if (estaInternado) {
                    Toast.makeText(InternarDadosActivity.this, "Não é possível internar um paciente já internado!", Toast.LENGTH_SHORT).show();
                } else {
                    Date dtInternacao = Utils.stringToDate(tvInternarDtInternacao.getText().toString(), "dd/MM/yyyy");
                    Date dtPrevisao = Utils.stringToDate(tvInternarDtPrevisao.getText().toString(), "dd/MM/yyyy");

                    internado = new Internado(dtInternacao, dtPrevisao, leito, paciente);

                    opcaoCrud(CRUD_INC);

                    finish();
                }
            }
        });

        btInternarCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(InternarDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        ibInternarPesquisar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                paciente = new PacienteCtrl(InternarDadosActivity.this).getByCpf(etInternarPacienteCpf.getText().toString());

                if (paciente != null) {
                    tvInternarPacienteNome.setText(paciente.getNome().toString());
                    tvInternarDtInternacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                    tvInternarDtPrevisao.setText("");
                    tvInternarDtPrevisao.requestFocus();
                } else {
                    Toast.makeText(InternarDadosActivity.this, "Paciente inexistente!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibInternarPaciente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InternarDadosActivity.this, PacienteDadosActivity.class);
                intent.putExtra("paciente", (Serializable) new Paciente());
                startActivityForResult(intent, MenuRecepcaoActivity.TELA_PACIENTE);
            }
        });

        ibDataPrevisao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MenuRecepcaoActivity.TELA_PACIENTE && resultCode == RESULT_OK) {
            String resultado = data.getStringExtra("cpf");

            //Coloque no EditText
            etInternarPacienteCpf.setText(resultado);
            ibInternarPesquisar.callOnClick();
        }
    }

    private void preparaDados() {

        leito = (Leito) getIntent().getSerializableExtra("internar");

        tvInternarLeitoCodigo.setText(leito.getCodigo());
        tvInternarLeitoUnidade.setText(leito.getUnidade().getNome());
        tvInternarLeitoSetor.setText(leito.getSetor().getNome());
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes, dia);

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

                    tvInternarDtPrevisao.setText(data);

                    btInternarOk.setEnabled(!tvInternarDtPrevisao.toString().trim().equals(""));
                }
            };

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
                msg = new InternarCtrl(InternarDadosActivity.this).insert(internado);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new InternarCtrl(InternarDadosActivity.this).update(internado);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new InternarCtrl(InternarDadosActivity.this).delete(internado);
            }
        }

        Toast.makeText(InternarDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Leito> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getLeitoService().create(leito);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getLeitoService().update(leito.getId(), leito);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getLeitoService().delete(leito.getId());

        call.enqueue(new Callback<Leito>() {
            @Override
            public void onResponse(Call<Leito> call, Response<Leito> response) {
                leito = response.body();

                resultEvent.onResult(leito);
            }

            @Override
            public void onFailure(Call<Leito> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " leito");
            }
        });
    }
}