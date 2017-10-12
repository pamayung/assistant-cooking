package com.labs.wildhan.assistantcooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by WILDHAN on 08/06/2017.
 */

public class DbDataSource {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    private String[] allCollumns = {
            DbHelper.COL_ID, DbHelper.COL_NAMA, DbHelper.COL_LINK, DbHelper.COL_GAMBAR,
            DbHelper.COL_KETERANGAN, DbHelper.COL_BAHAN, DbHelper.COL_LANGKAH
    };

    public DbDataSource(Context context){
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Boolean isFavorite(Integer id){

        Cursor cursor = database.query(DbHelper.TABLE_NAMA, allCollumns, DbHelper.COL_ID +" = " +id,null, null, null, null, null);
        if (cursor.getCount() >0) return true;
        else return false;

    }

    public Boolean tambahFavorite(String id_resep, String nama_resep, String link,
                                  String gambar, String keterangan, String bahan,
                                  String langkah){

        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_ID, id_resep);
        values.put(DbHelper.COL_NAMA, nama_resep);
        values.put(DbHelper.COL_LINK, link);
        values.put(DbHelper.COL_GAMBAR, gambar);
        values.put(DbHelper.COL_KETERANGAN, keterangan);
        values.put(DbHelper.COL_BAHAN, bahan);
        values.put(DbHelper.COL_LANGKAH, langkah);

        long insertId = database.insert(DbHelper.TABLE_NAMA, null, values);

        if (insertId == -1) return false;
        else return true;
    }

    public void deleteFavorite(Integer id_resep){
        String strFilter = "id_resep = " + id_resep;
        database.delete(DbHelper.TABLE_NAMA, strFilter, null);
    }

    public MenuModel cursorToFavorite(Cursor cursor){
        MenuModel favoriteModel = new MenuModel();

        favoriteModel.setId(cursor.getInt(0));
        favoriteModel.setNama(cursor.getString(1));
        favoriteModel.setLink(cursor.getString(2));
        favoriteModel.setImage(cursor.getString(3));
        favoriteModel.setDeskripsi(cursor.getString(4));
        favoriteModel.setBahan(cursor.getString(5));
        favoriteModel.setLangkah(cursor.getString(6));

        return favoriteModel;
    }

    public ArrayList<MenuModel> getFavorite(){
        ArrayList<MenuModel> daftarFavorite = new ArrayList<MenuModel>();

        Cursor cursor = database.query(DbHelper.TABLE_NAMA, allCollumns, null, null, null, null, null, null);
        //pindah ke data paling pertama
        cursor.moveToFirst();
        //jika masih ada data, masukan data ke daftar
        while(!cursor.isAfterLast()){
            MenuModel favoriteModel = cursorToFavorite(cursor);
            daftarFavorite.add(favoriteModel);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarFavorite;
    }
}
