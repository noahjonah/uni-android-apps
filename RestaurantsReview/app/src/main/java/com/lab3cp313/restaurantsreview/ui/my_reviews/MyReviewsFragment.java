package com.lab3cp313.restaurantsreview.ui.my_reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lab3cp313.restaurantsreview.R;
import com.lab3cp313.restaurantsreview.RestaurantsDbHelper;
import com.lab3cp313.restaurantsreview.ReviewsAdapter;
import com.lab3cp313.restaurantsreview.ReviewsModel;
import java.util.ArrayList;
import java.util.List;

public class MyReviewsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View myReviewsFragment = inflater.inflate(R.layout.fragment_my_reviews, container, false);

        RecyclerView recyclerView = myReviewsFragment.findViewById(R.id.reviewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RestaurantsDbHelper databaseHelper = new RestaurantsDbHelper(getContext());
        List<ReviewsModel> reviewList = new ArrayList<>();

        List<String> reviewsData = databaseHelper.fetchReviews();
        for (String reviewDetail : reviewsData) {
            String[] parts = reviewDetail.split("\n"); // splitting
            String restaurantName = parts[0].replace("Restaurant: ", "");
            String reviewText = parts[1].replace("Review: ", "");
            float rating = Float.parseFloat(parts[2].replace("Rating: ", ""));
            reviewList.add(new ReviewsModel(restaurantName, reviewText, rating));
        }

        ReviewsAdapter adapter = new ReviewsAdapter(reviewList);
        recyclerView.setAdapter(adapter);
        return myReviewsFragment;
    }
}
