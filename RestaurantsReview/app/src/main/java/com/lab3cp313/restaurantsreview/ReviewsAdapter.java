package com.lab3cp313.restaurantsreview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private final List<ReviewsModel> reviews;
    public ReviewsAdapter(List<ReviewsModel> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewsModel review = reviews.get(position);
        Objects.requireNonNull(holder).rvRestaurantName.setText(review.getRestaurantName());
        holder.rvReviewText.setText(review.getReviewText());
        holder.rvRating.setText(String.format("Rating: %s", review.getRating()));
    }

    @Override
    public int getItemCount() {return reviews.size();}

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView rvRestaurantName, rvReviewText, rvRating;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            rvRestaurantName = itemView.findViewById(R.id.reviewNameTextView);
            rvReviewText = itemView.findViewById(R.id.reviewTextView);
            rvRating = itemView.findViewById(R.id.ratingTextView);
        }
    }
}
