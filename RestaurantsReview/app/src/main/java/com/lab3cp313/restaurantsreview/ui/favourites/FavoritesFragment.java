package com.lab3cp313.restaurantsreview.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lab3cp313.restaurantsreview.FavoritesAdapter;
import com.lab3cp313.restaurantsreview.R;
import com.lab3cp313.restaurantsreview.RestaurantsDbHelper;
import java.util.List;

public class FavoritesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        RestaurantsDbHelper databaseHelper = new RestaurantsDbHelper(getContext());

//        // Fetch favorite restaurants for the user
//        List<String> favoriteRestaurants = databaseHelper.fetchFavoriteRestaurants(userId);
//
//        // Set up the ListView
//        ListView favoritesListView = view.findViewById(R.id.favoritesListView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
//                              android.R.layout.simple_list_item_1, favoriteRestaurants);
//        favoritesListView.setAdapter(adapter);

        RecyclerView recyclerView = view.findViewById(R.id.favRecyclerView);

        List<String> favoriteRestaurantNames = databaseHelper.fetchFavoriteRestaurants();
        FavoritesAdapter adapter = new FavoritesAdapter(getContext(),
                favoriteRestaurantNames, restaurantName ->
                Toast.makeText(getContext(), "It's yo fav Restaurant: " + restaurantName,
                        Toast.LENGTH_SHORT).show());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
