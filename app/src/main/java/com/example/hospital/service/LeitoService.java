package com.example.hospital.service;

import com.example.hospital.model.Leito;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LeitoService {

    @POST("leito")
    Call<Leito> create(@Body Leito leito);

    @PUT("leito/{id}")
    Call<Leito> update(@Path("id") long id, @Body Leito leito);

    @GET("leito")
    Call<List<Leito>> getAll();

    @GET("leito/{id}")
    Call<Leito> get(@Path("id") long id);

    @DELETE("leito/{id}")
    Call<Leito> delete(@Path("id") long id);
}
