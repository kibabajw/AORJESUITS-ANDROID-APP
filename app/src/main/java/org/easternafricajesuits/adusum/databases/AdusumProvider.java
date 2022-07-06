package org.easternafricajesuits.adusum.databases;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdusumProvider extends ContentProvider {

    private static final String LOG_TAG = AdusumProvider.class.getSimpleName();

    private static final int BROTHER = 100;
    private static final int BROTHER_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AdusumDatabaseContract.BrotherEntry.CONTENT_AUTHORITY, AdusumDatabaseContract.PATH_BROTHER, BROTHER);
        sUriMatcher.addURI(AdusumDatabaseContract.BrotherEntry.CONTENT_AUTHORITY, AdusumDatabaseContract.PATH_BROTHER + "/#", BROTHER_ID);
    }

    // Database helper object
    private AdusumAccountSQLHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new AdusumAccountSQLHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case BROTHER:
                cursor = database.query(AdusumDatabaseContract.BrotherEntry.ADUSUMACCOUNT_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BROTHER_ID:
                selection = AdusumDatabaseContract.BrotherEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(AdusumDatabaseContract.BrotherEntry.ADUSUMACCOUNT_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BROTHER:
                return insertBrother(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+ uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_USER_NAME)) {
            String username = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_USER_NAME);
            if (username == null) {
                throw new IllegalArgumentException("Username is required");
            }
        }

        if (values.containsKey(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID)) {
            String onlineID = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID);
            if (onlineID == null) {
                throw new IllegalArgumentException("Online id is required");
            }
        }

        if (values.containsKey(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_PASSWORD)) {
            String password = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_PASSWORD);
            if (password == null) {
                throw new IllegalArgumentException("Passwprd is required");
            }
        }

        if (values.containsKey(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN)) {
            String cookieTOKEN = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN);
            if (cookieTOKEN == null) {
                throw new IllegalArgumentException("Cookie-token is required");
            }
        }

        if (values.containsKey(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_AUTH_TOKEN)) {
            String authToken = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_AUTH_TOKEN);
            if (authToken == null) {
                throw new IllegalArgumentException("Auth token is required");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsAffected = database.update(AdusumDatabaseContract.BrotherEntry.ADUSUMACCOUNT_TABLE, values, selection, selectionArgs);

        if (rowsAffected != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsAffected;
    }

    private Uri insertBrother(Uri uri, ContentValues values) {
        // validate username
        String username = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_USER_NAME);
        if (username == null) {
            throw new IllegalArgumentException("Username must be provided");
        }
        // validate fullname
        String fullname = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_FULL_NAME);
        if (fullname == null) {
            throw new IllegalArgumentException("Fullname must be provided");
        }
        // validate date of birth
        String dateofbirth = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_DATE_OF_BIRTH);
        if (dateofbirth == null) {
            throw new IllegalArgumentException("Date of birth must be provided");
        }
        // validate date of entry
        String dateofentry = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_DATE_OF_ENTRY);
        if (dateofentry == null) {
            throw new IllegalArgumentException("Date of entry must be provided");
        }
        // validate name of provincial
        String nameofprovincial = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_NAME_OF_PROVINCIAL);
        if (nameofprovincial == null) {
            throw new IllegalArgumentException("Name of provincial must be provided");
        }
        // validate current community
        String currentcommunity = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_CURRENT_COMMUNITY);
        if (currentcommunity == null) {
            throw new IllegalArgumentException("Current community must be provided");
        }

        String cookieToken = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN);
        if (cookieToken == null) {
            throw new IllegalArgumentException("Cookie token is required");
        }

        // validate password
        String password = values.getAsString(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_PASSWORD);
        if (password == null) {
            throw new IllegalArgumentException("A password must be provided");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // insert a new brother with the given data
        long id = database.insert(AdusumDatabaseContract.BrotherEntry.ADUSUMACCOUNT_TABLE, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }
}
