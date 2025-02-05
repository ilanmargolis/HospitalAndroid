package com.example.hospital.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.PacienteCtrl;
import com.example.hospital.model.Paciente;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;
import com.example.hospital.util.ValidaCpf;
import com.example.hospital.util.ValidaSenha;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteDadosActivity extends AppCompatActivity {

    private EditText etPacienteNome, etPacienteEmail, etPacienteCpf, etPacienteSenha,
            etPacienteConfirmaSenha;
    private TextView tvPacienteDtNascimento;
    private ImageButton ibDataNascimento;
    private Button btPacienteOk, btPacienteCancelar;
    private Calendar calendario = Calendar.getInstance();
    private Paciente paciente;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_dados);

        etPacienteNome = (EditText) findViewById(R.id.etPacienteNome);
        etPacienteEmail = (EditText) findViewById(R.id.etPacienteEmail);
        etPacienteCpf = (EditText) findViewById(R.id.etPacienteCpf);
        tvPacienteDtNascimento = (TextView) findViewById(R.id.tvPacienteDtNascimento);
        etPacienteSenha = (EditText) findViewById(R.id.etPacienteSenha);
        etPacienteConfirmaSenha = (EditText) findViewById(R.id.etPacienteConfirmaSenha);
        ibDataNascimento = (ImageButton) findViewById(R.id.ibDataNascimento);
        btPacienteOk = (Button) findViewById(R.id.btPacienteOk);
        btPacienteCancelar = (Button) findViewById(R.id.btPacienteCancelar);

        // Habilita o btPacienteOk caso a senha seja digitada corretamente
        etPacienteSenha.addTextChangedListener(new ValidaSenha(this, btPacienteOk, etPacienteSenha, etPacienteConfirmaSenha));
        etPacienteConfirmaSenha.addTextChangedListener(new ValidaSenha(this, btPacienteOk, etPacienteSenha, etPacienteConfirmaSenha));

        etPacienteCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, etPacienteCpf));
        etPacienteCpf.addTextChangedListener(new ValidaCpf(this, btPacienteOk, etPacienteCpf));

        preparaDados();

        btPacienteOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomePaciente = etPacienteNome.getText().toString().trim();
                Date dtNascimento = Utils.stringToDate(tvPacienteDtNascimento.getText().toString(), "dd/MM/yyyy");
                String cpf = etPacienteCpf.getText().toString().trim();
                Paciente pacAux = new PacienteCtrl(PacienteDadosActivity.this).getByCpf(cpf);

                if (nomePaciente.equals("")) {
                    Toast.makeText(PacienteDadosActivity.this, "É necessário informar o nome!", Toast.LENGTH_SHORT).show();
                    etPacienteNome.requestFocus();
                } else if (cpf.equals("") || cpf.length() < 14) {
                    Toast.makeText(PacienteDadosActivity.this, "É necessário informar o CPF!", Toast.LENGTH_SHORT).show();
                    etPacienteCpf.requestFocus();
                } else if (paciente.getId() == 0 && pacAux != null) {
                    Toast.makeText(PacienteDadosActivity.this, "CPF já está sendo utilzado no sistema!", Toast.LENGTH_SHORT).show();

                    etPacienteCpf.setText("");
                    etPacienteCpf.requestFocus();
                } else if (tvPacienteDtNascimento.getText().toString().trim().equals("")) {
                    Toast.makeText(PacienteDadosActivity.this, "É necessário informar a data de nascimento!", Toast.LENGTH_SHORT).show();
                    tvPacienteDtNascimento.requestFocus();
                } else {
                    if (paciente.getId() == 0) { // inclusão

                        paciente.setNome(nomePaciente);
                        paciente.setEmail(etPacienteEmail.getText().toString());
                        paciente.setCpf(cpf);
                        paciente.setDataNascimento(dtNascimento);
                        paciente.setSenha(etPacienteSenha.getText().toString().trim());
                        paciente.setSexo(' ');

                        opcaoCrud(CRUD_INC);

                        Intent intent = new Intent();
                        intent.putExtra("cpf", etPacienteCpf.getText().toString());
                        setResult(RESULT_OK, intent);
                    } else { // alteração
                        if (!paciente.getNome().equalsIgnoreCase(etPacienteNome.getText().toString().trim()) ||
                                !paciente.getEmail().equals(etPacienteEmail.getText().toString().trim()) ||
                                !paciente.getDataNascimento().equals(dtNascimento) ||
                                (paciente.getSenha() != null && !paciente.getSenha().equals(etPacienteSenha.getText().toString().trim()))) {

                            paciente.setNome(nomePaciente);
                            paciente.setEmail(etPacienteEmail.getText().toString());
                            paciente.setCpf(etPacienteCpf.getText().toString());
                            paciente.setDataNascimento(dtNascimento);
                            paciente.setSenha(etPacienteSenha.getText().toString().trim());

                            opcaoCrud(CRUD_UPD);
                        } else {
                            Toast.makeText(PacienteDadosActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    finish();
                }
            }
        });

        btPacienteCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(PacienteDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        ibDataNascimento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    private void preparaDados() {

        paciente = (Paciente) getIntent().getSerializableExtra("paciente");

        if (paciente.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etPacienteNome.setText(paciente.getNome());
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        AlertDialog.THEME_TRADITIONAL,mDateSetListener,ano,mes,dia);

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

                    tvPacienteDtNascimento.setText(data);

                    btPacienteOk.setEnabled(!tvPacienteDtNascimento.toString().trim().equals(""));
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (paciente.getId() == 0) { // inclusão
            btPacienteOk.setText("Incluir");
            menu.clear();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_del:
                new AlertDialog.Builder(this)
                        .setTitle("Exclusão de paciente")
                        .setMessage("Tem certeza que deseja excluir esse paciente?")
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
                    msg = "Paciente " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new PacienteCtrl(PacienteDadosActivity.this).insert(paciente);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new PacienteCtrl(PacienteDadosActivity.this).update(paciente);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new PacienteCtrl(PacienteDadosActivity.this).delete(paciente);
            }
        }

        Toast.makeText(PacienteDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Paciente> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getPacienteService().create(paciente);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getPacienteService().update(paciente.getId(), paciente);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getPacienteService().delete(paciente.getId());

        call.enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                paciente = response.body();

                resultEvent.onResult(paciente);
            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " paciente");
            }
        });
    }
}