package com.foodorderapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        binding.reglogin.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Signin.class));
        });
        binding.regbutton.setOnClickListener(v -> {
            String name = "", email = "", pass = "";
            name = binding.regfirstname.getText().toString() + " " + binding.reglastname.getText().toString();
            email = binding.regemail.getText().toString();
            pass = binding.regpassword.getText().toString();

            if (binding.regfirstname.getText().toString().isEmpty()) {
                binding.regfirstname.setError("Enter your first name");
            } else if (binding.reglastname.getText().toString().isEmpty()) {
                binding.reglastname.setError("Enter your last name");
            } else if (binding.regemail.getText().toString().isEmpty()) {
                binding.regemail.setError("Enter your email");
            } else if (binding.regpassword.getText().toString().isEmpty()) {
                binding.regpassword.setError("Enter your password");
            } else {
                binding.regProgress.setVisibility(View.VISIBLE);
                register(name, email, pass);
            }
        });
    }

    private void register(String name, String email, String pass) {

        auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            binding.regProgress.setVisibility(View.GONE);
                            Toast.makeText(Register.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, MainActivity.class));
                        }else {
                            Toast.makeText(Register.this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}