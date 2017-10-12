package com.labs.wildhan.assistantcooking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KomentarActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RequestQueue requestQueue;
    private ArrayList<KomenModel> komenList;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swip;
    KomenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tanggapan");

        recyclerView = (RecyclerView)findViewById(R.id.rv_komen);
        swip = (SwipeRefreshLayout)findViewById(R.id.swipe_main);

        komenList = new ArrayList<>();

        komenList.clear();
        adapter = new KomenAdapter(getApplicationContext(), komenList);
        recyclerView.setAdapter(adapter);
        swip.setOnRefreshListener(this);
        swip.post(new Runnable() {
            @Override
            public void run() {
                swip.setRefreshing(true);
                adapter.notifyDataSetChanged();
                getData();
            }
        });

//        getData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KomentarActivity.this, TulisKomenActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getData(){
        swip.setRefreshing(true);
        String url = "http://pamayung.com/getApi/komenApi/1/5";
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Succes", response.toString());

                            JSONArray content = response.getJSONArray("content");

                            KomenModel komenModel = null;
                            for(int i = 0; i < content.length(); i++ ){
                                komenModel = new KomenModel();
                                komenModel.setId_komentar(content.getJSONObject(i).getInt("id_komentar"));
                                komenModel.setNama(content.getJSONObject(i).getString("nama_komentar"));
                                komenModel.setGambar(content.getJSONObject(i).getString("gambar_komentar"));
                                komenModel.setKomentar(content.getJSONObject(i).getString("komentar"));

                                komenList.add(komenModel);

                                final KomenAdapter adapter = new KomenAdapter(KomentarActivity.this, komenList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(KomentarActivity.this));
                                recyclerView.setAdapter(adapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        swip.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(KomentarActivity.this, "koneksi terputus", Toast.LENGTH_LONG).show();
                        swip.setRefreshing(false);
                    }
                });
        requestQueue.add(objectRequest);
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

    @Override
    public void onRefresh() {
        komenList.clear();
        adapter.notifyDataSetChanged();
        getData();
    }
}
