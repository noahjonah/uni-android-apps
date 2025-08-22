package com.lab3cp313.restaurantsreview;

public class ReviewsModel {
    private final String restaurantName;
    private final String reviewText;
    private final float rating;

    public ReviewsModel(String restaurantName, String reviewText, float rating) {
        this.restaurantName = restaurantName;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public float getRating() {
        return rating;
    }
}
