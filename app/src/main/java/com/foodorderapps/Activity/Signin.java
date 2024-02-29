package com.foodorderapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    ActivitySigninBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.loginregister.setOnClickListener(v -> {
            startActivity(new Intent(Signin.this, Register.class));
        });
        binding.loginbutton.setOnClickListener(v -> {
            String email = "", pass = "";
            email = binding.loginemail.getText().toString();
            pass = binding.loginpassword.getText().toString();
            if (email.isEmpty()) {
                binding.loginemail.setError("Enter email");
            } else if (pass.isEmpty()) {
                binding.loginpassword.setError("Enter password");
            } else {
                binding.progressbar.setVisibility(View.VISIBLE);
                signin(email,pass);
            }
        });
    }

    private void signin(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    binding.progressbar.setVisibility(View.GONE);
                    Intent intent = new Intent(Signin.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Signin.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}