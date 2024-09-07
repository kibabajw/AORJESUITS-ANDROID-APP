package org.easternafricajesuits.monthlythought;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.easternafricajesuits.news.NewsContract;

public class MonthlyThoughtDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "monthlythought.db";

    String SQL_CREATE_TABLE = "CREATE TABLE " + MonthlyThoughtContract.ThoughtEntry.TABLE_NAME + "("
            + MonthlyThoughtContract.ThoughtEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_HEADING + " TEXT NOT NULL, "
            + MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_TITLE + " TEXT NOT NULL, "
            + MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_SUB_TITLE + " TEXT NOT NULL, "
            + MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_IMAGE + " TEXT NOT NULL, "
            + MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_UPDATED_AT + " TEXT NOT NULL);";

    public MonthlyThoughtDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
