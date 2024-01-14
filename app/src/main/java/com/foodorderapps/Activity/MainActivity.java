package com.foodorderapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.foodorderapps.Adapter.MainAdapter;
import com.foodorderapps.Models.MainModel;
import com.foodorderapps.R;
import com.foodorderapps.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
ArrayList<MainModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.RecycleViewHome.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();

        arrayList.add(new MainModel(R.drawable.burger,"Burger","100","Chicken burger with extra cheese"));
        arrayList.add(new MainModel(R.drawable.kacchi,"Kacchi","200","Kacchi biriyani with extra mutton"));
        arrayList.add(new MainModel(R.drawable.pizza,"Pizza","300","Chicken pizza with extra cheese"));
        arrayList.add(new MainModel(R.drawable.hotdog,"Hot Dog","400","Hot Dog with extra cheese"));
        arrayList.add(new MainModel(R.drawable.chickenfry,"Chicken fries","500","Chicken fry"));
        arrayList.add(new MainModel(R.drawable.frenchfries,"French fries","600","French fry"));
        arrayList.add(new MainModel(R.drawable.donut,"Donut","700","Chocolate donut"));

        MainAdapter adapter=new MainAdapter(arrayList,this);
        binding.RecycleViewHome.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.cart){
            startActivity(new Intent(this, OrderActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}