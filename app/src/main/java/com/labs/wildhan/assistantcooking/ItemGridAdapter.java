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

/**
 * Created by WILDHAN on 17/03/2017.
 */

public class ItemGridAdapter extends BaseAdapter{
    ArrayList<MenuModel> menu;
    Activity activity;

    public ItemGridAdapter (Activity activity, ArrayList<MenuModel> menu){
        this.activity = activity;
        this.menu = menu;
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
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
        }else {
            holder = (ViewHolder)view.getTag();
        }
        MenuModel menuModel = (MenuModel)getItem(position);

        holder.tvNama.setText(menuModel.getNama());
        Picasso.with(activity).load(menuModel.getImage()).placeholder(ContextCompat.getDrawable(activity, R.drawable.placeholder))
                .into(holder.imgItem);
        return view;
    }
    static class ViewHolder{
        ImageView imgItem;
        TextView tvNama;
    }
}
