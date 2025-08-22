package com.lab3cp313.restaurantsreview.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.lab3cp313.restaurantsreview.R;
import com.lab3cp313.restaurantsreview.RestaurantsDbHelper;

public class RestaurantDetailsFragment extends Fragment {
    private RestaurantsDbHelper databaseHelper;
    private int restaurantId;

    public RestaurantDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        View view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);

        EditText reviewInput = view.findViewById(R.id.restaurantReviewText);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        CheckBox favoriteCheckbox = view.findViewById(R.id.favouriteCheckBox);
        Button addReviewButton = view.findViewById(R.id.addReviewBtn);
        TextView nameTextView = view.findViewById(R.id.restaurantNameView);
        TextView addressTextView = view.findViewById(R.id.restaurantAddressView);

        databaseHelper = new RestaurantsDbHelper(getContext());

        Bundle args = getArguments();
        if (args != null) {
            restaurantId = args.getInt("restaurant_id");
            String restaurantName = args.getString("restaurant_name");
            String restaurantAddress = args.getString("restaurant_address");

            nameTextView.setText(restaurantName);
            addressTextView.setText(restaurantAddress);
        }

        addReviewButton.setOnClickListener(v -> {
            String reviewText = reviewInput.getText().toString().trim();
            float rating = ratingBar.getRating();
            int userId = 1;

            if (!reviewText.isEmpty() && rating > 0) {
                databaseHelper.addReview(restaurantId, userId, reviewText, rating);
                Toast.makeText(getContext(), "Review added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please add a review and rating", Toast.LENGTH_SHORT).show();
            }
        });

        favoriteCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int userId = 1;
            if (isChecked) {
                databaseHelper.addFavorite(restaurantId, userId);
                Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.removeFavorite(restaurantId, userId);
                Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}