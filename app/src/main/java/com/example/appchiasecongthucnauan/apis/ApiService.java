package com.example.appchiasecongthucnauan.apis;

import com.example.appchiasecongthucnauan.models.LoginDto;
import com.example.appchiasecongthucnauan.models.RegisterDto;
import com.example.appchiasecongthucnauan.models.RecipeCategoryDto;
import com.example.appchiasecongthucnauan.models.RecipeDto;
import com.example.appchiasecongthucnauan.models.UserDto;
import com.example.appchiasecongthucnauan.models.CommentDto;
import com.example.appchiasecongthucnauan.models.CreateCommentRequest;

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
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/auth/register")
    Call<String> register(@Body RegisterDto registerDto);

    @POST("api/auth/login")
    Call<String> login(@Body LoginDto loginDto);

    @GET("api/auth/user")
    Call<UserDto> getUser(@Header("Authorization") String token);

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

    @GET("api/recipes")
    Call<List<RecipeDto>> getRecipes();

    @GET("api/recipes/{id}")
    Call<RecipeDto> getRecipe(@Path("id") String recipeId);

    @POST("api/comments")
    Call<CommentDto> createComment(@Header("Authorization") String token, @Body CreateCommentRequest request);
}
