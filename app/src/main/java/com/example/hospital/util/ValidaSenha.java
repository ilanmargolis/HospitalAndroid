package com.example.hospital.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.hospital.R;

import static com.google.android.material.color.MaterialColors.getColor;

public class ValidaSenha implements TextWatcher {

    private Context context;
    private View view;
    private EditText senha;
    private EditText confirma;

    public ValidaSenha(Context context, View view, EditText senha, EditText confirma) {
        this.context = context;
        this.view = view;
        this.senha = senha;
        this.confirma = confirma;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String senhaAux = senha.getText().toString().trim();
        String confirmaAux = confirma.getText().toString().trim();

        Boolean habilita = !senhaAux.equals("") &&
                !confirmaAux.equals("") &&
                senhaAux.equals(confirmaAux);

        if (habilita) {
            confirma.setTextColor(context.getColor(R.color.preto));
        } else {
            confirma.setTextColor(context.getColor(R.color.vermelho));
        }

        view.setEnabled(habilita);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
