package com.example.luchi.nytimessearch.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.luchi.nytimessearch.R;
import com.example.luchi.nytimessearch.model.Article;

import org.parceler.Parcels;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Article article = (Article) Parcels.unwrap(getIntent().getParcelableExtra("article"));
        WebView webView = (WebView)findViewById(R.id.wArticle);
final String url = article.getWebUrl();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
               view.loadUrl(url);
               return true;
            }
        });
webView.loadUrl(article.getWebUrl());
    }

}
