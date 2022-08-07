/***************************************************************************
 *
 *      source downloadAndShowImage
 *      https://betacode.net/10527/android-networking
 *
 **************************************************************************/
package com.sd.appnews.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import com.sd.appnews.data.dbsqlite.DatabaseHelper;
import com.sd.appnews.utils.DownloadImageTask;
import com.sd.appnews.R;
import com.sd.appnews.data.Article;

import java.io.*;


public class ShowArticleActivity extends AppCompatActivity {
    int PARAMETER_ART_SOURCE_ID     = 0;
    int PARAMETER_ART_SOURCE_NAME   = 1;
    int PARAMETER_ART_AUTHOR        = 2;
    int PARAMETER_ART_TITLE         = 3;
    int PARAMETER_ART_DESCRIPTION   = 4;
    int PARAMETER_ART_URL_SOURCE    = 5;
    int PARAMETER_ART_URL_TO_IMAGE  = 6;
    int PARAMETER_ART_PABLISHED     = 7;
    int PARAMETER_ART_CONTENT       = 8;

    String[] arcticle = {/*"source id"*/    "techcrunch",
                        /*"source name"*/   "TechCrunch",
                        /*"author"*/        "Anna Heim",
                        /*"title"*/         "Stocks with friends",
                        /*"description"*/   "Several startups want to make it easier and friendlier for individuals to buy stocks. But isn't pandemic-era stock picking just a bad habit that'd better be left behind?",
                        /*"url"*/           "https://techcrunch.com/2022/07/30/stocks-with-friends/",
                        /*"urlToImage"*/    "https://techcrunch.com/wp-content/uploads/2020/06/NSussman_Techcrunch_Exchange-multicolor.jpg?w=533",
                        /*"publishedAt"*/   "2022-07-30T14:00:43Z",
                        /*"content"*/       "Welcome to The TechCrunch Exchange, a weekly startups-and-markets newsletter. Its inspired by the daily TechCrunch+ column where it gets its name. Want it in your inbox every Saturday? Sign up here.\r… [+440 chars]"
    };

    TextView    textView_id;
    TextView    textView_name;
    TextView    textView_author_value;
    TextView    textView_title_value;
    TextView    textView_description_value;
    TextView    textView_source_value;
    TextView    textView_urlToImage_value;
    TextView    textView_publishedAt_value;
    TextView    textView_content_value;

    ImageView   imgView;

    DatabaseHelper  sqlHelper;
    SQLiteDatabase  db;
    Cursor          userCursor;
    long            userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article);

        setTitle(R.string.show_article);

        textView_id                 = (TextView) findViewById(R.id.tV_source_id);
        textView_name               = (TextView) findViewById(R.id.tV_source_name);
        textView_author_value       = (TextView) findViewById(R.id.tV_author_value);
        textView_title_value        = (TextView) findViewById(R.id.tV_title_value);
        textView_description_value  = (TextView) findViewById(R.id.tV_description_value);
        textView_source_value       = (TextView) findViewById(R.id.tV_url_source_value);
        textView_urlToImage_value   = (TextView) findViewById(R.id.tV_urlToImage_value);
        textView_publishedAt_value  = (TextView) findViewById(R.id.tV_publishedAt_value);
        textView_content_value      = (TextView) findViewById(R.id.tV_content_value);

        imgView                     = (ImageView) findViewById(R.id.imageView);

        Bundle argBundle = getIntent().getExtras();

        Article articleBundle;

        if (argBundle != null)
        {
            articleBundle = (Article) argBundle.getSerializable(Article.class.getSimpleName());
            arcticle[PARAMETER_ART_SOURCE_ID] = articleBundle.getId();
            arcticle[PARAMETER_ART_SOURCE_NAME] = articleBundle.getSource_name();
            arcticle[PARAMETER_ART_AUTHOR] = articleBundle.getAuthor();
            arcticle[PARAMETER_ART_TITLE] = articleBundle.getTitle();
            arcticle[PARAMETER_ART_DESCRIPTION] = articleBundle.getDescription();
            arcticle[PARAMETER_ART_URL_SOURCE] = articleBundle.getUrl();
            arcticle[PARAMETER_ART_URL_TO_IMAGE] = articleBundle.getUrlToImage();
            arcticle[PARAMETER_ART_PABLISHED] = articleBundle.getPublishedAt();
            arcticle[PARAMETER_ART_CONTENT] = articleBundle.getContent();
        }

        try {
            show_data();
        }
        catch (IOException ioe)
        {
            Log.d("ExcepImage", getString(R.string.sa_image_not_load));
        }

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        // 0    - статьи нет в избранных
        // > 0  - статья есть в избранных в избранных

        // если 0, то добавление
        //if (userId > 0) {
        if (userId == 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
//            nameBox.setText(userCursor.getString(1));
//            yearBox.setText(String.valueOf(userCursor.getInt(2)));
            userCursor.close();

//            Toast.makeText(this, "Статья добавляется в избранное"  //"No default network is currently active",
//                    , Toast.LENGTH_LONG).show();
        }
