package com.example.hospital.service;

import com.example.hospital.model.Funcionario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FuncionarioService {

    @POST("funcionario")
    Call<Funcionario> create(@Body Funcionario funcionario);

    @PUT("funcionario/{id}")
    Call<Funcionario> update(@Path("id") long id, @Body Funcionario funcionario);

    @GET("funcionario")
    Call<List<Funcionario>> getAll();

    @GET("funcionario/{id}")
    Call<Funcionario> get(@Path("id") long id);

    @DELETE("funcionario/{id}")
    Call<Funcionario> delete(@Path("id") long id);
}