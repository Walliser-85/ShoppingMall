package com.example.simploncenter.viewmodel.article;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simploncenter.R;

import java.util.ArrayList;

public class ListViewAllArticle extends ArrayAdapter<String> {
    private ArrayList<String> articlename;
    private ArrayList<String> desc;
    private ArrayList<Integer> imgid;
    private ArrayList<Integer> prices;
    private Activity context;

    public ListViewAllArticle(Activity context, ArrayList<String> articlename, ArrayList<String> desc, ArrayList<Integer> imgid, ArrayList<Integer> prices) {
            super(context, R.layout.fragment_all_articles);

            this.context=context;
            this.articlename=articlename;
            this.desc=desc;
            this.imgid=imgid;
            this.prices = prices;
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

            viewHolder.ivw.setImageResource(imgid.get(position));
            viewHolder.tvw1.setText(articlename.get(position));
            viewHolder.tvw2.setText(desc.get(position));
            viewHolder.tvw3.setText(prices.get(position));

            return r;
            }
    class ViewHolder{
        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageView ivw;
        ViewHolder(View v){
            tvw1 = (TextView) v.findViewById(R.id.tvshopnameA);
            tvw2 = (TextView) v.findViewById(R.id.tvdescriptionA);
            tvw3 = (TextView) v.findViewById(R.id.tvarticlesA);
            ivw = (ImageView) v.findViewById(R.id.imageViewA);
        }
    }
}
