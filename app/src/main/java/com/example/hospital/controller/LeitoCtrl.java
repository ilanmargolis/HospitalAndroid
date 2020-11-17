package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Unidade;

import java.util.List;

public class LeitoCtrl {

    private final Context context;

    public LeitoCtrl(Context context) {
        this.context = context;
    }

    public String insert(Leito leito){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.leitoDao().insert(leito);

            return "Leito incluída com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir leito: " + e;
        }
    }

    public String update(Leito leito){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.leitoDao().update(leito);

            return "Leito alterada com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar leito: " + e;
        }
    }

    public String delete(Leito leito){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.leitoDao().delete(leito);

            return "Leito excluída com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir leito: " + e;
        }
    }

    public List<Leito> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.leitoDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Leito> getLeitoUnidadeSetor(long unidade_id, long setor_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.leitoDao().getLeitoUnidadeSetor(unidade_id, setor_id);
        } catch (Exception e) {
            return null;
        }
    }
}
