package com.repatacodo.proffile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button RecyclerViewPlaceholder;
    Button FloatingActionButtonPlaceholder;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        //Setup toolbar to work
        setSupportActionBar(toolbar);


    }

    private void initializeViews(){
        //set up the recycler views for the profs
        RecyclerViewPlaceholder = findViewById(R.id.button);
        RecyclerViewPlaceholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfDetailActivity.class);
                startActivity(intent);
            }
        });

        //set up the floating action button to add more profs
        FloatingActionButtonPlaceholder = findViewById(R.id.button2);
        FloatingActionButtonPlaceholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProfActivity.class);
                startActivity(intent);
            }
        });

        toolbar = findViewById(R.id.main_toolbar);
    }

}