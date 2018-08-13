package com.intretech.app.base.net;

import com.intretech.app.base.utils.LogUtils;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yq06171 on 2018/7/4.
 */

public class RetrofitFactory {
    private static HttpService retrofitService = null;
    private static final long TIMEOUT = 20;
    public static String BASE_URL = "https://hongmen.memobird.cn/";
    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            // 添加通用的Header

            /*
            这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
            出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
             */
            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtils.printLog("http messgae:"+message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .sslSocketFactory(createSSLSocketFactory()) // https绕开验证
            .hostnameVerifier(new TrustAllHostnameVerifier())
            .build();
    private RetrofitFactory(){

    }

    private static HttpService getRetrofitService(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(HttpService.class);
    }

    public static HttpService getInstance() {
        if(retrofitService == null){
            retrofitService = getRetrofitService();
        }
        return retrofitService;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }


    public static void destroy(){
        if(retrofitService != null){
            retrofitService = null;
        }
    }

}
