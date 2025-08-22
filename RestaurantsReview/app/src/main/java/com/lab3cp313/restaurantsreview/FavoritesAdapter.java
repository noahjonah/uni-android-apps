package com.lab3cp313.restaurantsreview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private final Context context;
    private final List<String> favoriteRestaurantNames;
    private final OnRestaurantClickListener clickListener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(String restaurantName);
    }

    public FavoritesAdapter(Context context, List<String> favoriteRestaurantNames, OnRestaurantClickListener clickListener) {
        this.context = context;
        this.favoriteRestaurantNames = favoriteRestaurantNames;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        String restaurantName = favoriteRestaurantNames.get(position);

        Objects.requireNonNull(holder).nameTextView.setText(restaurantName);
        holder.itemView.setOnClickListener(v -> clickListener.onRestaurantClick(restaurantName));
    }

    @Override
    public int getItemCount() {
        return favoriteRestaurantNames.size();
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.favRestaurantNameText);
        }
    }
}
