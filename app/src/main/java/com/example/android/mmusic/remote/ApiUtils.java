package com.example.android.mmusic.remote;



public class ApiUtils {

    public static final String BASE_URL = "http://zavtrakov.eurodir.ru/";

//    public static final String X_AUTH_TOKEN = "x-auth-token";
//
//    public static final String PAYMENT_BASE_URL = "https://sbpaymentservices.payfort.com/FortAPI/paymentApi";

    public static RetrofitService getRetrofitService() {
        return getRetrofitService(false);
    }

    public static RetrofitService getRetrofitService(boolean recreate) {
        return RetrofitClient.getClient(BASE_URL, recreate).create(RetrofitService.class);
    }
}