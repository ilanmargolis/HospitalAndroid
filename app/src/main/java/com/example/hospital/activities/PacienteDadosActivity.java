package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.PacienteCtrl;
import com.example.hospital.model.Paciente;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteDadosActivity extends AppCompatActivity {

    private EditText etPacienteNome, etPacienteEmail, etPacienteCpf;
    private Button btPacienteOk, btPacienteCancelar;
    private Paciente paciente;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_dados);

        etPacienteNome = (EditText) findViewById(R.id.etPacienteNome);
        etPacienteEmail = (EditText) findViewById(R.id.etPacienteEmail);
        etPacienteCpf = (EditText) findViewById(R.id.etPacienteCpf);
        btPacienteOk = (Button) findViewById(R.id.btPacienteOk);
        btPacienteCancelar = (Button) findViewById(R.id.btPacienteCancelar);

        etPacienteCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, etPacienteCpf));

        paciente = (Paciente) getIntent().getSerializableExtra("funcionario");

        if (paciente.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etPacienteNome.setText(paciente.getNome());
        }

        btPacienteOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomePaciente = etPacienteNome.getText().toString().trim();

                Toast.makeText(PacienteDadosActivity.this, (Utils.isCPFValido(etPacienteCpf.getText().toString())?"CPF válido":"CPF INVÁLIDO"), Toast.LENGTH_SHORT).show();

                if (paciente.getId() == 0) { // inclusão
                    if (!nomePaciente.equals("")) {
                        paciente.setNome(nomePaciente);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(PacienteDadosActivity.this, "É necessário informar o nome do funcionário!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!paciente.getNome().equalsIgnoreCase(etPacienteNome.getText().toString().trim())) {
                        paciente.setNome(nomePaciente);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(PacienteDadosActivity.this, "O nome do funcionário tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btPacienteCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(PacienteDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

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