package com.repatacodo.proffile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class ProfDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GeneralFacultyLounge";
    public static final int DATABASE_VERSION = 1;

    public ProfDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
                    "AGE TEXT," +
                    "RECITATION NUMERIC," +
                    "ATTENDANCE NUMERIC," +
                    "CAMERA_STATUS NUMERIC," +
                    "DESCRIPTION TEXT);");

            insertProf(db,
                    null,
                    "lax",
                    "Lexie Grey",
                    "Lexie",
                    "medicine",
                    "28",
                    true,
                    true,
                    false,
                    "She has a photographic memory");
            insertProf(db,
                    null,
                    "lax",
                    "Meredith Grey",
                    "Mer",
                    "General Surgery",
                    "32",
                    false,
                    true,
                    false,
                    "Won many awards");
            insertProf(db,
                    null,
                    "lax",
                    "Derek Shephered",
                    "Derek",
                    "Neurosurgery",
                    "28",
                    false,
                    true,
                    false,
                    "Relax lang si sir");
            insertProf(db,
                    null,
                    "terror",
                    "Paul Joshua Aman",
                    "PJ",
                    "Statistics",
                    "18",
                    true,
                    true,
                    false,
                    "Gets mad when you bad");
            insertProf(db,
                    null,
                    "lax",
                    "Audrey Jeanelle Aman",
                    "Te Jeanelle",
                    "Telecommunications Engineering",
                    "28",
                    false,
                    true,
                    false,
                    "Chill si Madam");
        }
    }

    public static void insertProf(SQLiteDatabase db,
                                  byte[] picture,
                                  String teacherType,
                                  String name,
                                  String nickname,
                                  String subject,
                                  String age,
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

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        Canvas canvas = new Canvas();
        Bitmap resizedBitmap = null;
        if (bitmap != null) {
            int h = bitmap.getHeight();
            int w = bitmap.getWidth();
            int newWidth = 1000;
            int newHeight = 1000;

            float scaleWidth = ((float) newWidth) / w;
            float scaleHeight = ((float) newHeight) / h;

            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.preScale(scaleWidth, scaleHeight);

            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);

            canvas.drawBitmap(resizedBitmap, matrix, paint);


        }


        return resizedBitmap;
    }

}
