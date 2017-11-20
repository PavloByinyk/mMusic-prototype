package com.example.android.mmusic.remote;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private final static String TAG = RetrofitClient.class.getSimpleName();

    private static Retrofit retrofit = null;

    private static String mToken;

    public static Retrofit getClient(String baseUrl, boolean recreate) {

        if (retrofit == null || recreate) {

            OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(new ReceiveCookiesInterceptor())
                    //.addInterceptor(getLoggingInterceptorInstance())
                    //.addInterceptor(new TokenInterceptor())
                    .readTimeout(18, TimeUnit.SECONDS)
                    .connectTimeout(18, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(defaultHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }

    private static class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder();
            //builder.addHeader("Accept","application/json");

            return chain.proceed(builder.build());
        }
    }

//    private static void updateDeviceToken(final String deviceToken){
//        if(StringUtils.isNullOrEmpty(deviceToken))
//            return;
//
//        if(!SharedPreferencesUtils.getInstance().getToken().equals(mToken)){
//            getRetrofitService().setDeviceToken(new DeviceToken(deviceToken)).enqueue(new Callback<PrimitiveServerResponse>() {
//                @Override
//                public void onResponse(Call<PrimitiveServerResponse> call, retrofit2.Response<PrimitiveServerResponse> response) {
//                    mToken = SharedPreferencesUtils.getInstance().getToken();
//                }
//
//                @Override
//                public void onFailure(Call<PrimitiveServerResponse> call, Throwable t) {
//
//                }
//            });
//        }
//    }

//    private static class ReceiveCookiesInterceptor implements Interceptor {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request original = chain.request();
//            Response response = chain.proceed(original);
//            String tokenValue = response.header(X_AUTH_TOKEN);
//
//            if(!StringUtils.isNullOrEmpty(tokenValue)){
//                SharedPreferencesUtils.getInstance().setToken(tokenValue);
//                updateDeviceToken(SharedPreferencesUtils.getInstance().getDeviceToken());
//            }
//
//            return response;
//        }
//    }
//
    private static HttpLoggingInterceptor getLoggingInterceptorInstance() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

}