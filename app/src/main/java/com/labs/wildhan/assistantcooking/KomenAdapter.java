package com.labs.wildhan.assistantcooking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by WILDHAN on 31/07/2017.
 */

public class KomenAdapter extends RecyclerView.Adapter<KomenAdapter.ViewHolder> {
    ArrayList<KomenModel> komen;
    ArrayList<MenuModel> menu;
    Context context;

    public KomenAdapter(Context context, ArrayList<KomenModel> komen){
        this.komen = komen;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView txtNama;
        public TextView txtKomen;
        public ImageView ivKomen;

        SharedPreferences sharedPreferences;

        public ViewHolder(View v) {
            super(v);
            txtNama = (TextView) v.findViewById(R.id.txtNama);
            txtKomen = (TextView) v.findViewById(R.id.txtKomen);
            ivKomen = (ImageView)v.findViewById(R.id.iv_komen);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_komen,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        SharedPreferences preferences;
        KomenModel komenModel = komen.get(position);
//        MenuModel menuModel = menu.get(position);

//        holder.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = holder.sharedPreferences.edit();
//        editor.putInt("id_komentar", komenModel.getId_komentar());
//        editor.putString("nama_komentar", komenModel.getNama());
//        editor.putString("gambar_komentar", komenModel.getGambar());
//        editor.putString("komentar", komenModel.getKomentar());
//        editor.putInt("id", komenModel.getId());
//        editor.commit();

//        holder.sharedPreferences.getInt("id_komentar", 0);
//        holder.sharedPreferences.getInt("id", 5);



        holder.txtNama.setText(Html.fromHtml(komenModel.getNama()));
        holder.txtKomen.setText(Html.fromHtml(komenModel.getKomentar()));

        Picasso.with(context).load(komenModel.getGambar()).placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder))
                .into(holder.ivKomen);
    }

    @Override
    public int getItemCount() {
        return komen.size();
    }
}
