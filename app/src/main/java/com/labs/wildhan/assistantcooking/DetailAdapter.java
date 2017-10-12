package com.labs.wildhan.assistantcooking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WILDHAN on 23/03/2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.myViewHolder> {
    Context context;
    ArrayList<MenuModel> menu;
    ArrayList<BahanModel> bahan;

    public DetailAdapter(Context context, ArrayList<MenuModel>menu){
        this.context = context;
        this.menu = menu;

    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final MenuModel model = menu.get(position);

        holder.tvKeterangan.setText(Html.fromHtml(model.getDeskripsi()));
        holder.tvBahan.setText(Html.fromHtml(model.getBahan()));
        holder.tvLangkah.setText(Html.fromHtml(model.getLangkah()));
    }

    @Override
    public int getItemCount() {
        return menu.size();
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
