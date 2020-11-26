package com.example.hospital.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import com.example.hospital.controller.AltaCtrl;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.model.Alta;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medico;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaDadosActivity extends AppCompatActivity {

    private TextView tvAltaLeitoCodigo, tvAltaLeitoUnidade, tvAltaLeitoSetor, tvAltaPacienteNome,
            tvAltaDtInternacao, tvAltaDtPrevisao, tvAltaDtAlta;
    private ImageButton ibDataAlta;
    private Button btAltaOk, btAltaCancelar;
    private Calendar calendario = Calendar.getInstance();
    private Alta alta;
    private Internado internado;
    private Medico medico;

    private String msg = null;
    private final String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    static final int DATE_DIALOG_ID = 0;

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
        tvAltaDtAlta = (TextView) findViewById(R.id.tvAltaDtAlta);
        ibDataAlta = (ImageButton) findViewById(R.id.ibDataAlta);
        btAltaOk = (Button) findViewById(R.id.btAltaOk);
        btAltaCancelar = (Button) findViewById(R.id.btAltaCancelar);

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuMedicoActivity.TELA_MENU, getIntent());

        preparaDados();

        btAltaOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (tvAltaDtAlta.getText().toString().equals("")) {
                    Toast.makeText(AltaDadosActivity.this, "Data de de alta não informada!", Toast.LENGTH_SHORT).show();
                    tvAltaDtAlta.requestFocus();
                } else {
                    Date dtAlta = Utils.stringToDate(tvAltaDtAlta.getText().toString(), "dd/MM/yyyy");

                    alta = new Alta(internado, medico, dtAlta);

                    opcaoCrud(CRUD_INC);

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

        ibDataAlta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    private void preparaDados() {

        internado = (Internado) getIntent().getSerializableExtra("internado");
        medico = (Medico) getIntent().getSerializableExtra("medico");

        Leito leito = new LeitoCtrl(this).getById(internado.getLeito().getId());

        tvAltaLeitoCodigo.setText(leito.getCodigo());
        tvAltaLeitoUnidade.setText(leito.getUnidade().getNome());
        tvAltaLeitoSetor.setText(leito.getSetor().getNome());
        tvAltaDtInternacao.setText(Utils.dateToString(internado.getDataInternacao(), "dd/MM/yyyy"));
        tvAltaDtPrevisao.setText(Utils.dateToString(internado.getDataPrevisaoAlta(), "dd/MM/yyyy"));

        tvAltaPacienteNome.setText(internado.getPaciente().getNome());
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

                    tvAltaDtAlta.setText(data);

                    btAltaOk.setEnabled(!tvAltaDtAlta.toString().trim().equals(""));
                }
            };

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