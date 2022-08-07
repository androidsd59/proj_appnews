/******************************************************************************
 *
 * material from :
 * Simple HTTP Request with OkHttp - Android Studio Tutorial
 *
 ******************************************************************************/
package com.sd.appnews.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sd.appnews.R;
import com.sd.appnews.data.Article;
import com.sd.appnews.data.InfoJsonArray;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ALLNewsActivity extends AppCompatActivity {

    private final OkHttpClient  client = new OkHttpClient();
    Button                      btn_update;

    public InfoJsonArray        infoJsonArray;

    public ListView             listShortArticles;

    public ALLNewsActivity() throws JSONException {
    }
    //_________________________________________________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        setTitle(R.string.list_news);

        try {
            infoJsonArray = new InfoJsonArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_update = (Button) findViewById(R.id.btn_request_news)  ;

        listShortArticles = (ListView) findViewById(R.id.list);
        listShortArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                long id) {
                HashMap<String, String> stringStringHashMap = infoJsonArray.articlesList.get(position);
                Intent intent = new Intent(ALLNewsActivity.this, ShowArticleActivity.class);

                Article art_position = new Article(
                        stringStringHashMap.get("id"),
                        stringStringHashMap.get("name"),
                        stringStringHashMap.get("author"),
                        stringStringHashMap.get("title"),
                        stringStringHashMap.get("description"),
                        stringStringHashMap.get("url"),
                        stringStringHashMap.get("urlToImage"),
                        stringStringHashMap.get("publishedAt"),
                        stringStringHashMap.get("content"));

                intent.putExtra(Article.class.getSimpleName(), art_position);

                startActivity(new Intent(intent));
            }
        });
    }
    //_________________________________________________________________________
    
    public void OnClickRequest(View v) throws IOException {
//                .url("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1aa472aa48684f7aad47a6cc0fd2d388")
//                //.url("https://newsapi.org/v2/everything?q=tesla&from=2022-07-03&sortBy=publishedAt&apiKey=1aa472aa48684f7aad47a6cc0fd2d388")
//                .build();

        String url = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1aa472aa48684f7aad47a6cc0fd2d388";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    ALLNewsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                infoJsonArray.getElemets(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            ListAdapter adapter = new SimpleAdapter(ALLNewsActivity.this, infoJsonArray.articlesList,
                                    R.layout.list_item, new String[]{ "title", "publishedAt"},
                                    new int[]{R.id.title, R.id.published});
                            listShortArticles.setAdapter(adapter);

                            Log.d("RespBody", "onResponse: " + myResponse);
                        }
                    });
                }
                else
                {
                    ResponseBody responseBody = response.body();
                    Log.d("RespBody", "onResponse: " + responseBody.string());
                }

            }
        });

        btn_update.setText(R.string.update_news);
    }
    //_________________________________________________________________________

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rss, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //_________________________________________________________________________

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.favorites_open:
                    Intent  intent = new Intent(ALLNewsActivity.this, FavoritesActivity.class);
                    startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //_________________________________________________________________________
}
