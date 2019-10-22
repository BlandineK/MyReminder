package com.moringashool.myreminder.network;

import com.moringashool.myreminder.models.YelpRemindersSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpApi {

    @GET("businesses/search")
    Call<YelpRemindersSearchResponse> getReminders(
            @Query("location") String location,
            @Query("term") String term
    );
}
