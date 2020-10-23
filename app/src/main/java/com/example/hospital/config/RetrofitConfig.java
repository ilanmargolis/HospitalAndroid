package com.example.hospital.config;

import com.example.hospital.service.UnidadeService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private Retrofit retrofit;

    public RetrofitConfig() {
        retrofit = new Retrofit.Builder()
      //          .baseUrl("https://professor-allocation.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public UnidadeService getUnidadeService() {
        return retrofit.create(UnidadeService.class);
    }
}