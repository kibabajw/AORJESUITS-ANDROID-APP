package org.easternafricajesuits.adusum.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract.BrotherEntry;

public class AdusumAccountSQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "adusumaccount.db";
    private static final int DB_VERSION = 1;

    private static String CREATE_ACCOUNT = "CREATE TABLE " + BrotherEntry.ADUSUMACCOUNT_TABLE + "("
                    + BrotherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + BrotherEntry.COLUMN_BROTHER_USER_NAME + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_FULL_NAME + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_DATE_OF_BIRTH + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_DATE_OF_ENTRY + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_NAME_OF_PROVINCIAL + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_CURRENT_COMMUNITY + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_ACCOUNT_PICTURE + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_PASSWORD + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_AUTH_TOKEN + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_ONLINE_ID + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN + " TEXT NOT NULL, "
                    + BrotherEntry.COLUMN_BROTHER_ACCOUNT_STATUS + " INTEGER NOT NULL DEFAULT 0);";


    public AdusumAccountSQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
