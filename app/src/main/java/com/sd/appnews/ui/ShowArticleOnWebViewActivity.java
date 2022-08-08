package com.sd.appnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import com.sd.appnews.R;

public class ShowArticleOnWebViewActivity extends AppCompatActivity {

    WebView wv;
    String  url_article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article_on_web_view);

        Bundle extras = getIntent().getExtras();

        url_article = extras.getString("url");

        wv = (WebView) findViewById(R.id.webview_article);
        openWebPage(url_article);
    }
    //_________________________________________________________________________

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
//_____________________________________________________________________________