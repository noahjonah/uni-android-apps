package com.lab3cp313.androidmarkup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences markupSharedPreferences = getSharedPreferences("markupSharedPreference", MODE_PRIVATE);
        String username = markupSharedPreferences.getString("username", "");
        String password = markupSharedPreferences.getString("password", "");
        boolean checkbox_state = markupSharedPreferences.getBoolean("rememberMe", false);

        if (!checkbox_state && !username.isEmpty() && !password.isEmpty()) {
            findViewById(R.id.continue_btn).setOnClickListener(view -> {
                Intent toActivityOne = new Intent(MainActivity.this, ActivityTwo.class);
                startActivity(toActivityOne);
                finish();
            });
        } else {
            findViewById(R.id.continue_btn).setOnClickListener(view -> {
                Intent towardsActivityOne = new Intent(MainActivity.this, ActivityOne.class);
                startActivity(towardsActivityOne);
                finish();
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}