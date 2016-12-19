package com.programmer.programminglanguages;

import com.programmer.programminglanguages.entities.Tech;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by mac on 16.12.16.
 */
public interface Services {

    @GET("/api/techs/{id}")
    Call<Tech> getTech(@Path("id")String id);


    @GET("/api/techs")
    Call<List<Tech>> getAllTech();

}
