package com.example.hospital.controller;

import android.content.Context;

import com.example.hospital.config.RoomConfig;
import com.example.hospital.model.Leito;

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
            if (db.leitoDao().getInternamentoLeito(leito.getId()).size() > 0) {
                return "Não é possível alterar esse leito, ele está sendo utilizado no internamento!";
            } else {
                db.leitoDao().update(leito);
            }

            return "Leito alterada com sucesso";
        } catch (Exception e) {
            return "Erro ao alterar leito: " + e;
        }
    }

    public String delete(Leito leito){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            if (db.leitoDao().getInternamentoLeito(leito.getId()).size() > 0) {
                return "Não é possível excluir esse leito, ele está sendo utilizado no internamento!";
            } else {
                db.leitoDao().delete(leito);
            }

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

    public Leito getById(long id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.leitoDao().getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Leito> getBySetores(long setor_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.leitoDao().getBySetor(setor_id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Leito> getInternamentoLeito(long id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            return db.leitoDao().getInternamentoLeito(id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Leito> getLeitosDisponiveis(long unidade_id, long setor_id){

        RoomConfig db = RoomConfig.getInstance(context);

        try {
            List<Leito> todos = db.leitoDao().getLeitosUnidadeSetor(unidade_id, setor_id);

            List<Leito> ocupados = db.leitoDao().getLeitosOcupados(unidade_id, setor_id);

            todos.removeAll(ocupados);

            return todos;
        } catch (Exception e) {
            return null;
        }
    }
}
