package com.example.curious.network;

import com.example.curious.model.PushshiftDataObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PushshiftClient {
    @GET("/reddit/search/comment")
    Call<PushshiftDataObject> getCommentData(@Query("ids") String id);
}
