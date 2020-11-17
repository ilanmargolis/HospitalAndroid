package com.example.hospital.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.hospital.R;

public class ValidaCpf implements TextWatcher {

    private Context context;
    private View view;
    private EditText cpf;

    public ValidaCpf(Context context, View view, EditText cpf) {
        this.context = context;
        this.view = view;
        this.cpf = cpf;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean habilita = Utils.isCPFValido(cpf.getText().toString());

        if (habilita) {
            cpf.setTextColor(context.getColor(R.color.preto));
        } else {
            cpf.setTextColor(context.getColor(R.color.vermelho));
        }

        if (view instanceof ImageButton) {
            if (habilita) {
                view.setBackgroundColor(context.getColor(R.color.azul_5));
            } else {
                view.setBackgroundColor(context.getColor(R.color.vermelho));
            }
        }

        view.setEnabled(habilita);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
