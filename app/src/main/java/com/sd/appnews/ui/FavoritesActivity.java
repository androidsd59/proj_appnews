/******************************************************************************
 *
 *      Source :
 *      https://metanit.com/java/android/14.1.php
 *
 ******************************************************************************/
package com.sd.appnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.sd.appnews.R;
import com.sd.appnews.data.dbsqlite.DatabaseHelper;

public class FavoritesActivity extends AppCompatActivity {

    TextView            header;
    DatabaseHelper      databaseHelper;
    SQLiteDatabase      db;
    Cursor              articleCursor;
    SimpleCursorAdapter articleAdapter;

    ListView            articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_favorites);

        setTitle(R.string.favorites);

        header = findViewById(R.id.header);
        articleList = findViewById(R.id.list);
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoritesActivity.this, ReadAndDeleteArticleActivity.class);
                intent.putExtra("id", id);

                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
//
//        databaseHelper = new DatabaseHelper(getApplicationContext());
    }
    //_________________________________________________________________________

    @Override
    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        articleCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        String[] headers = new String[] {
                DatabaseHelper.COLUMN_AUTHOR,
                DatabaseHelper.COLUMN_TITLE,
                DatabaseHelper.COLUMN_PUBLISHED,
//                DatabaseHelper.COLUMN_SOURCE_ID,
//                DatabaseHelper.COLUMN_SOURCE_NAME,
//                DatabaseHelper.COLUMN_DESCRIPTION,
//                DatabaseHelper.COLUMN_URL,
//                DatabaseHelper.COLUMN_URL_IMAGE,
//                DatabaseHelper.COLUMN_CONTENT
        };

        articleAdapter = new SimpleCursorAdapter(this,
                R.layout.list_item_faforites,
                articleCursor,
                headers,
                new int[]{
                        R.id.tv_favorites_author,
                        R.id.tv_favorites_title,
                        R.id.tv_favorites_published,
                }, 0);
        String mess = getString(R.string.favorites_items_found);
        header.setText(mess +  articleCursor.getCount());
        articleList.setAdapter(articleAdapter);
    }
    //_________________________________________________________________________

    @Override
    public void onDestroy(){
        super.onDestroy();

        db.close();

        articleCursor.close();
    }
    //_________________________________________________________________________
}
//_____________________________________________________________________________
