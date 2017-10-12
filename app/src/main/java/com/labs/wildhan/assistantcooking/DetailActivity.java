package com.labs.wildhan.assistantcooking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static String KEY_ITEM = "item";
    private ImageView imageView;
//    private Button btnKomen;
    private RecyclerView rv_bahan;
    private MenuModel model;
    private ArrayList<MenuModel> menu;
//    private HashMap<String, String> hashMap;
    private DbDataSource db;
    private Boolean favorite;
    private String id_resep, nama_resep, link, gambar, keterangan, bahan, langkah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        menu = new ArrayList<>();

        model = (MenuModel)getIntent().getSerializableExtra(KEY_ITEM);
        final MenuModel menuModel = new MenuModel();
        menuModel.setDeskripsi(model.getDeskripsi());
        menuModel.setBahan(model.getBahan());
        menuModel.setLangkah(model.getLangkah());
        menuModel.setId(model.getId());
        menuModel.setLink(model.getLink());

        menu.add(menuModel);

//        if (hashMap == null || hashMap.isEmpty()){
        id_resep = String.valueOf(model.getId());
        nama_resep = model.getNama();
        link = model.getLink();
        gambar = model.getImage();
        keterangan = model.getDeskripsi();
        bahan = model.getBahan();
        langkah = model.getLangkah();
//        }else {
//            hashMap.get("nama_resep");
//            hashMap.get("gambar");
//            hashMap.get("link");
//            hashMap.get("keterangan");
//            hashMap.get("bahan");
//            hashMap.get("langkah");
//        }


        ((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setTitle(model.getNama());
        imageView = (ImageView)findViewById(R.id.iv_gambar);
        rv_bahan = (RecyclerView) findViewById(R.id.rv_bahan);
//        btnKomen = (Button)findViewById(R.id.btn_komen);
        FloatingActionButton fabStart = (FloatingActionButton)findViewById(R.id.fab_start);

        Picasso.with(DetailActivity.this).load(model.getImage()).into(imageView);

        DetailAdapter adapter = new DetailAdapter(this, menu);

        rv_bahan.setLayoutManager(new LinearLayoutManager(this));
        rv_bahan.setAdapter(adapter);

//        btnKomen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DetailActivity.this, KomentarActivity.class);
//                intent.putExtra(KomentarActivity.KEY_ITEM, menuModel);
//                startActivityForResult(intent, 0);
//            }
//        });

        fabStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, StratActivity.class);
                intent.putExtra(StratActivity.KEY_ITEM, menuModel);
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        db = new DbDataSource(DetailActivity.this);
        db.open();
        favorite = db.isFavorite(Integer.valueOf(model.getId()));

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
                        db.deleteFavorite(Integer.valueOf(model.getId()));
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
