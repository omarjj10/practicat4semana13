package com.example.practicat4semana13.factories;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit build(){
        return new Retrofit.Builder()
                .baseUrl("https://62851d593060bbd34744fa98.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
