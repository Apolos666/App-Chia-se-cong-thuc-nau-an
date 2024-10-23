package com.example.appchiasecongthucnauan.apis;

import com.example.appchiasecongthucnauan.models.LoginDto;
import com.example.appchiasecongthucnauan.models.RegisterDto;
import com.example.appchiasecongthucnauan.models.RecipeCategoryDto;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Header;
import java.util.List;
import okhttp3.RequestBody;

public interface ApiService {
    @POST("auth/register")
    Call<String> register(@Body RegisterDto registerDto);

    @POST("auth/login")
    Call<String> login(@Body LoginDto loginDto);

    @GET("api/recipe-categories")
    Call<List<RecipeCategoryDto>> getRecipeCategories();

    @Multipart
    @POST("api/recipes")
    Call<Void> createRecipe(
            @Header("Authorization") String token,
            @Part("Title") RequestBody title,
            @Part("Ingredients") RequestBody ingredients,
            @Part("Instructions") RequestBody instructions,
            @Part("RecipeCategoryId") RequestBody recipeCategoryId,
            @Part List<MultipartBody.Part> mediaFiles);
}
