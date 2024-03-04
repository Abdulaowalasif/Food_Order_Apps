package com.foodorderapps.Activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.foodorderapps.Adapter.MainAdapter;
import com.foodorderapps.Models.FirebaseModel;
import com.foodorderapps.Models.MainModel;
import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<MainModel> arrayList;
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Users");

    ActivityResultLauncher<String> launcher;
    FirebaseStorage storage=FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        auth = FirebaseAuth.getInstance();

        View header=binding.navView.getHeaderView(0);
        ImageView profile=header.findViewById(R.id.userProfile);

        String id = auth.getCurrentUser().getUid();


        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseModel userModel = snapshot.getValue(FirebaseModel.class);
                    TextView uname = header.findViewById(R.id.userName);
                    TextView uemail = header.findViewById(R.id.userEmail);

                    uname.setText(userModel.getUsername());
                    uemail.setText(userModel.getEmail());




                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation, if needed
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = ((FirebaseUser) currentUser).getUid();
            StorageReference storageReference=storage.getReference().child(userId);
            DatabaseReference userReference = database.getReference("Users").child(userId);


            launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {

                  profile.setImageURI(uri);
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    DatabaseReference imgReference = userReference.child("img"); // Create child under current user
                                    imgReference.setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }






        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecycleViewHome.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        binding.navView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        arrayList.add(new MainModel(R.drawable.kacchi, "Kacchi", "200", "Kacchi Biriyani with extra mutton"));
        arrayList.add(new MainModel(R.drawable.beef_curry, "Beef Curry", "300", "Beef Meat Curry with special ingredients"));
        arrayList.add(new MainModel(R.drawable.kala_bhuna, "Beef Kala Vuna", "350", "Beef Meat Kala Vuna with special spices"));
        arrayList.add(new MainModel(R.drawable.nehari, "Beef Nehari", "400", "2 piece Beef Nehira with extremely spiced Juice and Bone Marrow"));
        arrayList.add(new MainModel(R.drawable.chicken_tawa_jhal_fry, "Chicken Tawa Jhal Fry", "180", "Spicy Smokey Chicken Fry"));
        arrayList.add(new MainModel(R.drawable.chicken_tikka, "Chicken Tikka", "180", "Special ingredients mixed Tikka"));
        arrayList.add(new MainModel(R.drawable.chicken_fry, "Fried Chicken", "180", "Chicken fried, extremely crumbly outside but  soft inside."));
        arrayList.add(new MainModel(R.drawable.polao_chicken_korma, "Polao Chicken Korma", "400", "Hot sizzling Chicken Korma with Basmati rice Polao"));
        arrayList.add(new MainModel(R.drawable.set_menu_1, "Set Menu-1", "300", "Consists of fried rice, fried chicken, mixed vegetable & beef onion"));
        arrayList.add(new MainModel(R.drawable.set_menu_2, "Set Menu-2", "280", "Consists of chow-min, sausage, chicken chili & pop chicken"));
        arrayList.add(new MainModel(R.drawable.set_menu_3, "Set Menu-3", "320", "Consists of fried rice, fried chicken, mixed vegetable & chicken chili"));
        arrayList.add(new MainModel(R.drawable.set_menu_vat, "Set Menu-4(Rice)", "200", "Plain rice with different types of Vorta and Ilish Mach"));
        arrayList.add(new MainModel(R.drawable.keema_puri, "Chicken Keema Puri", "80", "4 piece Puri stuffed with beef keema & onion fry"));
        arrayList.add(new MainModel(R.drawable.singara, "Chicken Singara", "80", "4 piece Singara stuffed with chicken mixed mashed potato"));
        arrayList.add(new MainModel(R.drawable.porota, "Porota", "10", "1 piece Plain Porota"));
        arrayList.add(new MainModel(R.drawable.nun, "Nun Roti", "20", "1 piece Plain Nun Roti"));
        arrayList.add(new MainModel(R.drawable.butter_nun, "Butter Nun Roti", "25", "1 piece Nun Roti coated with Extra Light Butter "));


        MainAdapter adapter = new MainAdapter(arrayList, this);
        binding.RecycleViewHome.setAdapter(adapter);

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.cart) {
                    startActivity(new Intent(MainActivity.this, OrderActivity.class));
                    Toast.makeText(MainActivity.this, "In the cart", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.logout) {
                    auth.signOut();
                    startActivity(new Intent(MainActivity.this, Signin.class));
                } else if (id == R.id.share) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("application/vnd.android.package-archive");

                    intent.putExtra(Intent.EXTRA_STREAM, "share");
                    startActivity(Intent.createChooser(intent, "Share APK via"));
                } else if (id == R.id.rate) {
                    Toast toast = new Toast(MainActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.custom_toast, findViewById(R.id.container));
                    toast.setView(view);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = new Toast(MainActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.feedback_toast, findViewById(R.id.container));
                    toast.setView(view);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}