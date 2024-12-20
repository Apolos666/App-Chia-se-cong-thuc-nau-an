package com.example.appchiasecongthucnauan.apis;

import com.example.appchiasecongthucnauan.models.LoginDto;
import com.example.appchiasecongthucnauan.models.RecipeDto;
import com.example.appchiasecongthucnauan.models.RegisterDto;
import com.example.appchiasecongthucnauan.models.RecipeCategoryDto;
import com.example.appchiasecongthucnauan.models.UpdateRecipeDto;
import com.example.appchiasecongthucnauan.models.UpdateUserDto;
import com.example.appchiasecongthucnauan.models.explore.RecentRecipeDto;
import com.example.appchiasecongthucnauan.models.explore.TrendingRecipeDto;
import com.example.appchiasecongthucnauan.models.user.UserDto;
import com.example.appchiasecongthucnauan.models.CommentDto;
import com.example.appchiasecongthucnauan.models.CreateCommentRequest;
import com.example.appchiasecongthucnauan.models.SendMessageRequest;
import com.example.appchiasecongthucnauan.models.ConversationDto;
import com.example.appchiasecongthucnauan.models.MessageDto;
import com.example.appchiasecongthucnauan.models.userfollow.FollowStatusResponse;
import com.example.appchiasecongthucnauan.models.search.SearchResultDto;
import com.example.appchiasecongthucnauan.models.BookmarkResponse;
import com.example.appchiasecongthucnauan.models.BookmarkDto;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Part;
import retrofit2.http.Header;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.UUID;
import android.util.Log;

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
        Call<RecipeDto> getRecipe(@Path("id") String recipeId, @Header("Authorization") String token);

        @POST("api/comments")
        Call<CommentDto> createComment(@Header("Authorization") String token, @Body CreateCommentRequest request);

        @GET("api/users/{id}")
        Call<UserDto> getUserById(@Path("id") String userId, @Header("Authorization") String token);

        @PUT("api/users/{id}")
        Call<Void> updateUser(@Path("id") String userId, @Header("Authorization") String token,
                        @Body UpdateUserDto updateUserDto);

        @POST("api/chat/start")
        Call<String> startConversation(@Header("Authorization") String token, @Query("recipientId") String recipientId);

        @POST("api/chat/send")
        Call<Void> sendMessage(@Header("Authorization") String token, @Body SendMessageRequest request);

        @GET("api/chat/conversations")
        Call<List<ConversationDto>> getConversations(@Header("Authorization") String token);

        @GET("api/chat/messages/{conversationId}")
        Call<List<MessageDto>> getMessages(@Header("Authorization") String token,
                        @Path("conversationId") String conversationId);

        @POST("api/users-follow/{id}/follow")
        Call<Void> followUser(@Path("id") String userId, @Header("Authorization") String token);

        @DELETE("api/users-follow/{id}/unfollow")
        Call<Void> unfollowUser(@Path("id") String userId, @Header("Authorization") String token);

        @GET("api/users-follow/{id}/follow-status")
        Call<FollowStatusResponse> getFollowStatus(@Path("id") String userId, @Header("Authorization") String token);

        @GET("api/search")
        Call<SearchResultDto> search(@Header("Authorization") String token, @Query("q") String searchTerm);

        @GET("api/bookmarks/check/{recipeId}")
        Call<BookmarkResponse> getBookmarkStatus(@Header("Authorization") String token, @Path("recipeId") String recipeId);

        @POST("api/bookmarks/{recipeId}")
        Call<Void> addBookmark(@Header("Authorization") String token, @Path("recipeId") String recipeId);

        @DELETE("api/bookmarks/{recipeId}")
        Call<Void> removeBookmark(@Header("Authorization") String token, @Path("recipeId") String recipeId);

        @GET("api/bookmarks")
        Call<List<BookmarkDto>> getBookmarks(@Header("Authorization") String token);

        @POST("api/recipes/{id}/like")
        Call<Void> likeRecipe(@Path("id") String recipeId, @Header("Authorization") String token);

        @DELETE("api/recipes/{id}/unlike")
        Call<Void> unlikeRecipe(@Path("id") String recipeId, @Header("Authorization") String token);

        @GET("api/recipes/trending")
        Call<List<TrendingRecipeDto>> getTrendingRecipes(@Query("limit") int limit);

        @GET("api/recipes/recent")
        Call<List<RecentRecipeDto>> getRecentRecipes(@Query("limit") int limit);

        @PUT("api/recipes/{id}")
        Call<Void> updateRecipe(
            @Path("id") String recipeId,
            @Header("Authorization") String token,
            @Body UpdateRecipeDto updateRecipeDto
        );

        @DELETE("api/recipes/{id}")
        Call<Void> deleteRecipe(
            @Path("id") String recipeId,
            @Header("Authorization") String token
        );
}
