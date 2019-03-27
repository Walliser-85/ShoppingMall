package com.example.simploncenter.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ShopEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomListView extends ArrayAdapter<String> {
    private List<ShopEntity> list;
    private Activity context;
    Integer[] imgid={R.drawable.migros, R.drawable.ca, R.drawable.hm, R.drawable.interdiscount};

    public CustomListView(Activity context, List<ShopEntity> list) {
        super(context, R.layout.listview_layout);
        Log.i("########", "CustomListView Constructor" + list.size());
        this.context=context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("###", "getView");
        View r = convertView;
        ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) r.getTag();
        }

        viewHolder.ivw.setImageResource(imgid[0]);
        viewHolder.tvw1.setText(list.get(position).getShopName());
        viewHolder.tvw2.setText(list.get(position).getDescription());

        return r;
    }

    public void updateData(List<ShopEntity> shopList) {
        Log.i("########", "UpdateData");
        list.clear();
        list.addAll(shopList);
        notifyDataSetChanged();
    }

    class ViewHolder{
        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        ViewHolder(View v){
            tvw1 = (TextView) v.findViewById(R.id.tvshopname);
            tvw2 = (TextView) v.findViewById(R.id.tvdescription);
            ivw = (ImageView) v.findViewById(R.id.imageView);
            Log.i("###", "ViewHolder");
        }
    }
}
