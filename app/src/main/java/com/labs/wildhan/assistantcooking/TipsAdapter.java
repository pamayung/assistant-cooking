package com.labs.wildhan.assistantcooking;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WILDHAN on 12/07/2017.
 */

public class TipsAdapter extends BaseAdapter {
    ArrayList<TipsModel> list;
    Activity activity;

    public TipsAdapter(Activity activity, ArrayList<TipsModel> list){
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            view = inflater.inflate(R.layout.list_tips_trik, null);

            holder.tvJudul = (TextView)view.findViewById(R.id.tv_nama_tips);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        TipsModel tipsModel = (TipsModel)getItem(position);
        holder.tvJudul.setText(tipsModel.getNama());
        return view;
    }

    static class ViewHolder{
        TextView tvJudul;
    }
}
