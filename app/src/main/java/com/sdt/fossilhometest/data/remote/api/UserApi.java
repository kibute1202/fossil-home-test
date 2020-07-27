package com.sdt.fossilhometest.data.remote.api;

import com.sdt.fossilhometest.data.model.Result;
import com.sdt.fossilhometest.data.model.ReputationHistory;
import com.sdt.fossilhometest.data.model.User;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("users?site=stackoverflow")
    Single<Result<User>> getUsers(@Query("page") int page, @Query("pagesize") int pageSize);

    @GET("users/{userId}/reputation-history?site=stackoverflow")
    Single<Result<ReputationHistory>> getReputationHistories(@Path("userId") Integer userId,
                                                             @Query("page") int page,
                                                             @Query("pagesize") int pageSize);

}
