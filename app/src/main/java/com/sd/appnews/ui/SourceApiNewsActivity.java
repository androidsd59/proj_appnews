package com.sd.appnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sd.appnews.R;

public class SourceApiNewsActivity extends AppCompatActivity {

    public ListView listSourceAllNews;
    public String[] source_news = { "Popularity", "Published", "Business US", "Techcrunch", "The Wall Street Journal"};
    public String[] source_urls = {
            /*Popularity*/  "https://newsapi.org/v2/everything?q=apple&from=2022-08-08&to=2022-08-08&sortBy=popularity&apiKey=1aa472aa48684f7aad47a6cc0fd2d388",
            /*Published*/   "https://newsapi.org/v2/everything?q=tesla&from=2022-07-09&sortBy=publishedAt&apiKey=1aa472aa48684f7aad47a6cc0fd2d388",
            /*Business US*/ "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=1aa472aa48684f7aad47a6cc0fd2d388",
            /*Techcrunch*/  "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1aa472aa48684f7aad47a6cc0fd2d388",
            /*The Wall Street
                   Journal*/ "https://newsapi.org/v2/everything?domains=wsj.com&apiKey=1aa472aa48684f7aad47a6cc0fd2d388",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_api_news);

        setTitle(R.string.api_news_list);

        listSourceAllNews = (ListView) findViewById(R.id.list_all_source);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, source_news);
        listSourceAllNews.setAdapter(adapter);
        listSourceAllNews.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String selectedItem = source_news[position];
                Intent intent_source = new Intent(SourceApiNewsActivity.this, ALLNewsActivity.class);
                intent_source.putExtra(getString(R.string.url_source_parameter), source_urls[position]);
                startActivity(intent_source);
            }
        });
    }
}