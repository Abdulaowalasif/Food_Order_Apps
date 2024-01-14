package com.foodorderapps.Activity;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodorderapps.Database.DatabaseHelper;
import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    int quantity = 1;
    int totalPrice;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Intent intent=new Intent(this, MainActivity.class);

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

            binding.DetailsOrderBtnId.setOnClickListener(v -> {
                boolean isInserted = helper.insertOrder(Integer.parseInt(binding.DetailsPriceId.getText().toString()),
                        image,
                        Integer.parseInt(binding.DetailsQuantityId.getText().toString()),
                        binding.DetailsNameId.getText().toString(),
                        binding.DetailsPhoneId.getText().toString(),
                        description,
                        foodName);
                if (isInserted) {
                    Toast.makeText(this, "Order placed successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to placed order.PLease try again.", Toast.LENGTH_SHORT).show();
                }
             startActivity(intent);
                finish();
            });
        } else {
            int id = getIntent().getIntExtra("id", 0);
            Cursor cursor = helper.getOrderByID(id);
            if (cursor.moveToFirst()) {
                int image = cursor.getInt(5);
                // Set image resource
                binding.detailsImageViewId.setImageResource(image);
                // Set text values
                binding.DetailsPriceId.setText(String.valueOf(cursor.getInt(3)));
                binding.detailsFoodNameID.setText(cursor.getString(7));
                binding.DetailsDescriptionId.setText(cursor.getString(6));
                binding.DetailsNameId.setText(cursor.getString(1));
                binding.DetailsPhoneId.setText(cursor.getString(2));
                binding.DetailsQuantityId.setText(String.valueOf(cursor.getInt(4)));

                totalPrice = cursor.getInt(3);
                quantity = cursor.getInt(4);
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
                            updatedQuantity,
                            id
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