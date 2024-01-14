package com.foodorderapps.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.foodorderapps.Adapter.OrderAdapter;
import com.foodorderapps.Database.DatabaseHelper;
import com.foodorderapps.Models.OrdersModel;
import com.foodorderapps.databinding.ActivityOrderBinding;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        DatabaseHelper helper = new DatabaseHelper(this);
        ArrayList<OrdersModel> list = helper.getOrder();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.RecycleViewOrder.setLayoutManager(layoutManager);

        OrderAdapter adapter = new OrderAdapter(list, this);
        adapter.notifyDataSetChanged();
        binding.RecycleViewOrder.setAdapter(adapter);
    }
}