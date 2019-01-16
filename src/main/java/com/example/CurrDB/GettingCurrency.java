package com.example.CurrDB;
import okhttp3.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.concurrent.CountDownLatch;

public class GettingCurrency {
    String res="";
    int status=0;
    public String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();
        return connect(request);
    }

    public String connect(Request request) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                status=-1;
                countDownLatch.countDown();
            }


            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {

                    res=response.body().string();

                    status=1;
                    countDownLatch.countDown();
                } else {
                    status=0;
                    res=response.body().string();
                    countDownLatch.countDown();
                }
            }});
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        return res;
    }




}
