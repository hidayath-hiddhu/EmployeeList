package com.example.retrofitsample.apis;

import com.example.retrofitsample.models.EmployeeDetail;
import com.example.retrofitsample.models.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersApiService {

    @GET("users")
    Call<List<Users>> getUsers();
    @GET("users/1")
    Call<Users> getUserDetails();

}
// In this Interface we can use get, post, delete query request results or update results etc using individual methods
