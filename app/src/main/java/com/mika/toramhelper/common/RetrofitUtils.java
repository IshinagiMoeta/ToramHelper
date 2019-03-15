package com.mika.toramhelper.common;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @creater:moeta
 * @create time:2019/3/15 11:00
 * @description:
 */
public class RetrofitUtils {

    private static RetrofitUtils instance;

    private static Retrofit retrofit;

    private RetrofitUtils(Context ctx) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Common.getBaseUrl(ctx))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitUtils getInstance(Context ctx) {
        if (instance == null) {
            instance = new RetrofitUtils(ctx);
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
