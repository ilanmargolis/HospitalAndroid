package com.example.hospital.service;

import com.example.hospital.model.Unidade;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UnidadeService {

    @POST("unidade")
    Call<Unidade> create(@Body Unidade unidade);

    @PUT("unidade/{id}")
    Call<Unidade> update(@Path("id") long id, @Body Unidade unidade);

    @GET("unidade")
    Call<List<Unidade>> getAll();

    @GET("unidade/{id}")
    Call<Unidade> get(@Path("id") long id);

    @DELETE("unidade/{id}")
    Call<Unidade> delete(@Path("id") long id);
}