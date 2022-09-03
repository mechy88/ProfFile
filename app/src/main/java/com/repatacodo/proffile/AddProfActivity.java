package com.repatacodo.proffile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class AddProfActivity extends AppCompatActivity {
    ImageView profProfilePic;
    Bitmap bitmap = null;
    Toolbar toolbar;
    EditText teacherType;
    EditText fullname;
    EditText nickname;
    EditText subject;
    EditText age;
    Spinner recitation;
    Spinner attendance;
    Spinner camera;
    EditText description;
    private static final int galleryPick = 101101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prof);
        initializeViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Upload an image when profile pic is clicked
        profProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, galleryPick);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(AddProfActivity.this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    bitmap = ProfDatabaseHelper.resizeBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (bitmap != null){
                    profProfilePic.setImageBitmap(bitmap);
                }
                //Save image wherever you want to save it
            }

        }

    }

    private void initializeViews(){
        toolbar = findViewById(R.id.add_toolbar);
        teacherType = findViewById(R.id.EditText_value_teacher_type_AddProfActivity);
        fullname = findViewById(R.id.EditText_value_fullname_AddProfActivity);
        nickname = findViewById(R.id.EditText_value_nickname_AddProfActivity);
        subject = findViewById(R.id.EditText_value_teacher_subject_AddProfActivity);
        age = findViewById(R.id.EditText_value_Age_AddProfActivity);
        recitation = findViewById(R.id.Spinner_value_Recitation_AddProfActivity);
        attendance = findViewById(R.id.Spinner_value_Attendance_AddProfActivity);
        camera = findViewById(R.id.Spinner_value_Camera_AddProfActivity);
        description = findViewById(R.id.EditText_value_Description_AddProfActivity);
        profProfilePic = findViewById(R.id.profImage_add);
    }

    //converts answer in spinners to boolean to be stored
    private boolean requiredToBoolean(Spinner s){
        String answer;
        answer = String.valueOf(s.getSelectedItem());
        return answer.equals("Required");
    }

    public void onClickDone(View view) {

        final Bitmap bmap = ((BitmapDrawable)profProfilePic.getDrawable()).getBitmap();
        Drawable myDrawable = getResources().getDrawable(R.drawable.upload_image);
        final Bitmap templateProf = ((BitmapDrawable) myDrawable).getBitmap();

        //check if all are not empty, if empty tell user to fill all fields
        if (!(bmap.sameAs(templateProf))
                && !(teacherType.getText().toString().matches(""))
                && !(fullname.getText().toString().matches(""))
                && !(nickname.getText().toString().matches(""))
                && !(subject.getText().toString().matches(""))
                && !(age.getText().toString().matches(""))
        ) {
            SQLiteOpenHelper profDatabaseHelper = new ProfDatabaseHelper(this);
            try {
                SQLiteDatabase db = profDatabaseHelper.getWritableDatabase();
                Toast.makeText(this, fullname.getText().toString(), Toast.LENGTH_LONG).show();
                ProfDatabaseHelper.insertProf(db, ProfDatabaseHelper.getBitmapAsByteArray(bitmap), teacherType.getText().toString(), fullname.getText().toString(), nickname.getText().toString(), subject.getText().toString(), age.getText().toString(), requiredToBoolean(recitation), requiredToBoolean(attendance), requiredToBoolean(camera), description.getText().toString());
                //set the length for each array
                db.close();

            } catch (Exception e) {
                Toast.makeText(this, "I hate errors, but here ya go!", Toast.LENGTH_SHORT).show();
            }
            super.finish();
        } else {
            Toast.makeText(AddProfActivity.this, "Input correct values! Don't leave Blanks!", Toast.LENGTH_SHORT).show();
        }
    }
}