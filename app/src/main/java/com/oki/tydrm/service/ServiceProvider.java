package com.oki.tydrm.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Monica on 2015/7/28.
 */
public class ServiceProvider {

    private static String BASE_URL = "http://www.ty16.cn/index.php";

    private static RetrofitService API_SERVICE;

    public static RetrofitService getInstance() {

        if (API_SERVICE == null) {

            int TIME_OUT = 1000 * 60;

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
            client.setWriteTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
            client.setReadTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
            client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));  //cookie机制

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setClient(new OkClient(client))
                    .setConverter(new GsonConverter(gson))
                    .build();

            API_SERVICE = restAdapter.create(RetrofitService.class);
        }
        return API_SERVICE;
    }
}
