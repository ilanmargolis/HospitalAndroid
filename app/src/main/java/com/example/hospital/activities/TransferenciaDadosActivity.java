package com.example.hospital.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.controller.AltaCtrl;
import com.example.hospital.controller.LeitoCtrl;
import com.example.hospital.model.Alta;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medico;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Mask;
import com.example.hospital.util.Utils;

import java.util.Date;

import retrofit2.Call;

public class TransferenciaDadosActivity extends AppCompatActivity {

    private TextView tvTransfLeitoCodigo, tvTransfLeitoUnidade, tvTransfLeitoSetor, tvTransfPacienteNome;
    private Button btTransfOk, btTransfCancelar;
    private Alta alta;
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
        setContentView(R.layout.activity_transferencia_dados);

        tvTransfLeitoCodigo = (TextView) findViewById(R.id.tvTransfLeitoCodigo);
        tvTransfLeitoUnidade = (TextView) findViewById(R.id.tvTransfLeitoUnidade);
        tvTransfLeitoSetor = (TextView) findViewById(R.id.tvTransfLeitoSetor);
        tvTransfPacienteNome = (TextView) findViewById(R.id.tvTransfPacienteNome);
        btTransfOk = (Button) findViewById(R.id.btTransfOk);
        btTransfCancelar = (Button) findViewById(R.id.btTransfCancelar);

        // Caso não seja esqolhida nenhuma opção do menu suspenso, ele volta para a tela de menu
        setResult(MenuRecepcaoActivity.TELA_MENU, getIntent());

        preparaDados();

        btTransfOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                } else
                    {
//                    alta = new Alta(internado, medico, dtAlta);

//                    opcaoCrud(CRUD_UPD);

                    finish();
                }
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
                    msg = "Alta médica " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new AltaCtrl(TransferenciaDadosActivity.this).insert(alta);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new AltaCtrl(TransferenciaDadosActivity.this).update(alta);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new AltaCtrl(TransferenciaDadosActivity.this).delete(alta);
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