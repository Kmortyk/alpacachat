package iss.vanil.retrofitex.service;

import java.util.List;

import iss.vanil.retrofitex.entity.Account;
import iss.vanil.retrofitex.entity.Message;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MessageService {

    @GET("/message/get_all")
    Call<List<Message>> getMessages();

    @GET("/message/get")
    Call<List<Message>> getMessages(@Query("from") int from, @Query("to") int to);

    @POST("/message/get")
    Call<List<Message>> getMessages(@Body Account account);

    @POST("/message/send")
    Call<ResponseBody> sendMessage(@Body Message message);

}
