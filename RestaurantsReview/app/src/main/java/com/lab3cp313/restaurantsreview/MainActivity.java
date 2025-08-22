package com.lab3cp313.restaurantsreview;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.lab3cp313.restaurantsreview.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private RestaurantsDbHelper databaseHelper;
    private RestaurantsAdapter adapter;
    private List<RestaurantsModel> restaurantList;

    @Override
    protected void onStart() {
        super.onStart();
        if (!(databaseHelper.isUserExists())) {
            showUserDialog();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new RestaurantsDbHelper(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favourites, R.id.nav_my_reviews)
                .setOpenableLayout(drawer)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.userNameTextView);
        String username = databaseHelper.fetchUserName();

        if (username == null || username.isEmpty()) {
            username = "Guest";
        }
        navUsername.setText(String.format("Welcome %s", username));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            showAddRestaurantDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation
                .findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void showUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome to Restaurants Review App!");
        builder.setMessage("Please enter your name to continue looking at our restaurants...");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("SAVE ME", (dialog, which) -> {
            String userName = input.getText().toString().trim();

            if (!(userName.isEmpty())) {
                databaseHelper.createNewUser(userName);
            } else {
                showUserDialog();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showAddRestaurantDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Restaurant");

        final EditText inputName = new EditText(this);
        inputName.setHint("Name");

        final EditText inputAddress = new EditText(this);
        inputAddress.setHint("Address");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputName);
        layout.addView(inputAddress);
        builder.setView(layout);

        builder.setPositiveButton("Add Restaurant", (dialog, which) -> {
            String name = inputName.getText().toString().trim();
            String address = inputAddress.getText().toString().trim();

            if (!name.isEmpty() && !address.isEmpty()) {
                databaseHelper.addRestaurant(name, address);

                RestaurantsModel newRestaurant = new RestaurantsModel(name, address);
                restaurantList.add(newRestaurant);
                adapter.notifyDataSetChanged();

                Toast.makeText(this, "Restaurant added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}