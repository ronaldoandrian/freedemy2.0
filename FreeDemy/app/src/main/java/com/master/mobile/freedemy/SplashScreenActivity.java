package com.master.mobile.freedemy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.mobile.freedemy.account.LoginActivity;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        try {
            GifDrawable gifFromAssets = new GifDrawable( getAssets(), "giphy.gif" );
            GifImageView gifImage = findViewById(R.id.logo);
            gifImage.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkLoginStatus();
    }

    public void checkLoginStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        boolean     isConnected = user != null;
        if(isConnected) isConnected = user.isEmailVerified();
        Handler handler = new Handler();

        if (isConnected) {
            handler.postDelayed(() -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 1000);
        } else {
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, 1000);
        }
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
