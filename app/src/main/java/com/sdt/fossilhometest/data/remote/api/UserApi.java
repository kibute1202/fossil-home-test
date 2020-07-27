package com.sdt.fossilhometest.data.remote.api;

import com.sdt.fossilhometest.data.model.api.Result;
import com.sdt.fossilhometest.data.model.db.User;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {

    @GET("users?site=stackoverflow")
    Single<Result<User>> getUsers(@Query("page") int page, @Query("pagesize") int pageSize);

    @GET("users/{userId}/reputation-history?site=stackoverflow")
    Single<Result<User>> getReputationHistories(@Query("page") int page, @Query("pagesize") int pageSize);

}
