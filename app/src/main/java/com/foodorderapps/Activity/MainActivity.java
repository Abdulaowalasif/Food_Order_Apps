package com.foodorderapps.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import com.google.android.material.navigation.NavigationView;

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

        arrayList.add(new MainModel(R.drawable.kacchi,"Kacchi","200","Kacchi Biriyani with extra mutton"));
        arrayList.add(new MainModel(R.drawable.beef_curry,"Beef Curry","300","Beef Meat Curry with special ingredients"));
        arrayList.add(new MainModel(R.drawable.kala_bhuna,"Beef Kala Vuna","350","Beef Meat Kala Vuna with special spices"));
        arrayList.add(new MainModel(R.drawable.nehari,"Beef Nehari","400","2 piece Beef Nehira with extremely spiced Juice and Bone Marrow"));
        arrayList.add(new MainModel(R.drawable.chicken_tawa_jhal_fry,"Chicken Tawa Jhal Fry","180","Spicy Smokey Chicken Fry"));
        arrayList.add(new MainModel(R.drawable.chicken_tikka,"Chicken Tikka","180","Special ingredients mixed Tikka"));
        arrayList.add(new MainModel(R.drawable.chicken_fry,"Fried Chicken","180","Chicken fried, extremely crumbly outside but  soft inside."));
        arrayList.add(new MainModel(R.drawable.polao_chicken_korma,"Polao Chicken Korma","400","Hot sizzling Chicken Korma with Basmati rice Polao"));
        arrayList.add(new MainModel(R.drawable.set_menu_1,"Set Menu-1","300","Consists of fried rice, fried chicken, mixed vegetable & beef onion"));
        arrayList.add(new MainModel(R.drawable.set_menu_2,"Set Menu-2","280","Consists of chow-min, sausage, chicken chili & pop chicken"));
        arrayList.add(new MainModel(R.drawable.set_menu_3,"Set Menu-3","320","Consists of fried rice, fried chicken, mixed vegetable & chicken chili"));
        arrayList.add(new MainModel(R.drawable.set_menu_vat,"Set Menu-4(Rice)","200","Plain rice with different types of Vorta and Ilish Mach"));
        arrayList.add(new MainModel(R.drawable.keema_puri,"Chicken Keema Puri","80","4 piece Puri stuffed with beef keema & onion fry"));
        arrayList.add(new MainModel(R.drawable.singara,"Chicken Singara","80","4 piece Singara stuffed with chicken mixed mashed potato"));
        arrayList.add(new MainModel(R.drawable.porota,"Porota","10","1 piece Plain Porota"));
        arrayList.add(new MainModel(R.drawable.nun,"Nun Roti","20","1 piece Plain Nun Roti"));
        arrayList.add(new MainModel(R.drawable.butter_nun,"Butter Nun Roti","25","1 piece Nun Roti coated with Extra Light Butter "));



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