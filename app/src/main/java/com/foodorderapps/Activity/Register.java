package com.foodorderapps.Activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.foodorderapps.Models.FirebaseModel;
import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

                String finalName = name;
                String finalEmail = email;

                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    binding.regProgress.setVisibility(View.GONE);
                                    String id = auth.getCurrentUser().getUid();
                                    FirebaseModel model = new FirebaseModel(finalName,finalEmail);
                                    reference.child(id).setValue(model);
                                    Toast.makeText(Register.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(Register.this,MainActivity.class));
                                        }
                                    },1000);
                                } else {
                                    binding.regProgress.setVisibility(View.GONE);
                                    Toast.makeText(Register.this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
