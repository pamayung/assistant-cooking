package com.labs.wildhan.assistantcooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailFavorite extends AppCompatActivity {
    private ImageView imageView;
    private RecyclerView rv_bahan;
    private MenuModel model;
    private ArrayList<FavoriteModel> menu;
    private ArrayList<HashMap<String,String>> hashMap;
    private DbDataSource db;
    private Boolean favorite;
    public static String id_resep, nama_resep, link, gambar, keterangan, bahan, langkah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        btnStrat = (Button)findViewById(R.id.btn_start);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        hashMap = new ArrayList<>();

        HashMap<String, String> data = new HashMap<>();
        data.put("link", link);
        data.put("keterangan", keterangan);
        data.put("bahan", bahan);
        data.put("langkah", langkah);

        hashMap.add(data);

        final FavoriteModel model = new FavoriteModel();
        model.setLink(link);

//        menu.add(model);

        ((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setTitle(nama_resep);
        imageView = (ImageView)findViewById(R.id.iv_gambar);
        rv_bahan = (RecyclerView) findViewById(R.id.rv_bahan);
        FloatingActionButton fabStart = (FloatingActionButton)findViewById(R.id.fab_start);

        Picasso.with(DetailFavorite.this).load(gambar).into(imageView);

        FavoriteAdapter adapter = new FavoriteAdapter(this, hashMap);

        rv_bahan.setLayoutManager(new LinearLayoutManager(this));
        rv_bahan.setAdapter(adapter);
//
        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailFavorite.this, StartFavorite.class);
                intent.putExtra(StartFavorite.KEY_ITEM, model);
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        db = new DbDataSource(DetailFavorite.this);
        db.open();
        favorite = db.isFavorite(Integer.valueOf(id_resep));

        if (favorite){
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        }else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
//        if (fab != null){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (favorite){
                    Log.i("CEK", String.valueOf(favorite));
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    db.deleteFavorite(Integer.valueOf(id_resep));
                    favorite = false;
                    Snackbar.make(view, "Terhapus dari Favorit", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp);
                    favorite = true;
                    if (db.tambahFavorite(id_resep, nama_resep, link, gambar, keterangan,
                            bahan, langkah)){

                    }else {

                    }
                    Snackbar.make(view, "Tersimpan di Favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
//        }

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
