package com.foodorderapps.Activity;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.foodorderapps.Database.DatabaseHelper;
import com.foodorderapps.Models.FirebaseModel;
import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivityDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    int quantity = 1;
    int totalPrice;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference= database.getReference("Users");
    FirebaseAuth auth=FirebaseAuth.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(this, MainActivity.class);

        DatabaseHelper helper = new DatabaseHelper(this);
        if (getIntent().getIntExtra("type", 0) == 1) {
            final int image = getIntent().getIntExtra("image", 0);
            final String price = getIntent().getStringExtra("price");
            final String foodName = getIntent().getStringExtra("name");
            final String description = getIntent().getStringExtra("description");

            totalPrice = Integer.parseInt(price);
            binding.DetailsQuantityId.setText(String.valueOf(quantity));
            binding.DetailsAddId.setOnClickListener(v -> {
                if (quantity >= 1) {
                    quantity++;
                    binding.DetailsQuantityId.setText(String.valueOf(quantity));
                    binding.DetailsPriceId.setText(String.valueOf(totalPrice * quantity));
                }
            });
            binding.detailsSubscriptId.setOnClickListener(v -> {
                 if (quantity > 1) {
                    quantity--;
                    binding.DetailsQuantityId.setText(String.valueOf(quantity));
                    binding.DetailsPriceId.setText(String.valueOf((totalPrice * quantity)));
                }
            });

            binding.detailsImageViewId.setImageResource(image);
            binding.DetailsPriceId.setText(price);
            binding.detailsFoodNameID.setText(foodName);
            binding.DetailsDescriptionId.setText(description);
            {

                reference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FirebaseModel mode=snapshot.getValue(FirebaseModel.class);
                        binding.DetailsNameId.setText(mode.getUsername());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            binding.DetailsOrderBtnId.setOnClickListener(v -> {
                String name = "", phone = "",address = "";
                if (binding.DetailsNameId.getText().toString().isEmpty()) {
                    binding.DetailsNameId.setError("Enter your name");
                } else if (binding.DetailsPhoneId.getText().toString().isEmpty()) {
                    binding.DetailsPhoneId.setError("Enter your phone number");
                }
                else if (binding.DetailsAddressId.getText().toString().isEmpty()) {
                    binding.DetailsAddressId.setError("Enter your address ");
                } else {
                    name = binding.DetailsNameId.getText().toString();
                    phone = binding.DetailsPhoneId.getText().toString();
                    address = binding.DetailsAddressId.getText().toString();
                    boolean isInserted = helper.insertOrder(Integer.parseInt(binding.DetailsPriceId.getText().toString()),
                            image,
                            Integer.parseInt(binding.DetailsQuantityId.getText().toString()),
                            name,
                            phone,
                            address,
                            description,
                            foodName);

                    if (isInserted) {
                        Toast.makeText(this, "Order placed successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to placed order.PLease try again.", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(intent);
                    finish();
                }

            });
        } else {
            int id = getIntent().getIntExtra("id", 0);
            Cursor cursor = helper.getOrderByID(id);
            if (cursor.moveToFirst()) {
                int image = cursor.getInt(6);
                binding.detailsImageViewId.setImageResource(image);
                binding.DetailsPriceId.setText(String.valueOf(cursor.getInt(4)));
                binding.detailsFoodNameID.setText(cursor.getString(8));
                binding.DetailsDescriptionId.setText(cursor.getString(7));
                binding.DetailsNameId.setText(cursor.getString(1));
                binding.DetailsPhoneId.setText(cursor.getString(2));
                binding.DetailsAddressId.setText(cursor.getString(3));
                binding.DetailsQuantityId.setText(String.valueOf(cursor.getInt(5)));


                totalPrice = cursor.getInt(4);
                quantity = cursor.getInt(5);
                int singleUnitPrice = (totalPrice / quantity);

                binding.DetailsAddId.setOnClickListener(v -> {
                    if (quantity >= 1) {
                        quantity++;
                        binding.DetailsQuantityId.setText(String.valueOf(quantity));
                        binding.DetailsPriceId.setText(String.valueOf(singleUnitPrice * quantity));
                    }
                });

                binding.detailsSubscriptId.setOnClickListener(v -> {
                    if (quantity > 1) {
                        quantity--;
                        binding.DetailsQuantityId.setText(String.valueOf(quantity));
                        binding.DetailsPriceId.setText(String.valueOf(singleUnitPrice * quantity));
                    }
                });

                binding.DetailsOrderBtnId.setText(R.string.upBtn);
                binding.DetailsOrderBtnId.setOnClickListener(view -> {
                    int updatedQuantity = Integer.parseInt(binding.DetailsQuantityId.getText().toString());
                    boolean isUpdated = helper.updateOrder(
                            binding.DetailsNameId.getText().toString(),
                            binding.DetailsPhoneId.getText().toString(),
                            binding.DetailsAddressId.getText().toString(),
                            updatedQuantity,
                            id,
                            Integer.parseInt(binding.DetailsPriceId.getText().toString())
                    );
                    if (isUpdated) {
                        Toast.makeText(DetailsActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(intent);
                    finish();
                });
            }
            cursor.close();
        }
    }
}