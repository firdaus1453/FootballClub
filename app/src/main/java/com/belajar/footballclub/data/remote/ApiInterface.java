package com.belajar.footballclub.data.remote;

import com.belajar.footballclub.data.model.ResponseAllTeam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Muhammad Firdaus on 22/02/2020.
 */
public interface ApiInterface {
    @GET("api/v1/json/1/search_all_teams.php")
    Call<ResponseAllTeam> getAllTeam(
            @Query("l") String nameLeague
    );
}
