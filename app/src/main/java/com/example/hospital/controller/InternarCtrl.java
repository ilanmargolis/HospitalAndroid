package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Internado;

import java.util.List;

public class InternarCtrl {

    private final Context context;

    public InternarCtrl(Context context) {
        this.context = context;
    }

    public String insert(Internado internado){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.internarDao().insert(internado);

            return "Internado incluído com sucesso";
        } catch (Exception e) {
            return "Erro ao inserir internado: " + e;
        }
    }

    public String update(Internado internado){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.internarDao().update(internado);

            return "Internado alterado com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar internado: " + e;
        }
    }

    public String delete(Internado internado){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            db.internarDao().delete(internado);

            return "Internado excluído com sucesso";
        } catch (Exception e) {
            return "Erro ao excluir internado: " + e;
        }
    }

    public List<Internado> getAll(){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.internarDao().getAll();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Internado> getLeitoUnidadeSetor(long unidade_id, long setor_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.internarDao().getInternadoUnidadeSetor(unidade_id, setor_id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Internado> getInternadoPaciente(long unidade_id, long setor_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.internarDao().getInternadoPaciente(unidade_id, setor_id);
        } catch (Exception e) {
            return null;
        }
    }

}