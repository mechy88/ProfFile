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
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Update info when the activity is restarted

        //get arrays from database using a cursor
        int[] id;
        byte[][] picture;
        String[] teacherType;
        String[] name;
        String[] nickname;
        String[] subject;
        boolean[] recitation;


        SQLiteOpenHelper profDatabaseHelper = new ProfDatabaseHelper(this);
        try {
            SQLiteDatabase db = profDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("PROF", new String[]{"_id", "PICTURE", "TEACHER_TYPE", "NAME", "NICKNAME", "SUBJECT", "RECITATION"}, null, null, null, null, "NAME ASC");

            //Create arrays for data in recycler view, set the length for each array
            id = new int[cursor.getCount()];
            picture = new byte[cursor.getCount()][];
            teacherType = new String[cursor.getCount()];
            name = new String[cursor.getCount()];
            nickname = new String[cursor.getCount()];
            subject = new String[cursor.getCount()];
            recitation = new boolean[cursor.getCount()];

            cursor.moveToFirst();

            //Get info out of the cursor and store in arrays
            for (int i = 0; i < cursor.getCount(); i++) {

                id[i] = cursor.getInt(0);
                picture[i] = cursor.getBlob(1);
                teacherType[i] = cursor.getString(2);
                name[i] = cursor.getString(3);
                nickname[i] = cursor.getString(4);
                subject[i] = cursor.getString(5);
                recitation[i] = cursor.getInt(6) != 0;

                cursor.moveToNext();
            }


            cursor.close();
            db.close();

            //create adapter using array values
            RecyclerView.Adapter<ProfCardAdapter.ViewHolder> adapter = new ProfCardAdapter(picture, teacherType, name, nickname, subject, recitation);
            //set the adapter to the recyclerview
            recyclerView.setAdapter(adapter);
            //set the recyclerView with a LinearLayoutManager
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //Set the click listener of the CardViews in the Recycler view to open the detail activity of the clicked Prof using the id
            ((ProfCardAdapter) adapter).setListener(new ProfCardAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(MainActivity.this, ProfDetailActivity.class);
                    intent.putExtra(ProfDetailActivity.EXTRA_PROF_ID, id[position]);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "An error occurred, contact the developer!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews(){
        toolbar = findViewById(R.id.main_toolbar);
        recyclerView = findViewById(R.id.profList);
    }

    public void onClickDone(View view) {
        Intent intent = new Intent(this, AddProfActivity.class);
        startActivity(intent);
    }

}