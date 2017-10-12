package com.labs.wildhan.assistantcooking;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by WILDHAN on 11/06/2017.
 */

public class ItemFavoriteAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> data_list;
//    ArrayList<MenuModel> favorite;
    Activity activity;

    public ItemFavoriteAdapter(Activity activity, ArrayList<HashMap<String, String>> data_list){
        this.data_list = data_list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_grid, null);
            holder.imgItem = (ImageView)view.findViewById(R.id.img_item);
            view.setTag(holder);
            holder.tvNama = (TextView)view.findViewById(R.id.tv_name);
        }
        final HashMap<String, String> data = data_list.get(position);
        MenuModel model = new MenuModel();

        holder.tvNama.setText(data.get("nama_resep"));
        Picasso.with(activity).load(data.get("gambar")).placeholder(ContextCompat.getDrawable(activity, R.drawable.placeholder))
                .into(holder.imgItem);

        return view;
    }
    static class ViewHolder{
        ImageView imgItem;
        TextView tvNama;
    }
}
