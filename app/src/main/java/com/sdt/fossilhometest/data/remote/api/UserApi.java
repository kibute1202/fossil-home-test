package com.sdt.fossilhometest.data.remote.api;

import com.sdt.fossilhometest.data.model.api.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {

    @GET("users?site=stackoverflow")
    Call<UserResponse> getUsers(@Query("page") int page, @Query("pagesize") int pageSize);

}
