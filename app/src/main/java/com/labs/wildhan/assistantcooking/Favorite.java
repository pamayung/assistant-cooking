package com.labs.wildhan.assistantcooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.HashMap;

public class Favorite extends AppCompatActivity {
    private ArrayList<MenuModel> listFavorite;
    private StaggeredGridView staggeredGridView;
    private DbDataSource db;
    ArrayList<HashMap<String,String>> arrayList;
    String id_resep, nama_resep, link, gambar, keterangan, bahan, langkah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Favorite");

        arrayList = new ArrayList<>();
        db = new DbDataSource(Favorite.this);
        db.open();

        staggeredGridView = (StaggeredGridView)findViewById(R.id.gv_staggered);

        gvStaggered();
    }

    private void gvStaggered() {
        for (int i = 0; i < db.getFavorite().size(); i++){
            HashMap<String, String> data = new HashMap<>();
            id_resep = data.put("id_resep", String.valueOf(db.getFavorite().get(i).getId()));
            nama_resep = data.put("nama_resep", db.getFavorite().get(i).getNama());
            link = data.put("link", db.getFavorite().get(i).getLink());
            gambar = data.put("gambar", db.getFavorite().get(i).getImage());
            keterangan = data.put("keterangan", db.getFavorite().get(i).getDeskripsi());
            bahan = data.put("bahan", db.getFavorite().get(i).getBahan());
            langkah = data.put("langkah", db.getFavorite().get(i).getLangkah());

            arrayList.add(data);
        }

        ItemFavoriteAdapter gridAdapter =  new ItemFavoriteAdapter(Favorite.this, arrayList);
        staggeredGridView.setAdapter(gridAdapter);
        staggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> data = arrayList.get(position);

                DetailFavorite.id_resep = data.get("id_resep");
                DetailFavorite.nama_resep = data.get("nama_resep");
                DetailFavorite.link = data.get("link");
                DetailFavorite.gambar = data.get("gambar");
                DetailFavorite.keterangan = data.get("keterangan");
                DetailFavorite.bahan = data.get("bahan");
                DetailFavorite.langkah = data.get("langkah");

                Intent intent = new Intent(Favorite.this, DetailFavorite.class);
                Favorite.this.startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
