package com.lab3cp313.restaurantsreview;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsDbHelper extends SQLiteOpenHelper {
    private static final String database_name = "RestaurantReviewApp.db";
    private static final int database_version = 1;

    public RestaurantsDbHelper(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_restaurants_table = "CREATE TABLE restaurants ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "address TEXT"
                + ");";
        db.execSQL(create_restaurants_table);

        String insert_restaurants_data = "INSERT INTO restaurants (name, address) VALUES "
                + "('Restaurant A', '123 Main St'),"
                + "('Restaurant B', '456 Oak St'),"
                + "('Restaurant C', '789 Pine St');";
        db.execSQL(insert_restaurants_data);

        String create_reviews_table = "CREATE TABLE reviews ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "restaurant_id INTEGER NOT NULL,"
                + "user_id INTEGER NOT NULL,"
                + "review_text TEXT,"
                + "rating REAL CHECK (rating >= 0 AND rating <= 5),"
                + "FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),"
                + "FOREIGN KEY (user_id) REFERENCES users (id)"
                + ");";
        db.execSQL(create_reviews_table);

        String insert_reviews_data = "INSERT INTO reviews (restaurant_id, user_id, review_text, rating) VALUES "
                + "(1, 1, 'Great food!', 4.5),"
                + "(2, 2, 'Not bad.', 3.0),"
                + "(3, 1, 'Wonderful experience!', 5.0);";
        db.execSQL(insert_reviews_data);

        String create_favorites_table = "CREATE TABLE favorites ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER NOT NULL,"
                + "restaurant_id INTEGER NOT NULL,"
                + "FOREIGN KEY (user_id) REFERENCES users (id),"
                + "FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)"
                + ");";
        db.execSQL(create_favorites_table);

        String insert_favorites_data = "INSERT INTO favorites (user_id, restaurant_id) VALUES "
                + "(1, 1),"
                + "(2, 2),"
                + "(1, 3);";
        db.execSQL(insert_favorites_data);

        String create_users_table = "CREATE TABLE users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_name TEXT NOT NULL"
                + ");";
        db.execSQL(create_users_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "restaurants");
        db.execSQL("DROP TABLE IF EXISTS " + "reviews");
        db.execSQL("DROP TABLE IF EXISTS " + "favorites");
        db.execSQL("DROP TABLE IF EXISTS " + "users");
        onCreate(db);
    }

    public void createNewUser(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name", userName);
        db.insert("users", null, values);
    }

    public boolean isUserExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM users", null);
        boolean exists = false;
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) == 1;
        }
        cursor.close();
        return exists;
    }

    public String fetchUserName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT user_name FROM users WHERE id = 1";
        Cursor cursor = db.rawQuery(query, null);
        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return userName;
    }

    public void addRestaurant(String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        db.insert("restaurants", null, values);
        db.close();
    }

    public List<RestaurantsModel> fetchAllRestaurants() {
        List<RestaurantsModel> restaurants = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, name, address FROM restaurants", null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                    restaurants.add(new RestaurantsModel(name, address));
                }
            } finally {
                cursor.close();
            }
        }
        assert cursor != null;
        cursor.close();
        return restaurants;
    }

    public void addReview(int restaurantId, int userId, String reviewText, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("restaurant_id", restaurantId);
        values.put("user_id", userId);
        values.put("review_text", reviewText);
        values.put("rating", rating);

        db.insert("reviews", null, values);
        db.close();
    }

    public void addFavorite(int restaurantId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("restaurant_id", restaurantId);
        values.put("user_id", userId);

        db.insert("favorites", null, values);
        db.close();
    }

    public void removeFavorite(int restaurantId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favorites", "restaurant_id=? AND user_id=?",
                new String[]{String.valueOf(restaurantId), String.valueOf(userId)});
        db.close();
    }

    public List<String> fetchReviews() {
        List<String> reviewDetails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT res.name AS restaurant_name, r.review_text, r.rating " +
                "FROM reviews r " +
                "JOIN restaurants res ON r.restaurant_id = res.id";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String restaurantName = cursor.getString(cursor.getColumnIndexOrThrow("restaurant_name"));
                String reviewText = cursor.getString(cursor.getColumnIndexOrThrow("review_text"));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));

                String reviewDetail = "Restaurant: " + restaurantName + "\nReview: " + reviewText + "\nRating: " + rating;
                reviewDetails.add(reviewDetail);
            }
            cursor.close();
        }
        db.close();
        return reviewDetails;
    }

    public List<String> fetchFavoriteRestaurants() {
        List<String> favoriteRestaurantNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT r.name FROM favorites f " +
                "JOIN restaurants r ON f.restaurant_id = r.id";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                favoriteRestaurantNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteRestaurantNames;
    }

}
