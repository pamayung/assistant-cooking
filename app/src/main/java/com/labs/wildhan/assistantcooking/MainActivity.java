package com.labs.wildhan.assistantcooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.etsy.android.grid.StaggeredGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private StaggeredGridView staggeredGridView;
    private ArrayList<MenuModel> menu;
    private RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefreshLayout;
    ItemGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawable_layout);
        staggeredGridView = (StaggeredGridView)findViewById(R.id.gv_staggered);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        menu = new ArrayList<>();

        menu.clear();
        adapter = new ItemGridAdapter(this, menu);
        staggeredGridView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                adapter.notifyDataSetChanged();
                gvStaggered();
            }
        });

//        gvStaggered();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("Lets go cooking");

        navigationView = (NavigationView)findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.isChecked()){
                    item.setChecked(false);
                }else {
                    item.setChecked(true);
                }
                drawerLayout.closeDrawers();

                switch (item.getItemId()){
                    case R.id.nav_favorite:
                        Intent intent = new Intent(MainActivity.this, Favorite.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_tips:
                        Intent i = new Intent(MainActivity.this, TipsActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.nav_tanggapan:
                        Intent in = new Intent(MainActivity.this, KomentarActivity.class);
                        startActivity(in);
                        return true;
                    case R.id.nav_about:
                        Intent inte = new Intent(MainActivity.this, About.class);
                        startActivity(inte);
                        return true;
                }
                return true;
            }
        });


    }

    private void gvStaggered(){
        swipeRefreshLayout.setRefreshing(true);
        String url = "http://pamayung.com/getApi/resepApi/1/5";
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Succes", response.toString());

                            JSONArray content = response.getJSONArray("content");

                            MenuModel menuModel = null;
                            for(int i = 0; i < content.length(); i++ ){
                                menuModel = new MenuModel();
                                menuModel.setId(content.getJSONObject(i).getInt("id_resep"));
                                menuModel.setNama(content.getJSONObject(i).getString("nama_resep"));
                                menuModel.setLink(content.getJSONObject(i).getString("link"));
                                menuModel.setImage(content.getJSONObject(i).getString("gambar"));
                                menuModel.setDeskripsi(content.getJSONObject(i).getString("keterangan"));
                                menuModel.setBahan(content.getJSONObject(i).getString("bahan"));
                                menuModel.setLangkah(content.getJSONObject(i).getString("langkah"));

                                menu.add(menuModel);
                            }

                            ItemGridAdapter gridAdapter = new ItemGridAdapter(MainActivity.this, menu);
                            staggeredGridView.setAdapter(gridAdapter);
                            staggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    MenuModel menuModel = menu.get(position);

                                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                    intent.putExtra(DetailActivity.KEY_ITEM, menuModel);
                                    startActivityForResult(intent, 0);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(MainActivity.this, "koneksi terputus", Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        requestQueue.add(objectRequest);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        menu.clear();
        adapter.notifyDataSetChanged();
        gvStaggered();
    }
}
