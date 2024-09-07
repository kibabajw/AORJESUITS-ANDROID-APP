package org.easternafricajesuits.adusum;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.model.AdusumServiceModel;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.adusum.services.AdusumService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProbationActivity extends AppCompatActivity {

    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;
    private String status = "";
    private String localID = "";
    private String onlineID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probation);

        Button buttonBackHome = (Button) findViewById(R.id.probationbuttonhome);
        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProbationActivity.this, MainActivity.class));
            }
        });

        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(this);
        SQLiteDatabase db = mAdusumAccountSQLHelper.getReadableDatabase();

        String[] projection = {
                AdusumDatabaseContract.BrotherEntry._ID,
                AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID,
                AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID
        };

        Cursor cursor = db.query(
                AdusumDatabaseContract.BrotherEntry.ADUSUMACCOUNT_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
                int localidColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry._ID);
                int statusColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID);
                int onlineidColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID);

                while (cursor.moveToNext()) {
                    status = cursor.getString(statusColumnIndex);
                    localID = cursor.getString(localidColumnIndex);
                    onlineID = cursor.getString(onlineidColumnIndex);
                }
        } finally {
            cursor.close();
        }
//        fetchOnline(status);
        checkbrotherOnlineStatus(localID, onlineID);
    }

    private void checkbrotherOnlineStatus(String theLocalID, String theOnlineID) {
        Intent intent = new Intent(this, AdusumService.class);
        intent.putExtra(AdusumDatabaseContract.BrotherEntry._ID, theLocalID);
        intent.putExtra(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID, theOnlineID);
        startService(intent);
    }

}