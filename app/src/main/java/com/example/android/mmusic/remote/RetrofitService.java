package com.example.android.mmusic.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by android on 11/15/17.
 */

public interface RetrofitService {


    @GET("response.json")
    Call<ResponseBody> getData();


//    @GET("response.json")
//    fun getData(): Call<PlaceResponse>
//    @POST("user/login")
//    fun loginUp(@Body body: UserLoginBody): Call<LoginResponse>
//
//    @POST("user/login")
//    fun loginUp(@Body body: FacebookLoginBody): Call<LoginResponse>
//
//    @POST("user/logout")
//    fun logout(@Header("X-Token") header: String): Call<ResponseBody>
//
//    @PATCH("user/{userId}")
//    fun deactivateAccount(@Header("X-Token") header: String,
//                          @Path("userId") userId: String): Call<ResponseBody>
//
//    @POST("user/register")
//    fun registrationUSer(@Body body: UserRegistrationBody): Call<LoginResponse>
//
//    @GET("user/{user_id}/items")
//    fun getTradedItemsData(@Path("user_id") userId: Int): Call<List<TradedItemR>>
}
