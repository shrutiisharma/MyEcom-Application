package com.streamliners.myecomapp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MapServices {
    private static MapInterface MapInterface;

    public static MapInterface getClient() {
        if (MapInterface == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okclient = new OkHttpClient();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            Retrofit client;
            client = new Retrofit.Builder().baseUrl(MapUtility.MAP_URL).client(okclient.newBuilder().connectTimeout(100, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).addInterceptor(logging).build()).addConverterFactory(GsonConverterFactory.create(gson)).build();
            MapInterface = client.create(MapInterface.class);
        }
        return MapInterface;
    }

    public interface MapInterface {

        @GET("/maps/api/geocode/json")
        Call<MapRequestResponse> MapData(@Query("latlng") String latlng);

    }
}