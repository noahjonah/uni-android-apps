package com.lab3cp313.restaurantsreview.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lab3cp313.restaurantsreview.R;
import com.lab3cp313.restaurantsreview.RestaurantsAdapter;
import com.lab3cp313.restaurantsreview.RestaurantsDbHelper;
import com.lab3cp313.restaurantsreview.RestaurantsModel;
import java.util.List;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View homeFragment = inflater.inflate(R.layout.fragment_home, container, false);

        RestaurantsDbHelper databaseHelper = new RestaurantsDbHelper(getContext());
        List<RestaurantsModel> restaurantList = databaseHelper.fetchAllRestaurants();
        RecyclerView recyclerView = homeFragment.findViewById(R.id.restaurantsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RestaurantsAdapter adapter = new RestaurantsAdapter(getContext(), restaurantList, restaurant -> {
            Bundle bundle = new Bundle();
            bundle.putInt("restaurant_id", restaurant.getId());
            bundle.putString("restaurant_name", restaurant.getName());
            bundle.putString("restaurant_address", restaurant.getAddress());

            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.restaurant_details, bundle);
        });
        recyclerView.setAdapter(adapter);

        return homeFragment;
    }
}