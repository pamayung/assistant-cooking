package com.labs.wildhan.assistantcooking;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WILDHAN on 29/03/2017.
 */

public class BahanAdapter extends BaseAdapter {
    private ArrayList<BahanModel> items;
    private Activity activity;

    public BahanAdapter (Activity activity, ArrayList<BahanModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bahan_row, null);
            holder.tvBahan = (TextView)view.findViewById(R.id.tv_list_bahan);
            holder.checkBox = (CheckBox)view.findViewById(R.id.checkbox1);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        BahanModel bahanModel = (BahanModel)getItem(position);

        holder.tvBahan.setText(bahanModel.getBahan());
        holder.checkBox.setChecked(bahanModel.isSelected());

        return view;
    }
    static class ViewHolder{
        TextView tvBahan;
        CheckBox checkBox;
    }
}
