package com.labs.wildhan.assistantcooking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by WILDHAN on 19/07/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.myViewHolder> {
    Context context;
    ArrayList<HashMap<String,String>> data;

    public FavoriteAdapter(Context context, ArrayList<HashMap<String,String>> data){
        this.context = context;
        this.data = data;

    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final HashMap<String, String> i = data.get(position);

        holder.tvKeterangan.setText(Html.fromHtml(i.get("keterangan")));
        holder.tvBahan.setText(Html.fromHtml(i.get("bahan")));
        holder.tvLangkah.setText(Html.fromHtml(i.get("langkah")));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView tvKeterangan, tvBahan, tvLangkah;

        public myViewHolder(View itemView){
            super(itemView);
            tvKeterangan = (TextView) itemView.findViewById(R.id.tv_keterangan);
            tvBahan = (TextView) itemView.findViewById(R.id.tv_bahan);
            tvLangkah = (TextView) itemView.findViewById(R.id.tv_langkah);
        }
    }
}
