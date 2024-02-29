package com.foodorderapps.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.foodorderapps.Models.OrdersModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static int VERSION = 4;
    final static String DATABASE_NAME = "my_database.db";
    final static String TABLE_NAME = "Orders";
    final static String ID = "Id";
    final static String NAME = "Name";
    final static String PHONE = "Phone";
    final static String ADDRESS = "Address";
    final static String PRICE = "Price";
    final static String IMAGE = "Image";
    final static String QUANTITY = "Quantity";
    final static String DESCRIPTION = "Description";
    final static String FOOD_NAME = "FoodName";
    final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,"
            + NAME + " TEXT ," + PHONE + " TEXT ," + ADDRESS + " TEXT ," + PRICE + " int ," + QUANTITY + " int ," + IMAGE + " int ,"
            + DESCRIPTION + " TEXT ," + FOOD_NAME + " TEXT )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertOrder(int price, int image, int quantity, String name, String phone,String address, String description, String foodName) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRICE, price);
        values.put(IMAGE, image);
        values.put(QUANTITY, quantity);
        values.put(NAME, name);
        values.put(PHONE, phone);
        values.put(ADDRESS, address);
        values.put(DESCRIPTION, description);
        values.put(FOOD_NAME, foodName);
        long id = database.insert(TABLE_NAME, null, values);
        return id >= 0;
    }

    public int deleteOrder(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, ID + "=" + id, null);
    }

    public boolean updateOrder(String name, String phone, String address, int quantity, int id,int price) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(PRICE,price);
        values.put(PHONE, phone);
        values.put(ADDRESS, address);
        values.put(QUANTITY, quantity);
        try {
            long UID = database.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(id)});
            return UID > 0;
        } catch (Exception e) {
            return false;
        } finally {
            database.close();
        }
    }

    public Cursor getOrderByID(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=" + id, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    @SuppressLint("Range")
    public ArrayList<OrdersModel> getOrder() {
        ArrayList<OrdersModel> orderList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            while (cursor.moveToNext()) {
                OrdersModel model = new OrdersModel();
                model.setOrderNumber(String.valueOf(cursor.getInt(cursor.getColumnIndex(ID))));
                model.setOrderPrice(String.valueOf(cursor.getInt(cursor.getColumnIndex(PRICE))));
                model.setOrderQuantity(String.valueOf(cursor.getInt(cursor.getColumnIndex(QUANTITY))));
                model.setOrderTitle(cursor.getString(cursor.getColumnIndex(FOOD_NAME)));
                model.setImg(cursor.getInt(cursor.getColumnIndex(IMAGE)));
                orderList.add(model);
            }
        cursor.close();
        database.close();
        return orderList;
    }
}
