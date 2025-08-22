package com.cp313.lab01;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText firstName, secondName, email, country, dateOfBirth, phoneNumber, username, password;
    private RadioGroup genderSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstNameEntry);
        secondName = findViewById(R.id.secondNameEntry);
        email = findViewById(R.id.emailAddressEntry);
        country = findViewById(R.id.countryEntry);
        dateOfBirth = findViewById(R.id.dateOfBirthEntry);
        phoneNumber = findViewById(R.id.phoneNumberEntry);
        username = findViewById(R.id.usernameEntry);
        password = findViewById(R.id.passwordEntry);
        genderSelection = findViewById(R.id.radioGroupGender);

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGender = genderSelection.getCheckedRadioButtonId();
                RadioButton gender;

                // Some validations
                if(TextUtils.isEmpty(firstName.getText())){
                    firstName.setError("First name cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(secondName.getText())){
                    secondName.setError("Second name cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(email.getText())){
                    email.setError("Email cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(country.getText())){
                    country.setError("Your nationality cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(dateOfBirth.getText())){
                    dateOfBirth.setError("Your nationality cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(phoneNumber.getText())){
                    phoneNumber.setError("Your nationality cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(username.getText())){
                    username.setError("Your nationality cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(password.getText())){
                    password.setError("Your nationality cannot be empty!");
                    return;
                }

                if(selectedGender != -1){
                    gender = findViewById(selectedGender);
                }else{
                    Toast.makeText(MainActivity.this, "Please choose your gender type!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Intent to move from one activity to another with putExtras(moving with data)
                Intent toWelcomeActivity = new Intent(MainActivity.this, MessageActivity.class);
                toWelcomeActivity.putExtra("first_name", firstName.getText().toString());
                toWelcomeActivity.putExtra("second_name", secondName.getText().toString());
                toWelcomeActivity.putExtra("email_address", email.getText().toString());
                toWelcomeActivity.putExtra("country", country.getText().toString());
                toWelcomeActivity.putExtra("gender", gender.getText().toString());
                toWelcomeActivity.putExtra("date_of_birth", dateOfBirth.getText().toString());
                toWelcomeActivity.putExtra("phone_number", phoneNumber.getText().toString());
                toWelcomeActivity.putExtra("username", username.getText().toString());
                toWelcomeActivity.putExtra("password", password.getText().toString());
                startActivity(toWelcomeActivity);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}