package com.example.simploncenter.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simploncenter.R;

public class CustomListViewArticle extends ArrayAdapter<String> {
    private String[] shopname;
    private String[] desc;
    private Integer[] imgid;
    private Integer[] articles;
    private Activity context;

    public CustomListViewArticle(Activity context, String[] shopname, String[] desc, Integer[] imgid, Integer[] articles) {
        super(context, R.layout.listview_layout, shopname);

        this.context=context;
        this.shopname=shopname;
        this.desc=desc;
        this.imgid=imgid;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        viewHolder.ivw.setImageResource(imgid[position]);
        viewHolder.tvw1.setText(shopname[position]);
        viewHolder.tvw2.setText(desc[position]);
        viewHolder.tvw3.setText("Articles " + Integer.toString(articles[position]));

        return r;
    }
    class ViewHolder{
        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageView ivw;
        ViewHolder(View v){
            tvw1 = (TextView) v.findViewById(R.id.tvshopname);
            tvw2 = (TextView) v.findViewById(R.id.tvdescription);
            tvw3 = (TextView) v.findViewById(R.id.tvarticles);
            ivw = (ImageView) v.findViewById(R.id.imageView);
        }
    }
}
