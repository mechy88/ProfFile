package com.repatacodo.proffile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class ProfDetailActivity extends AppCompatActivity {
    Toolbar toolbar;

    static String EXTRA_PROF_ID = "bestiesSiSam&Cyrian";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_detail);
        initializeViews();

        //setup toolbar to work
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: make it so that I use the ID to display the Prof details in this activity
        if (getIntent().getExtras() != null){
            getIntent().getExtras().getInt(EXTRA_PROF_ID);
        }
    }

    private void initializeViews(){
        toolbar = findViewById(R.id.detail_toolbar);
    }
}