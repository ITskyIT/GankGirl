package com.tian.gankgirl.http;


import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tian.gankgirl.app.App;
import com.tian.gankgirl.bean.MainBean;
import com.tian.gankgirl.util.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 网络请求
 */
public class RetrofitHelper {
    private static OkHttpClient okHttpClient = null;
    private static APIService mainApis = null;
    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    public RetrofitHelper() {

        initRetrofit();
    }

    private void initRetrofit() {
        initOkhttp();

    }

    private void initOkhttp() {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(Environment.getExternalStorageDirectory(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor =new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };
/*

        builder.addInterceptor(mRewriteCacheControlInterceptor);
        builder.addNetworkInterceptor(mRewriteCacheControlInterceptor);
        builder.addInterceptor(headerInterceptor);

        builder.cache(cache);*/
        //设置超时
      /*  builder.addInterceptor(mRewriteCacheControlInterceptor);
        builder.addNetworkInterceptor(mRewriteCacheControlInterceptor);
        builder .addInterceptor(logInterceptor);
        builder.addInterceptor(new LoggingInterceptor());
        builder.connectTimeout(7676, TimeUnit.MILLISECONDS);
        builder.readTimeout(7676, TimeUnit.MILLISECONDS);
       // builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.cache(cache);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();*/

        okHttpClient = new OkHttpClient.Builder()

                //.addInterceptor(interceptor)
                .addInterceptor(logInterceptor)
               // .addNetworkInterceptor(interceptor)
               // .addInterceptor(headerInterceptor)
               // .cache(cache)
                .build();
        mainApis=getMainApis();
    }

    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isNetConnected(App.getInstance()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetConnected(App.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(App.getInstance())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
    /**
     * 请求响应日志信息，方便debug
     */
    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.i("TAGGGGG",String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i("TAGGGGGssss",String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
    public static APIService getMainApis() {

        //addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        Retrofit fuliRetrofit = new Retrofit.Builder()
                .baseUrl(APIService.BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return fuliRetrofit.create(APIService.class);
    }
    public Observable<MainBean> getAppData(String category,int page) {
        return mainApis.getMainData(category,page);
    }
    Interceptor interceptor = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (NetWorkUtils.isNetConnected(App.getInstance())) {
                Response response = chain.proceed(request);
                int maxAge = 6;
                // 在线缓存在1分钟内可读取
                String cacheControl = request.cacheControl().toString();
                Log.e("yjbo-cache", "在线缓存在1分钟内可读取" + cacheControl);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + CACHE_STALE_SEC).build();
            } else {
                Log.e("yjbo-cache", "离线时缓存时间设置");
                request = request.newBuilder().cacheControl(FORCE_CACHE1)//此处设置了7秒---修改了系统方法
                        .build();
                Response response = chain.proceed(request); //下面注释的部分设置也没有效果，因为在上面已经设置了
                 return response.newBuilder()

                  .build();
                  }
                  }
                  };
    public static final CacheControl FORCE_CACHE1 = new CacheControl.Builder() .onlyIfCached().maxStale(7, TimeUnit.SECONDS).build();

}
