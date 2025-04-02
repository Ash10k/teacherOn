package com.ash.teacheron.retrofit.builders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitBuilder
{
    private static Retrofit retrofit;
    //private static final String BASE_URL = "http://3.94.123.180/api/";
    //private static final String BASE_URL = "http://18.207.152.170/api/";
    private static final String BASE_URL = "http://3.94.98.4/api/";
    public static Retrofit build()
    {
        try
        {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(3, TimeUnit.MINUTES) // Increase connect timeout to 10 minutes
                    .writeTimeout(3, TimeUnit.MINUTES)   // Increase write timeout to 10 minutes
                    .readTimeout(3, TimeUnit.MINUTES);    // Increase read timeout to 10 minutes
            // code for logging request sent by Retrofit
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
            OkHttpClient okHttpClient = builder.build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return retrofit;
    }
}