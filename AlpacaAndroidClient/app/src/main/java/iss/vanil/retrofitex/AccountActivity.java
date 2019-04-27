package iss.vanil.retrofitex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

public class AccountActivity extends AppCompatActivity {

    private final static String baseUrl = "http://www.alpaca.host";
    private static ArrayList<Message> messages;

    private EditText nameEditText;
    private String accountKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        nameEditText = findViewById(R.id.nameEditText);

        Button button = findViewById(R.id.enterButton);
        button.setOnClickListener((v) -> enterAccount());

    }

    public void enterAccount() {

        String name = nameEditText.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountService service = retrofit.create(AccountService.class);
        Call<ResponseBody> call = service.enter(new Account(name));

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    if(response.body() != null){

                        String json = response.body().string();
                        JSONObject o = new JSONObject(json);

                        accountKey = o.getString("account_key");
                        makeToast("Entered: account key is " + accountKey);

                        Intent intent = new Intent(AccountActivity.this, MessagesActivity.class);
                        intent.putExtra("account_name", name);
                        intent.putExtra("account_key", accountKey);
                        startActivity(intent);

                    }else{
                        makeToast("Failure: response body is null.");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    makeToast("Failure: IOException.");
                } catch (JSONException e) {
                    e.printStackTrace();
                    makeToast("Failure: Malformed json.");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                makeToast("Failure: onResponse failure.");
            }

        });

    }

    private void makeToast(String message) {
        Toast.makeText(AccountActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
