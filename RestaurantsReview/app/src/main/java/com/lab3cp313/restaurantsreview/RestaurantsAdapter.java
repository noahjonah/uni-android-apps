package com.lab3cp313.restaurantsreview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {
    private final Context context;
    private final List<RestaurantsModel> restaurantsList;
    private final OnRestaurantClickListener listener;

    public RestaurantsAdapter(Context context, List<RestaurantsModel> restaurantList, OnRestaurantClickListener listener) {
        this.context = context;
        this.restaurantsList = restaurantList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantsModel restaurant = restaurantsList.get(position);

        holder.restaurantName.setText(restaurant.getName());
        holder.restaurantAddress.setText(restaurant.getAddress());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClick(restaurant);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurantImage;
        TextView restaurantName, restaurantAddress;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurantImageView);
            restaurantName = itemView.findViewById(R.id.restaurantNameText);
            restaurantAddress = itemView.findViewById(R.id.restaurantsAddressText);

        }
    }

    public interface OnRestaurantClickListener {
        void onRestaurantClick(RestaurantsModel restaurant);
    }
}
