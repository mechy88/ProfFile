package com.repatacodo.proffile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //TODO: FIGURE OUT WHY NOT ALL PROFS RENDER PROPERLY, some cardviews lack info. For context, try running the program
    Button RecyclerViewPlaceholder;
    Button FloatingActionButtonPlaceholder;
    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        //Setup toolbar to work
        setSupportActionBar(toolbar);

        //get arrays from database using a cursor
        byte[][] picture;
        String[] teacherType;
        String[] name;
        String[] nickname;
        String[] subject;
        boolean[] recitation;


        SQLiteOpenHelper profDatabaseHelper = new ProfDatabaseHelper(this);
        try {
            SQLiteDatabase db = profDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("PROF", new String[] {"_id", "PICTURE", "TEACHER_TYPE", "NAME", "NICKNAME", "SUBJECT", "RECITATION"}, null, null, null, null, "NAME ASC");

            //set the length for each array
            picture = new byte[cursor.getCount()][];
            teacherType = new String[cursor.getCount()];
            name = new String[cursor.getCount()];
            nickname = new String[cursor.getCount()];
            subject = new String[cursor.getCount()];
            recitation = new boolean[cursor.getCount()];

            cursor.moveToFirst();

            for(int i = 0; i < cursor.getCount(); i++){
                Log.v("Checking", "Moved to " + i);

                    picture[i] = cursor.getBlob(1);
                    teacherType[i] = cursor.getString(2);
                    name[i] = cursor.getString(3);
                    nickname[i] = cursor.getString(4);
                    subject[i] = cursor.getString(5);
                    recitation[i] = cursor.getInt(6) != 0;
                    Log.v("Checking", "Extracted Values at index " + i);
                cursor.moveToNext();
            }

            Log.v("Checking", Arrays.toString(name));
            //TODO: use these to get a better grasp of the errors

            cursor.close();
            db.close();

            //create adapter using array values
            RecyclerView.Adapter<ProfCardAdapter.ViewHolder> adapter = new ProfCardAdapter(picture, teacherType, name, nickname, subject, recitation);
            //set the adapter to the recyclerview
            recyclerView.setAdapter(adapter);
            //then do a layout manager, but not here
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ((ProfCardAdapter) adapter).setListener(new ProfCardAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(MainActivity.this, ProfDetailActivity.class);
                    intent.putExtra(ProfDetailActivity.EXTRA_PROF_ID, position);
                    startActivity(intent);
                }
            });

        } catch (Exception e){
            Toast.makeText(this, "I hate errors, but here ya go!", Toast.LENGTH_SHORT).show();
        }





    }

    private void initializeViews(){
        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.profList);
    }

    //sets the listener for the adapter
}