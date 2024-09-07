package org.easternafricajesuits.news;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NewsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "news.db";

    String SQL_CREATE_TABLE = "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + "("
            + NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NewsContract.NewsEntry.COLUMN_TITLE + " TEXT NOT NULL, "
            + NewsContract.NewsEntry.COLUMN_CREATED_AT + " TEXT NOT NULL, "
            + NewsContract.NewsEntry.COLUMN_IMAGE + " TEXT NOT NULL, "
            + NewsContract.NewsEntry.COLUMN_BODY + " TEXT NOT NULL);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME;
    public NewsDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
