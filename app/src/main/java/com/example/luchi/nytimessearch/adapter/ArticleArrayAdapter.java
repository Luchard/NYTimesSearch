package com.example.luchi.nytimessearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luchi.nytimessearch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.luchi.nytimessearch.model.Article;

/**
 * Created by luchi on 2/13/2018.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context , List<Article> articles){
        super(context , android.R.layout.simple_list_item_1 , articles);

    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
    Article article = getItem(position);
    if(convertView == null){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article_result , parent , false);
    }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView tvtile = (TextView) convertView.findViewById(R.id.tvArticle);

        tvtile.setText(article.getHeadline());
        String thmubnail = article.getThumbnail();

        if(!TextUtils.isEmpty(thmubnail)){
            Picasso.with(getContext()).load(thmubnail).into(imageView);
        }

        return  convertView;
    }
}
