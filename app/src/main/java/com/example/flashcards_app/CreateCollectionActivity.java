package com.example.flashcards_app;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateCollectionActivity extends AppCompatActivity {

    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);
        createButton = findViewById(R.id.createButton);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        createButton.setOnClickListener(view -> {
            try {
                DriverManager.getConnection("jdbc:mariadb://localhost:3306/flashcards_db", "root", "example");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}