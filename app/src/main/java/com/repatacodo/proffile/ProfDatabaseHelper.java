package com.repatacodo.proffile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class ProfDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GeneralFacultyLounge";
    public static final int DATABASE_VERSION = 1;

    public ProfDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    public void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE PROF (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "PICTURE BLOB," +
                    "TEACHER_TYPE TEXT," +
                    "NAME TEXT," +
                    "NICKNAME TEXT," +
                    "SUBJECT TEXT," +
                    "AGE NUMERIC," +
                    "RECITATION NUMERIC," +
                    "ATTENDANCE NUMERIC," +
                    "CAMERA_STATUS NUMERIC," +
                    "DESCRIPTION TEXT);");

            insertProf(db,
                    null,
                    "lax",
                    "Lexie",
                    "Lexie Grey",
                    "medicine",
                    28,
                    true,
                    true,
                    false,
                    "Relax lang si sir");
        }
    }

    public static void insertProf(SQLiteDatabase db,
                                  byte[] picture,
                                  String teacherType,
                                  String name,
                                  String nickname,
                                  String subject,
                                  int age,
                                  boolean recitation,
                                  boolean attendance,
                                  boolean cameraStatus,
                                  String description) {

        ContentValues profValues = new ContentValues();

        profValues.put("PICTURE", picture);
        profValues.put("TEACHER_TYPE", teacherType);
        profValues.put("NAME", name);
        profValues.put("NICKNAME", nickname);
        profValues.put("SUBJECT", subject);
        profValues.put("AGE", age);
        profValues.put("RECITATION", recitation);
        profValues.put("ATTENDANCE", attendance);
        profValues.put("CAMERA_STATUS", cameraStatus);
        profValues.put("DESCRIPTION", description);

        db.insert("PROF", null, profValues);
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}