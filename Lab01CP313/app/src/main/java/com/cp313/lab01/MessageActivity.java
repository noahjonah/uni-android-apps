package com.cp313.lab01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);

        TextView welcome_msg = findViewById(R.id.welcome_msg);
        TextView first_name = findViewById(R.id.first_name_value);
        TextView second_name = findViewById(R.id.second_name_value);
        TextView email_address = findViewById(R.id.email_value);
        TextView country = findViewById(R.id.nationality_value);
        TextView gender = findViewById(R.id.gender_value);
        TextView date_of_birth = findViewById(R.id.date_of_birth_value);
        TextView phone_number = findViewById(R.id.phone_number_value);
        TextView username = findViewById(R.id.username_value);

        // Receiving intent data
        Intent dataReceiver = getIntent();
        String firstName, secondName, emailAddress, nationality, genderData, dateOfBirth, phoneNumber, userName;

        if(dataReceiver != null){
            firstName = dataReceiver.getStringExtra("first_name");
            secondName = dataReceiver.getStringExtra("second_name");
            emailAddress = dataReceiver.getStringExtra("email_address");
            nationality = dataReceiver.getStringExtra("country");
            genderData = dataReceiver.getStringExtra("gender");
            dateOfBirth = dataReceiver.getStringExtra("date_of_birth");
            phoneNumber = dataReceiver.getStringExtra("phone_number");
            userName = dataReceiver.getStringExtra("username");

            // Setting the received intent data into the text views
            welcome_msg.setText(String.format("Welcome %s %s", firstName, secondName));
            first_name.setText(firstName);
            second_name.setText(secondName);
            email_address.setText(emailAddress);
            country.setText(nationality);
            gender.setText(genderData);
            date_of_birth.setText(dateOfBirth);
            phone_number.setText(phoneNumber);
            username.setText(userName);
        }

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(MessageActivity.this, MainActivity.class);
                startActivity(toMainActivity);
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