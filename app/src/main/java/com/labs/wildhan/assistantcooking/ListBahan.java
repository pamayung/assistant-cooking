package com.labs.wildhan.assistantcooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListBahan extends AppCompatActivity {
    public static String KEY_ITEM = "item";
    private ListView lvBahan;
    private MenuModel model;
    private ArrayList<MenuModel> menu;
    private ArrayList<BahanModel> items;
    private Button mulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bahan);

        menu = new ArrayList<>();
        items = new ArrayList<>();

        lvBahan = (ListView) findViewById(R.id.lv_bahan);
        mulai = (Button)findViewById(R.id.btn_mulai);

        model = (MenuModel)getIntent().getSerializableExtra(KEY_ITEM);

        final MenuModel menuModel = new MenuModel();
        menuModel.setBahan(model.getBahan());
        menuModel.setLangkah(model.getLangkah());
        menuModel.setLink(model.getLink());
        menu.add(menuModel);

        BahanModel bahan = null;
        String bahans[] = model.getBahan().split("<br>");
        for (int x = 0; x < bahans.length; x++){
            bahan = new BahanModel(bahans[x], false);
            items.add(bahan);
        }

        BaseAdapter adapter = new BahanAdapter(this, items);
        lvBahan.setAdapter(adapter);

        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBahan.this, StratActivity.class);
                intent.putExtra(StratActivity.KEY_ITEM, menuModel);
                startActivityForResult(intent, 0);
            }
        });
    }
}
