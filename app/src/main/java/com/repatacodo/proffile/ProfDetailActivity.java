package com.repatacodo.proffile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    int id;
    ImageView profProfilePic;
    TextView teacherType;
    TextView teacherName;
    TextView teacherNickname;
    TextView subject;
    TextView age;
    TextView recitation;
    TextView attendance;
    TextView camera;
    TextView moreText;
    Button delete;
    Button edit;

    static String EXTRA_PROF_ID = "bestiesSiSam&Cyrian";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_detail);
        initializeViews();
        activateButtons();

        //setup toolbar to work
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null){
            id = getIntent().getExtras().getInt(EXTRA_PROF_ID);
        }

        displayInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void activateButtons(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteOpenHelper profDatabaseHelper = new ProfDatabaseHelper(ProfDetailActivity.this);
                SQLiteDatabase db = profDatabaseHelper.getWritableDatabase();
                db.delete("PROF", "_id = ?", new String[] {String.valueOf(id)});
                finish();
            }
        });

        //TODO: MAKE THIS BUTTON WORK
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfDetailActivity.this, "function coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayInfo(){
        SQLiteOpenHelper profDatabaseHelper = new ProfDatabaseHelper(this);
        SQLiteDatabase db = profDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query("PROF", new String[] {"_id", "PICTURE", "TEACHER_TYPE", "NAME", "NICKNAME", "SUBJECT", "AGE", "RECITATION", "ATTENDANCE", "CAMERA_STATUS", "DESCRIPTION"}, null, null, null, null, "NAME ASC");

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++){
            if (cursor.getInt(0) == id){
                if (cursor.getBlob(1) != null){
                    byte[] blob = cursor.getBlob(1);
                    Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    profProfilePic.setImageBitmap(Bitmap.createScaledBitmap(bmp, /*profProfilePic.getWidth()*/ 2000, /*profProfilePic.getHeight()*/ 2000, false));
                }
                teacherType.setText(cursor.getString(2));
                teacherName.setText(cursor.getString(3));
                teacherNickname.setText(cursor.getString(4));
                subject.setText(cursor.getString(5));
                age.setText(Integer.toString(cursor.getInt(6)));
                if (cursor.getInt(7) != 0){
                    recitation.setText("Required");
                } else {
                    recitation.setText("Optional");
                }
                if (cursor.getInt(8) != 0){
                    attendance.setText("Required");
                } else {
                    attendance.setText("Optional");
                }
                if (cursor.getInt(9) != 0){
                    camera.setText("Required");
                } else {
                    camera.setText("Optional");
                }
                moreText.setText(cursor.getString(10));

            }
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

    }

    private void initializeViews(){
        toolbar = findViewById(R.id.detail_toolbar);
        profProfilePic = findViewById(R.id.profImage_detail);
        teacherType = findViewById(R.id.txt_value_teacher_type_detailActivity);
        teacherName = findViewById(R.id.txt_value_teacher_name_detailActivity);
        teacherNickname = findViewById(R.id.txt_value_teacher_nickname_detailActivity);
        subject = findViewById(R.id.txt_value_teacher_subject_detailActivity);
        age = findViewById(R.id.txt_value_Age_detailActivity);
        recitation = findViewById(R.id.txt_value_recitation_detailActivity);
        attendance = findViewById(R.id.txt_value_attendance_detailActivity);
        camera = findViewById(R.id.txt_value_cam_detailActivity);
        moreText = findViewById(R.id.txt_MoreText);
        delete = findViewById(R.id.btn_delete_detail);
        edit = findViewById(R.id.btn_edit_detail);
    }
}