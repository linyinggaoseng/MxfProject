package com.mxf.mxfproject.server;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mxf on 17/2/15.
 */

public class HttpManager {
    BaseHttpRequest baseHttpRequest;
    ServerCallBack  servercb;
    private OkHttpClient okHttpClient;
    private Gson gson;
    private ProgressDialog progressDialog;
    // 超时时间
    public static final int TIMEOUT = 1000 * 15;
    public HttpManager(BaseHttpRequest httpRequest,ServerCallBack serverCb){
        this.baseHttpRequest = httpRequest;
        this.servercb = serverCb;
        okHttpClient = new OkHttpClient();
        // 设置超时时间
        okHttpClient.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS).build();
        gson = new GsonBuilder().create();
    }

    public void performRequest(Activity content){

        try {
            JSONObject json = this.baseHttpRequest.genParams();
            /**
             * 创建请求的参数body
             */
            FormBody.Builder builder = new FormBody.Builder();
            if (null != json){
                Iterator<String> sIterator = json.keys();
                while (sIterator.hasNext()){
                    // 获得key
                    String key = sIterator.next();
                    // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                    String value;
                    try {
                        value = json.getString(key);
                        builder.add(key, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            String url = this.baseHttpRequest.genUrl();
            Request request = new Request.Builder().url(url).post(builder.build())
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    Log.e("lbk", "HttpUtils:====>" + e.toString());
                    servercb.onFail("网络异常");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    // 网络请求成功
                    Object resultObj = null;
                    String result = response.body().string();
                    if (null != baseHttpRequest.getMclass()) {
                        try {
                            resultObj = gson.fromJson(result, baseHttpRequest.getMclass());
                            servercb.onSucess(resultObj);
                        } catch (Exception e) {
                            Log.i("lbk", e.toString());
                        }
                        Log.i("lbk", "HttpUtils:====>" + result);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
