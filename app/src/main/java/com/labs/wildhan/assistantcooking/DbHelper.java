package com.labs.wildhan.assistantcooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WILDHAN on 07/06/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cooking.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_NAMA = "resep";
    public static final String COL_ID = "id_resep";
    public static final String COL_NAMA = "nama_resep";
    public static final String COL_LINK = "link";
    public static final String COL_GAMBAR = "gambar";
    public static final String COL_KETERANGAN = "keterangan";
    public static final String COL_BAHAN = "bahan";
    public static final String COL_LANGKAH = "langkah";

    public static final String SQL_CREATE = "create table "
            + TABLE_NAMA + "("
            + COL_ID +" integer primary key, "
            + COL_NAMA +" varchar(35) not null, "
            + COL_LINK +" varchar(225) not null, "
            + COL_GAMBAR +" varchar(225) not null, "
            + COL_KETERANGAN +" text not null, "
            + COL_BAHAN +" text not null, "
            + COL_LANGKAH +" text not null);";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
        Log.d("Data", "onCreate" +SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMA);
        onCreate(db);
    }

}
