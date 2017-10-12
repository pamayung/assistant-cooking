package com.labs.wildhan.assistantcooking;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class TipsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ListView listView;
    ArrayList<TipsModel> data;
    RequestQueue requestQueue;
    TipsAdapter adapter;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Tips & Trik Memasak");

        listView = (ListView) findViewById(R.id.lv_tips);
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe_tips);

        data = new ArrayList<>();

        data.clear();
        adapter = new TipsAdapter(this, data);
        listView.setAdapter(adapter);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                adapter.notifyDataSetChanged();
                getData();
            }
        });
    }

    public void getData(){
        swipe.setRefreshing(true);
        String url = "http://pamayung.com/getApi/tipsApi/1/5";
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Succes", response.toString());

                            JSONArray content = response.getJSONArray("content");

                            TipsModel tipsModel = null;
                            for (int i = 0; i < content.length(); i++){
                                tipsModel = new TipsModel();
                                tipsModel.setId(content.getJSONObject(i).getInt("id_tips"));
                                tipsModel.setNama(content.getJSONObject(i).getString("nama_tips"));
                                tipsModel.setLink(content.getJSONObject(i).getString("link_tips"));

                                data.add(tipsModel);
                            }

                            TipsAdapter adapter = new TipsAdapter(TipsActivity.this, data);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TipsModel tips = data.get(position);
                                    Intent intent = new Intent(TipsActivity.this, TipsVideo.class);
                                    intent.putExtra(TipsVideo.KEY_ITEM, tips);
                                    startActivityForResult(intent, 0);
                                }
                            });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(TipsActivity.this, "koneksi terputus", Toast.LENGTH_LONG).show();
                        swipe.setRefreshing(false);
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
        data.clear();
        adapter.notifyDataSetChanged();
        getData();
    }
}
