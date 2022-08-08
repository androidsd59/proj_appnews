package com.sd.appnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sd.appnews.R;
import com.sd.appnews.data.dbsqlite.DatabaseHelper;

import okhttp3.internal.ws.RealWebSocket;

public class ReadAndDeleteArticleActivity extends AppCompatActivity {

     TextView   article_author;
     TextView   article_title;
     TextView   article_published;
     Button     btn_read_art;
     Button     btn_delete;

    DatabaseHelper  sqlHelper;
    SQLiteDatabase  db;
    Cursor          userCursor;
    long            userId=0;

    String          url_article = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_article);

        setTitle(R.string.favorite_article_read_delete);

        article_author      = (TextView) findViewById(R.id.tv_favorites_author);
        article_title       = (TextView) findViewById(R.id.tv_favorites_title);
        article_published   = (TextView) findViewById(R.id.tv_favorites_published);
        btn_read_art        = (Button)  findViewById(R.id.btn_read_article);
        btn_delete          = (Button)  findViewById(R.id.btn_delete_article);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();

            article_author.setText(userCursor.getString(3));
            article_title.setText(userCursor.getString(4));
            article_published.setText(userCursor.getString(8));

            url_article = userCursor.getString(6);

            userCursor.close();
        }
    }
    //_________________________________________________________________________

    public void OnClickDeleteArticle(View v) {
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    //_________________________________________________________________________

    public void OnClickReadArticle(View v) {
        Intent intent = new Intent(ReadAndDeleteArticleActivity.this, ShowArticleOnWebViewActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("url", url_article);

        startActivity(intent);
    }
    //_________________________________________________________________________

    private void goHome(){

        db.close();

        Intent intent = new Intent(this, FavoritesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    //_________________________________________________________________________

}
//_____________________________________________________________________________
