package com.foodorderapps.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodorderapps.Activity.DetailsActivity;
import com.foodorderapps.Database.DatabaseHelper;
import com.foodorderapps.Models.OrdersModel;
import com.foodorderapps.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    ArrayList<OrdersModel> list;
    Context context;

    public OrderAdapter(ArrayList<OrdersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setImageResource(list.get(position).getImg());
        holder.orderPrice.setText(list.get(position).getOrderPrice());
        holder.orderQuantity.setText(list.get(position).getOrderQuantity());
        holder.orderNumber.setText(list.get(position).getOrderNumber());
        holder.orderName.setText(list.get(position).getOrderTitle());

        DatabaseHelper helper = new DatabaseHelper(context);

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, DetailsActivity.class);
            intent.putExtra("type",2);
            intent.putExtra("id",Integer.parseInt(list.get(position).getOrderNumber()));
            context.startActivity(intent);
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setCancelable(true).setTitle("Delete")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            if (helper.deleteOrder(list.get(position).getOrderNumber()) > 0) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", (dialogInterface, i) -> {
                            dialogInterface.cancel();
                        }).show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView orderPrice, orderQuantity, orderName, orderNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.OrderImageView);
            orderName = itemView.findViewById(R.id.OrderName);
            orderNumber = itemView.findViewById(R.id.OrderNo);
            orderPrice = itemView.findViewById(R.id.OrderPrice);
            orderQuantity = itemView.findViewById(R.id.OrderQuantity);
        }
    }
}
