package com.example.hospital.service;

import com.example.hospital.model.Setor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SetorService {

    @POST("setor")
    Call<Setor> create(@Body Setor setor);

    @PUT("setor/{id}")
    Call<Setor> update(@Path("id") long id, @Body Setor setor);

    @GET("setor")
    Call<List<Setor>> getAll();

    @GET("setor/{id}")
    Call<Setor> get(@Path("id") long id);

    @DELETE("setor/{id}")
    Call<Setor> delete(@Path("id") long id);
}