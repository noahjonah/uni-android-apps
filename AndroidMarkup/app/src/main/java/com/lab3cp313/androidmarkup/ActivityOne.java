package com.lab3cp313.androidmarkup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_one);

        SharedPreferences markupSharedPreference = getSharedPreferences("markupSharedPreference", MODE_PRIVATE);

        EditText usernameText = findViewById(R.id.usernameEditText);
        EditText userPassword = findViewById(R.id.passwordEditText);
        CheckBox rememberCheck_state = findViewById(R.id.rememberCheckBox);
        Button submit_button = findViewById(R.id.submittingBtn);

        if (markupSharedPreference.getBoolean("rememberCheck_state", false)) {
            usernameText.setText(markupSharedPreference.getString("username", ""));
            userPassword.setText(markupSharedPreference.getString("password", ""));
            rememberCheck_state.setChecked(true);
        }

        submit_button.setOnClickListener(v -> {
            String username = usernameText.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(ActivityOne.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(ActivityOne.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor saveMarkup = markupSharedPreference.edit();
            saveMarkup.putString("username", username);
            saveMarkup.putString("password", password);
            saveMarkup.putBoolean("rememberCheck_state", rememberCheck_state.isChecked());
            saveMarkup.apply();

            Toast.makeText(ActivityOne.this, "Credentials Are Well Saved In Shared Preference!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}