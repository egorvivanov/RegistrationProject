package edu.example.egorvivanov.registrationproject.api;

import edu.example.egorvivanov.registrationproject.model.ResponseData;
import edu.example.egorvivanov.registrationproject.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AcademyApi {

    @POST("registration")
    Call<Void> registration(@Body User user);

    @GET("user")
    Call<ResponseData<User>> authorization();

}
