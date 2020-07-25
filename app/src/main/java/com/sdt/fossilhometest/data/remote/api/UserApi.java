package com.sdt.fossilhometest.data.remote.api;

import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {

    @GET("users?site=stackoverflow")
    Single<List<User>> getUsers(@Query("page") int page, @Query("pagesize") int pageSize);

}
