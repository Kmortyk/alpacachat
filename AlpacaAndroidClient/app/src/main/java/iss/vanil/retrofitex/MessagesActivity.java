package iss.vanil.retrofitex;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iss.vanil.retrofitex.entity.Account;
import iss.vanil.retrofitex.entity.Message;
import iss.vanil.retrofitex.service.AccountService;
import iss.vanil.retrofitex.service.MessageService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagesActivity extends AppCompatActivity {

    private final static String baseUrl = "http://www.alpaca.host";
    private String accountKey;

    private static List<Message> messages;
    private EditText messageEditText;


    private RecyclerView recyclerView;
    private MessagesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messages = new ArrayList<>();
        adapter = new MessagesRecyclerAdapter(messages);

        recyclerView = findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MessageItemDecoration((int)dp2px(8)));

        messageEditText = findViewById(R.id.messageEditText);

        accountKey = getIntent().getStringExtra("account_key");

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener((v) -> sendMessage());

        loadMessages();

    }

    private void loadMessages() {

        messages.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessageService service = retrofit.create(MessageService.class);
        Call<List<Message>> call = service.getMessages(new Account(null, accountKey));

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                if(response.body() != null) {
                    messages.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }else{
                    makeToast("Failure: response body is null.");
                }

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
                makeToast("Failure: onResponse failure.");
            }

        });

    }

    private void sendMessage() {

        String message = messageEditText.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessageService service = retrofit.create(MessageService.class);
        Call<ResponseBody> call = service.sendMessage(new Message(accountKey, message));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loadMessages();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                makeToast("Failure: onResponse failure.");
            }
        });

    }

    private void makeToast(String message) {
        Toast.makeText(MessagesActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}
