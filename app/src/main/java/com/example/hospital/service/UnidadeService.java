package com.example.hospital.service;

import com.example.hospital.model.Unidade;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UnidadeService {

    @POST
    Call<Unidade> create(@Body Unidade unidade);

    @GET("unidade")
    Call<List<Unidade>> getAllUnidade();

    @GET("unidade")
    Call<Unidade> getUnidade(@Query("id") long id);

    @DELETE("unidade")
    Call<Unidade> delete(@Query("id") long id);
}