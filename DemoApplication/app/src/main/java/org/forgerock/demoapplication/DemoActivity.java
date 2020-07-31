package org.forgerock.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.forgerock.android.auth.FRListener;
import org.forgerock.android.auth.FRUser;
import org.forgerock.android.auth.UserInfo;
import org.forgerock.android.auth.interceptor.AccessTokenInterceptor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DemoActivity extends AppCompatActivity {

    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        content = findViewById(R.id.content);

        //Trigger the /userinfo endpoint
        Button userInfo = findViewById(R.id.userinfo);
        userInfo.setOnClickListener(view -> FRUser.getCurrentUser().getUserInfo(new FRListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                show(result.getRaw());
            }

            @Override
            public void onException(Exception e) {
                show(e.getMessage());
            }
        }));

        //Logout user
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            FRUser.getCurrentUser().logout();
            Intent intent = new Intent(DemoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        //Invoke Backend API
        Button api = findViewById(R.id.api);
        api.setOnClickListener(view -> {

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .followRedirects(false);
            builder.addInterceptor(new AccessTokenInterceptor());

            OkHttpClient client = builder.build();
            Request request = new Request.Builder().url("http://10.0.2.2:9001/products").build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    show(e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            try {
                                show(response.body().string());
                            } catch (IOException e) {
                                show(e.getMessage());
                            }
                        } else {
                            show("Code:" + response.code() +
                                    "Body:" +
                                    response.body().string());
                        }
                }
            });

        });

    }

    private void show(String text) {
        runOnUiThread(() -> content.setText(text));
    }

    private void show(JSONObject text) {
        runOnUiThread(() -> {
            try {
                content.setText(text.toString(4));
            } catch (JSONException e) {
                //ignore
            }
        });
    }


}