//        else {
//            Toast.makeText(this, "Статья ранее размещена в избранное"  //"No default network is currently active",
//                    , Toast.LENGTH_LONG).show();
//        }

    }
    //_________________________________________________________________________

    public void show_data() throws IOException {
        textView_id.setText(               arcticle[PARAMETER_ART_SOURCE_ID]);
        textView_name.setText(             arcticle[PARAMETER_ART_SOURCE_NAME]);
        textView_author_value.setText(     arcticle[PARAMETER_ART_AUTHOR]);
        textView_title_value.setText(      arcticle[PARAMETER_ART_TITLE]);
        textView_description_value.setText(arcticle[PARAMETER_ART_DESCRIPTION]);
        textView_source_value.setText(     arcticle[PARAMETER_ART_URL_SOURCE]);
        textView_urlToImage_value.setText( arcticle[PARAMETER_ART_URL_TO_IMAGE]);
        textView_publishedAt_value.setText(arcticle[PARAMETER_ART_PABLISHED]);
        textView_content_value.setText(    arcticle[PARAMETER_ART_CONTENT]);

        downloadAndShowImage(arcticle[PARAMETER_ART_URL_TO_IMAGE]);
    }
    //_________________________________________________________________________

    private boolean checkInternetConnection() {
        // Get Connectivity Manager
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Details about the currently active default data network
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, getString(R.string.sa_no_default_network)  //"No default network is currently active",
                    , Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isConnected()) {
            Toast.makeText(this, getString(R.string.sa_network_is_not_connected)//"Network is not connected"
                    , Toast.LENGTH_LONG).show();
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, getString(R.string.sa_network_not_available)//"Network not available"
                    , Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, getString(R.string.sa_network_OK)//"Network OK"
                    , Toast.LENGTH_LONG).show();
        return true;
    }
    //_________________________________________________________________________

    public void downloadAndShowImage(String strUrl) {
        boolean networkOK = this.checkInternetConnection();
        if (!networkOK) {
            return;
        }
        String imageUrl = strUrl;

        // Create a task to download and display image.
        DownloadImageTask task = new DownloadImageTask(this.imgView);

        // Execute task (Pass imageUrl).
        task.execute(imageUrl);
    }
    //_________________________________________________________________________

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_art, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //_________________________________________________________________________

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id) {
            case R.id.favorites_open:
                intent = new Intent(ShowArticleActivity.this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            case R.id.favorites_add_art:
                //intent = new Intent(ShowArticleActivity.this, FavoritesActivity.class);
                //startActivity(intent);

                ContentValues cv = new ContentValues();
//                cv.put(DatabaseHelper.COLUMN_NAME, nameBox.getText().toString());
//                cv.put(DatabaseHelper.COLUMN_YEAR, Integer.parseInt(yearBox.getText().toString()));
                cv.put(DatabaseHelper.COLUMN_SOURCE_ID,     arcticle[PARAMETER_ART_SOURCE_ID]);
                cv.put(DatabaseHelper.COLUMN_SOURCE_NAME,   arcticle[PARAMETER_ART_SOURCE_NAME]);
                cv.put(DatabaseHelper.COLUMN_AUTHOR,        arcticle[PARAMETER_ART_AUTHOR]);
                cv.put(DatabaseHelper.COLUMN_TITLE,         arcticle[PARAMETER_ART_TITLE]);
                cv.put(DatabaseHelper.COLUMN_DESCRIPTION,   arcticle[PARAMETER_ART_DESCRIPTION]);
                cv.put(DatabaseHelper.COLUMN_URL,           arcticle[PARAMETER_ART_URL_SOURCE]);
                cv.put(DatabaseHelper.COLUMN_URL_IMAGE,     arcticle[PARAMETER_ART_URL_TO_IMAGE]);
                cv.put(DatabaseHelper.COLUMN_PUBLISHED,     arcticle[PARAMETER_ART_PABLISHED]);
                cv.put(DatabaseHelper.COLUMN_CONTENT,       arcticle[PARAMETER_ART_CONTENT]);

                //if (userId > 0) {
                //db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null);
                //db.insert(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null);
                try {
                    db.insert(DatabaseHelper.TABLE, null, cv);
                }
                catch (Exception ex)
                {
                    Toast.makeText(ShowArticleActivity.this, getString(R.string.exception_sqlite), Toast.LENGTH_LONG).show();
                }
                goHome();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //_________________________________________________________________________

    private void goHome() {
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, ShowArticleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    //_________________________________________________________________________

}
//_____________________________________________________________________________
