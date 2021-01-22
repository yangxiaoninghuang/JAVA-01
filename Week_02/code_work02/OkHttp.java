package com.geek.work02;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttp  {
    public static void main(String[] args) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url("http://localhost:8801").build();

        Response execute = okHttpClient.newCall(request).execute();
        if (execute.isSuccessful()){
            System.out.println(execute.body().string());
        }


    }
}
