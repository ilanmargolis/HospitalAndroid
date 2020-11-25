package com.example.hospital.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.InternarCtrl;
import com.example.hospital.controller.LeitoCtrl;
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
            tvInternarPacienteNome;
    private EditText etInternarPacienteCpf, etInternarDtInternacao, etInternarDtPrevisao;
    private Button btInternarOk, btInternarCancelar;
    private ImageButton ibInternarPesquisar, ibInternarPaciente;
    private Leito leito;
    private Internado internado = null;
    private Paciente paciente;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internar_dados);

        tvInternarLeitoCodigo = (TextView) findViewById(R.id.tvInternarLeitoCodigo);
        tvInternarLeitoUnidade = (TextView) findViewById(R.id.tvInternarLeitoUnidade);
        tvInternarLeitoSetor = (TextView) findViewById(R.id.tvInternarLeitoSetor);
        etInternarPacienteCpf = (EditText) findViewById(R.id.etInternarPacienteCpf);
        tvInternarPacienteNome = (TextView) findViewById(R.id.tvInternarPacienteNome);
        etInternarDtInternacao = (EditText) findViewById(R.id.etInternarDtInternacao);
        etInternarDtPrevisao = (EditText) findViewById(R.id.etInternarDtPrevisao);
        ibInternarPesquisar = (ImageButton) findViewById(R.id.ibInternarPesquisar);
        ibInternarPaciente = (ImageButton) findViewById(R.id.ibInternarPaciente);
        btInternarOk = (Button) findViewById(R.id.btInternarOk);
        btInternarCancelar = (Button) findViewById(R.id.btInternarCancelar);

        etInternarPacienteCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, etInternarPacienteCpf));
        etInternarPacienteCpf.addTextChangedListener(new ValidaCpf(this, ibInternarPesquisar, etInternarPacienteCpf));

        etInternarDtInternacao.addTextChangedListener(Mask.insert(Mask.DATA_MASK, etInternarDtInternacao));
        etInternarDtPrevisao.addTextChangedListener(Mask.insert(Mask.DATA_MASK, etInternarDtPrevisao));

        preparaDados();

        btInternarOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date dtInternacao = Utils.stringToDate(etInternarDtInternacao.getText().toString(), "dd/MM/yyyy");
                Date dtPrevisao = Utils.stringToDate(etInternarDtPrevisao.getText().toString(), "dd/MM/yyyy");
                boolean estaInternado = new InternarCtrl(InternarDadosActivity.this).isInternadoPaciente(paciente.getId());

                if (etInternarPacienteCpf.getText().toString().equals("") || paciente == null) {
                    Toast.makeText(InternarDadosActivity.this, "Paciente não informado!", Toast.LENGTH_SHORT).show();
                    etInternarPacienteCpf.requestFocus();
                } else if (etInternarDtPrevisao.getText().toString().equals("")) {
                    Toast.makeText(InternarDadosActivity.this, "Data de previsão de alta não informada!", Toast.LENGTH_SHORT).show();
                    etInternarDtPrevisao.requestFocus();
                } else if (estaInternado) {
                    Toast.makeText(InternarDadosActivity.this, "Não é possível internar um paciente já internado!", Toast.LENGTH_SHORT).show();
                } else {
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
                    etInternarDtInternacao.setText(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                    etInternarDtPrevisao.setText("");
                    etInternarDtPrevisao.requestFocus();
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