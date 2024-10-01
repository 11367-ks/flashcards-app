package com.example.flashcards_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;
    ListView flashcardsView;
    URL url;
    HttpURLConnection con;
    Gson gson;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        flashcardsView = findViewById(R.id.listView);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* TODO:
            - Implement refreshing on the main activity,
            - Split networking into multiple threads on both activities,
            - Display flashcards data after clicking on each listview entry,
            - Add flashcards deletion and modification actions,
            - Optimize code and naming conventions,
            - Implement better exception handling mechanism
        */

        try {
            // Single thread networking only for test purposes
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

                    ArrayList<String> flashcardsQuestions = new ArrayList<>();
                    Type flashcardListType = new TypeToken<List<Flashcard>>() {}.getType();
                    gson = new Gson();
                    List<Flashcard> flashcards = gson.fromJson(jsonContent.toString(), flashcardListType);
                    for (Flashcard flashcard : flashcards) {
                        Log.i("INFO", String.format("ID: %s, Question: %s, Answer: %s", flashcard.getId(), flashcard.getQuestion(), flashcard.getAnswer()));
                        flashcardsQuestions.add(flashcard.getQuestion());
                    }
                    adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, flashcardsQuestions);
                    flashcardsView.setAdapter(adapter);

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        button.setOnClickListener(view -> startActivity(new Intent(this, CreateCollectionActivity.class)));
    }
}