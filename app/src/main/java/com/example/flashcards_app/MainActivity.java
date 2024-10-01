package com.example.flashcards_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        URL url;
        HttpURLConnection con;
        Gson gson;

        //TODO: Finish JSON reading from PHP API
        try {
            // Single thread networking only for test purposes
            // TODO: split networking into multiple threads
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            url = new URL("http://10.0.2.2/public/index.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                try (InputStream in = con.getInputStream();
                     InputStreamReader isr = new InputStreamReader(in);
                     BufferedReader br = new BufferedReader(isr)) {

                    StringBuilder jsonContent = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        jsonContent.append(line);
                    }

                    Type flashcardListType = new TypeToken<List<Flashcard>>() {}.getType();
                    gson = new Gson();
                    List<Flashcard> flashcards = gson.fromJson(jsonContent.toString(), flashcardListType);
                    for (Flashcard flashcard : flashcards) {
                        Log.i("INFO", String.format("ID: %s, Question: %s, Answer: %s", flashcard.getId(), flashcard.getQuestion(), flashcard.getAnswer()));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button.setOnClickListener(view -> startActivity(new Intent(this, CreateCollectionActivity.class)));
    }
}