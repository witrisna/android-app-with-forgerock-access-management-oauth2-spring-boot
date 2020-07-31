package org.forgerock.demoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.forgerock.android.auth.FRListener;

public class MainActivity extends AppCompatActivity implements FRListener<Void> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSuccess(Void result) {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onException(Exception e) {

    }
}
