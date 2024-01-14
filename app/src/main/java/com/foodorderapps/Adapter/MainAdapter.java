package com.foodorderapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodorderapps.Activity.DetailsActivity;
import com.foodorderapps.Models.MainModel;
import com.foodorderapps.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<MainModel> arrayList;
    Context context;

    public MainAdapter(ArrayList<MainModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setImageResource(arrayList.get(position).getImg());
        holder.title.setText(arrayList.get(position).getTitle());
        holder.price.setText(arrayList.get(position).getPrice());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("image", arrayList.get(position).getImg());
                intent.putExtra("name", arrayList.get(position).getTitle());
                intent.putExtra("price", arrayList.get(position).getPrice());
                intent.putExtra("description", arrayList.get(position).getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, description, price;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ImageView);
            title = itemView.findViewById(R.id.Name);
            description = itemView.findViewById(R.id.Description);
            price = itemView.findViewById(R.id.Price);
        }
    }
}
