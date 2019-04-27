package iss.vanil.retrofitex.service;

import iss.vanil.retrofitex.entity.Account;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountService {

    @FormUrlEncoded
    @POST("/account/create")
    Call<ResponseBody> createAccount(@Field("name") String name);

    @FormUrlEncoded
    @POST("/account/get_key")
    Call<ResponseBody> getKey(@Field("name") String name);

    @POST("/account/enter")
    Call<ResponseBody> enter(@Body Account account);

}
