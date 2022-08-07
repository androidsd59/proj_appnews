package com.sd.appnews.data.dbsqlite;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "newsart_favorites.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "articles"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID            = "_id";
    public static final String COLUMN_SOURCE_ID     = "source_id";
    public static final String COLUMN_SOURCE_NAME   = "source_name";
    public static final String COLUMN_AUTHOR        = "author";
    public static final String COLUMN_TITLE         = "title";
    public static final String COLUMN_DESCRIPTION   = "description";
    public static final String COLUMN_URL           = "url";
    public static final String COLUMN_URL_IMAGE     = "urlToImage";
    public static final String COLUMN_PUBLISHED     = "publishedAt";
    public static final String COLUMN_CONTENT       = "content";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE articles (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                            + COLUMN_SOURCE_ID      + " TEXT, "
                                            + COLUMN_SOURCE_NAME    + " TEXT, "
                                            + COLUMN_AUTHOR         + " TEXT, "
                                            + COLUMN_TITLE          + " TEXT, "
                                            + COLUMN_DESCRIPTION    + " TEXT, "
                                            + COLUMN_URL            + " TEXT, "
                                            + COLUMN_URL_IMAGE      + " TEXT, "
                                            + COLUMN_PUBLISHED      + " TEXT, "
                                            + COLUMN_CONTENT        + " TEXT);"
        );

        // добавление начальных данных
        //db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_ + ", " + COLUMN_  + ") VALUES (' ', 1981);");
    }
    //_________________________________________________________________________

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
    //_________________________________________________________________________
}
//_____________________________________________________________________________
