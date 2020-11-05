package com.example.hospital.service;

import com.example.hospital.model.Medicamento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MedicamentoService {

    @POST("medicamento")
    Call<Medicamento> create(@Body Medicamento medicamento);

    @PUT("medicamento")
    Call<Medicamento> updateMedicamento(@Query("id") long id, @Body Medicamento medicamento);

    @GET("medicamento")
    Call<List<Medicamento>> getAllMedicamento();

    @GET("medicamento")
    Call<Medicamento> getMedicamento(@Query("id") long id);

    @DELETE("medicamento")
    Call<Medicamento> delete(@Query("id") long id);
}
