package com.example.hospital.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Funcionario;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;
import com.example.hospital.util.ValidaSenha;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioDadosActivity extends AppCompatActivity {

    private EditText etFuncionarioNome, etFuncionarioEmail, etFuncionarioSenha, etFuncionarioConfirmaSenha;
    private RadioButton rbFuncionarioAdmin, rbFuncionarioRecep;
    private Button btFuncionarioOk, btFuncionarioCancelar;
    private Funcionario funcionario;
    private byte setor = -1;

    private String msg = null;
    private String tipoOpcao[][] = {{"incluído", "alterado", "excluído"}, {"incluir", "alterar", "excluir"}};
    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_dados);

        etFuncionarioNome = (EditText) findViewById(R.id.etFuncionarioNome);
        etFuncionarioEmail = (EditText) findViewById(R.id.etFuncionarioEmail);
        etFuncionarioSenha = (EditText) findViewById(R.id.etFuncionarioSenha);
        etFuncionarioConfirmaSenha = (EditText) findViewById(R.id.etFuncionarioConfirmaSenha);
        rbFuncionarioAdmin = (RadioButton) findViewById(R.id.rbFuncionarioAdmin);
        rbFuncionarioRecep = (RadioButton) findViewById(R.id.rbFuncionarioRecep);
        btFuncionarioOk = (Button) findViewById(R.id.btFuncionarioOk);
        btFuncionarioCancelar = (Button) findViewById(R.id.btFuncionarioCancelar);

        // Habilia o btFuncionarioOk caso a senha seja digitada corretamente
        etFuncionarioSenha.addTextChangedListener(new ValidaSenha(btFuncionarioOk, etFuncionarioSenha, etFuncionarioConfirmaSenha));
        etFuncionarioConfirmaSenha.addTextChangedListener(new ValidaSenha(btFuncionarioOk, etFuncionarioSenha, etFuncionarioConfirmaSenha));

        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        if (funcionario.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etFuncionarioNome.setText(funcionario.getNome());
            etFuncionarioEmail.setText(funcionario.getEmail());
            etFuncionarioSenha.setText(funcionario.getSenha());
            etFuncionarioConfirmaSenha.setText(funcionario.getSenha());

            if (funcionario.getSetor() == 0) {
                rbFuncionarioAdmin.setChecked(true);
            } else {
                rbFuncionarioRecep.setChecked(true);
            }
        }

        btFuncionarioOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomeFuncionario = etFuncionarioNome.getText().toString().trim();

                if (funcionario.getId() == 0) { // inclusão
                    if (!nomeFuncionario.equals("")) {

                        funcionario.setNome(nomeFuncionario);
                        funcionario.setEmail(etFuncionarioEmail.getText().toString().trim());
                        funcionario.setSenha(etFuncionarioSenha.getText().toString().trim());
                        funcionario.setSetor(setor);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(FuncionarioDadosActivity.this, "É necessário informar o nome do funcionário!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!funcionario.getNome().equals(etFuncionarioNome.getText().toString().trim()) ||
                            !funcionario.getEmail().equals(etFuncionarioEmail.getText().toString().trim()) ||
                            (funcionario.getSenha() != null && !funcionario.getSenha().equals(etFuncionarioSenha.getText().toString().trim())) ||
                            funcionario.getSetor() != setor) {

                        funcionario.setNome(nomeFuncionario);
                        funcionario.setEmail(etFuncionarioEmail.getText().toString().trim());
                        funcionario.setSenha(etFuncionarioSenha.getText().toString().trim());
                        funcionario.setSetor(setor);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(FuncionarioDadosActivity.this, "Não houve alteração nos dados!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btFuncionarioCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(FuncionarioDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (funcionario.getId() == 0) { // inclusão
            btFuncionarioOk.setText("Incluir");
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
                    msg = "Funcionário " + tipoOpcao[0][tipoCrud] + " com sucesso";
                }

                @Override
                public void onFail(String message) {
                    msg = message;
                }
            });
        } else {
            if (tipoCrud == CRUD_INC) {
                msg = new FuncionarioCtrl(FuncionarioDadosActivity.this).insert(funcionario);
            } else if (tipoCrud == CRUD_UPD) {
                msg = new FuncionarioCtrl(FuncionarioDadosActivity.this).update(funcionario);
            } else if (tipoCrud == CRUD_DEL) {
                msg = new FuncionarioCtrl(FuncionarioDadosActivity.this).delete(funcionario);
            }
        }

        Toast.makeText(FuncionarioDadosActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void crudDados(byte tipoCrud, ResultEvent resultEvent) {

        Call<Funcionario> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getFuncionarioService().create(funcionario);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getFuncionarioService().update(funcionario.getId(), funcionario);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getFuncionarioService().delete(funcionario.getId());

        call.enqueue(new Callback<Funcionario>() {
            @Override
            public void onResponse(Call<Funcionario> call, Response<Funcionario> response) {
                funcionario = response.body();

                resultEvent.onResult(funcionario);
            }

            @Override
            public void onFailure(Call<Funcionario> call, Throwable t) {
                resultEvent.onFail("Erro ao " + tipoOpcao[1][tipoCrud] + " funcionário");
            }
        });
    }

    public void onRadioButtonClickSetor(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rbFuncionarioAdmin:
                if (checked) {
                    setor = 0;
                }

                break;
            case R.id.rbFuncionarioRecep:
                if (checked) {
                    setor = 1;
                }

                break;
        }
    }

}