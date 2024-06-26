package com.foodorderapps.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URI;

public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String ss = "android.resource://" + getPackageName() + "/raw/ss";
        Uri s1= Uri.parse(ss);
        binding.ss.setVideoURI(s1);
        binding.ss.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (auth.getCurrentUser() != null) {
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                }
           else {

                    intent = new Intent(SplashScreen.this, Signin.class);
                }

                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}