package org.easternafricajesuits.adusum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.SplashScreenActivity;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.adusum.model.DeleteAccountPayload;
import org.easternafricajesuits.adusum.model.DeleteAccountResponse;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityDeleteAccountBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAccountActivity extends AppCompatActivity {

    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;
    private ActivityDeleteAccountBinding binding;
    private AlertDialog.Builder alertDelete;
    private String userOnlineID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar tbAccount = binding.toolbarDeleteAccount;
        setSupportActionBar(tbAccount);

        ActionBar actionBarAccount = getSupportActionBar();
        actionBarAccount.setDisplayHomeAsUpEnabled(true);

        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(this);

        userOnlineID = fetchmyidLocally();

        alertDelete = new AlertDialog.Builder(this);

        binding.buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showallertdialog();
            }
        });
    }

    private void showallertdialog() {
        alertDelete.setTitle("Account Deletion")
                .setMessage("You are about to delete your account, and accompanying data. Are you sure about this?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteaccount();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void deleteaccount() {
        Toast.makeText(this, "Deleting account......", Toast.LENGTH_LONG).show();

        Call<DeleteAccountResponse> call = RetrofitClient.getInstance().getApi().deletemyaccount(new DeleteAccountPayload(userOnlineID));
        call.enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response) {
                if (response.body().getStatus().equals("200")) {
                    Toast.makeText(DeleteAccountActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (logout(userOnlineID)) {
                                startActivity(new Intent(DeleteAccountActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }, 2000);
                } else {
                    Toast.makeText(DeleteAccountActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {
                Toast.makeText(DeleteAccountActivity.this, "Sorry, could not delete account, try again later.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String fetchmyidLocally() {
        String userId = "";
        SQLiteDatabase db = mAdusumAccountSQLHelper.getReadableDatabase();

        String[] projection = {
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
            int myIdColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID);

            String id = "";

            while (cursor.moveToNext()) {
                id = cursor.getString(myIdColumnIndex);
            }
            userId = id;
        } finally {
            cursor.close();
        }

        return userId;
    }

    private boolean logout(String logoutId) {
        long idtouse = (long) Math.floor(Double.parseDouble(logoutId));
        ContentValues values = new ContentValues();
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN, "0");

        int rowsAffected = getContentResolver().update(
                ContentUris.withAppendedId(AdusumDatabaseContract.BrotherEntry.CONTENT_URI, idtouse), values, null, null);

        if (rowsAffected == 0) {
            return false;
        } else {
            return true;
        }
    }

}