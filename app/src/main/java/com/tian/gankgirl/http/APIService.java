package com.tian.gankgirl.http;

import com.tian.gankgirl.bean.MainBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 接口
 */
public interface APIService {
    String BaseUrl = "http://gank.io/api/data/";
    @GET("{category}/10/{page}")
    Observable<MainBean> getMainData(@Path("category")String category,@Path("page") int page);

}
