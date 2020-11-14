package com.example.hospital.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hospital.R;
import com.example.hospital.config.RetrofitConfig;
import com.example.hospital.controller.FuncionarioCtrl;
import com.example.hospital.controller.SetorCtrl;
import com.example.hospital.controller.UnidadeCtrl;
import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Setor;
import com.example.hospital.repository.ResultEvent;
import com.example.hospital.util.Utils;
import com.example.hospital.util.ValidaSenha;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuncionarioDadosActivity extends AppCompatActivity {

    private EditText etFuncionarioNome, etFuncionarioEmail, etFuncionarioSenha, etFuncionarioConfirmaSenha;
    private RadioButton rbFuncionarioAdmin, rbFuncionarioRecep;
    private Button btFuncionarioOk, btFuncionarioCancelar;
    private Spinner spFuncionarioSetor;
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
        spFuncionarioSetor = (Spinner) findViewById(R.id.spFuncionarioSetor);
        btFuncionarioOk = (Button) findViewById(R.id.btFuncionarioOk);
        btFuncionarioCancelar = (Button) findViewById(R.id.btFuncionarioCancelar);

        // Habilia o btFuncionarioOk caso a senha seja digitada corretamente
        etFuncionarioSenha.addTextChangedListener(new ValidaSenha(this, btFuncionarioOk, etFuncionarioSenha, etFuncionarioConfirmaSenha));
        etFuncionarioConfirmaSenha.addTextChangedListener(new ValidaSenha(this, btFuncionarioOk, etFuncionarioSenha, etFuncionarioConfirmaSenha));

        if (!carregaSpinner()) {
            Toast.makeText(this, "É necessário incluir unidades de atendimento e/ou setor!", Toast.LENGTH_LONG).show();

            finish();
        };

        preparaDados();

        btFuncionarioOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomeFuncionario = etFuncionarioNome.getText().toString().trim();

                if (funcionario.getId() == 0) { // inclusão
                    if (!nomeFuncionario.equals("")) {

                        funcionario.setNome(nomeFuncionario);
                        funcionario.setEmail(etFuncionarioEmail.getText().toString().trim());
                        funcionario.setSenha(etFuncionarioSenha.getText().toString().trim());

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(FuncionarioDadosActivity.this, "É necessário informar o nome do funcionário!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!funcionario.getNome().equals(etFuncionarioNome.getText().toString().trim()) ||
                            !funcionario.getEmail().equals(etFuncionarioEmail.getText().toString().trim()) ||
                            (funcionario.getSenha() != null && !funcionario.getSenha().equals(etFuncionarioSenha.getText().toString().trim())) ||
                            !funcionario.getSetor().toString().equals(spFuncionarioSetor.toString())) {

                        funcionario.setNome(nomeFuncionario);
                        funcionario.setEmail(etFuncionarioEmail.getText().toString().trim());
                        funcionario.setSenha(etFuncionarioSenha.getText().toString().trim());

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

    private boolean carregaSpinner() {
        List<Setor> setorList = null;

        // Carrega todos os departamentos no spinner
        if (Utils.hasInternet(this)) {

        } else {
            setorList = new SetorCtrl(this).getAll();
        }

        if (setorList.size() > 0) {
            ArrayAdapter aa = new ArrayAdapter<>(FuncionarioDadosActivity.this, android.R.layout.simple_spinner_item, setorList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spFuncionarioSetor.setAdapter(aa);
        } else {
            return false;
        }

        spFuncionarioSetor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                funcionario.setSetor((Setor) spFuncionarioSetor.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                funcionario.setSetor(null);
            }
        });

        return true;
    }

    private void preparaDados() {
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        if (funcionario.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etFuncionarioNome.setText(funcionario.getNome());
            etFuncionarioEmail.setText(funcionario.getEmail());
            etFuncionarioSenha.setText(funcionario.getSenha());
            etFuncionarioConfirmaSenha.setText(funcionario.getSenha());

            spFuncionarioSetor.setSelection(Utils.getIndex(spFuncionarioSetor, funcionario.getSetor().getNome()));
        }
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
}