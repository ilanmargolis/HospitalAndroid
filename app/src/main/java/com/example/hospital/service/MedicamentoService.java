package com.example.hospital.service;

import com.example.hospital.model.Medicamento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicamentoService {

    @POST("medicamento")
    Call<Medicamento> create(@Body Medicamento medicamento);

    @PUT("medicamento/{id}")
    Call<Medicamento> update(@Path("id") long id, @Body Medicamento medicamento);

    @GET("medicamento")
    Call<List<Medicamento>> getAll();

    @GET("medicamento/{id}")
    Call<Medicamento> get(@Path("id") long id);

    @DELETE("medicamento/{id}")
    Call<Medicamento> delete(@Path("id") long id);
}
