package com.example.hospital.activities;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.AltaCtrl;
import com.example.hospital.controller.InternarCtrl;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.model.Alta;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaDadosActivity extends AppCompatActivity {

    private TextView tvAltaLeitoCodigo, tvAltaLeitoUnidade, tvAltaLeitoSetor, tvAltaPacienteNome,
            tvAltaDtInternacao, tvAltaDtPrevisao;
    private EditText etAltaDtAlta;
    private Button btAltaOk, btAltaCancelar;
    private Alta alta;
    private Internado internado;
    private Paciente paciente;
    private Medico medico;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_dados);

        tvAltaLeitoCodigo = (TextView) findViewById(R.id.tvAltaLeitoCodigo);
        tvAltaLeitoUnidade = (TextView) findViewById(R.id.tvAltaLeitoUnidade);
        tvAltaLeitoSetor = (TextView) findViewById(R.id.tvAltaLeitoSetor);
        tvAltaPacienteNome = (TextView) findViewById(R.id.tvAltaPacienteNome);
        tvAltaDtInternacao = (TextView) findViewById(R.id.tvAltaDtInternacao);
        tvAltaDtPrevisao = (TextView) findViewById(R.id.tvAltaDtPrevisao);
        etAltaDtAlta = (EditText) findViewById(R.id.etAltaDtAlta);
        btAltaOk = (Button) findViewById(R.id.btAltaOk);
        btAltaCancelar = (Button) findViewById(R.id.btAltaCancelar);

        preparaDados();

        btAltaOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date dtAlta = Utils.stringToDate(etAltaDtAlta.getText().toString(), "dd/MM/yyyy");

                if (etAltaDtAlta.getText().toString().equals("")) {
                    Toast.makeText(AltaDadosActivity.this, "Data de de alta não informada!", Toast.LENGTH_SHORT).show();
                    etAltaDtAlta.requestFocus();
                } else {
                    if (alta == null) { // inclusão

                        alta = new Alta(internado, medico, dtAlta);

                        opcaoCrud(CRUD_INC);
                    } else { // alteração
                        if (false) {
                            //                    if (!leito.getCodigo().equalsIgnoreCase(etLeitoCodigo.getText().toString().trim()) ||
                            //                            !leito.getUnidade().toString().equals(spLeitoUnidade.toString())||
                            //                            !leito.getSetor().toString().equals(spLeitoSetor.toString())) {

                            opcaoCrud(CRUD_UPD);
                        } else {
                            Toast.makeText(AltaDadosActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    finish();
                }
            }
        });

        btAltaCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(AltaDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

    private void preparaDados() {

        internado = (Internado) getIntent().getSerializableExtra("alta");
        medico = (Medico) getIntent().getSerializableExtra("medico");

        if (internado != null) {
            Leito leito = new LeitoCtrl(this).getById(internado.getLeito().getId());

            tvAltaLeitoCodigo.setText(leito.getCodigo());
            tvAltaLeitoUnidade.setText(leito.getUnidade().getNome());
            tvAltaLeitoSetor.setText(leito.getSetor().getNome());
            tvAltaDtInternacao.setText(Utils.dateToString(internado.getDataInternacao(), "dd/MM/yyyy"));
            tvAltaDtPrevisao.setText(Utils.dateToString(internado.getDataPrevisaoAlta(), "dd/MM/yyyy"));

            tvAltaPacienteNome.setText(internado.getPaciente().getNome());
        }
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
                msg = new AltaCtrl(AltaDadosActivity.this).insert(alta);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new AltaCtrl(AltaDadosActivity.this).update(alta);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new AltaCtrl(AltaDadosActivity.this).delete(alta);
            }
        }

        Toast.makeText(AltaDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
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