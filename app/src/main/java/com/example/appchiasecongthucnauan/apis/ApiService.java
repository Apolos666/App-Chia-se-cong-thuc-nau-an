package com.example.appchiasecongthucnauan.apis;

import com.example.appchiasecongthucnauan.models.LoginDto;
import com.example.appchiasecongthucnauan.models.RegisterDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/register")
    Call<String> register(@Body RegisterDto registerDto);

    @POST("auth/login")
    Call<String> login(@Body LoginDto loginDto);
}
