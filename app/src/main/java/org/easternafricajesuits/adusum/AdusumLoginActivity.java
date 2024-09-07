package org.easternafricajesuits.adusum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.model.UserLoginModel;
import org.easternafricajesuits.adusum.model.UserLoginReceived;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.databinding.ActivityAdusumLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class AdusumLoginActivity extends AppCompatActivity {

    private TextInputLayout adusumLoginEditUsernameTextInputLayout;
    private TextInputLayout adusumLoginEditPasswordTextInputLayout;
    private EditText adusumLoginEditUsername;
    private EditText adusumLoginEditPassword;
    private Button adusumLoginButton;
    private ProgressDialog loginProgress;

    // SQLite database
    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;
    private ActivityAdusumLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdusumLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbarAdusumLogin;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).setDisplayHomeAsUpEnabled(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView textViewlinkcreateaccount = findViewById(R.id.adusumlinkcreateaccount);
        textViewlinkcreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdusumLoginActivity.this, AdusumregisterActivity.class));
            }
        });

        TextView textViewresetPassword = findViewById(R.id.adusumlinkresetpassword);
        textViewresetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdusumLoginActivity.this, GetResetEmailActivity.class));
            }
        });

        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(this);

        loginProgress = new ProgressDialog(AdusumLoginActivity.this);

        adusumLoginEditUsernameTextInputLayout = findViewById(R.id.adusumLoginEditUsernameTextInputLayout);
        adusumLoginEditPasswordTextInputLayout = findViewById(R.id.adusumLoginEditPasswordTextInputLayout);

        adusumLoginEditUsername = findViewById(R.id.adusumLoginEditUsername);
        adusumLoginEditPassword = findViewById(R.id.adusumLoginEditPassword);

        adusumLoginButton = findViewById(R.id.adusumLoginButton);
        adusumLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateLoginDetails()) {
                    loginProgress.show();
                    loginProgress.setContentView(R.layout.progress_dialog);
                    loginProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    loginProgress.setCancelable(false);

                    String login_username;
                    String login_password;

                    UserLoginModel userModel = new UserLoginModel(
                            login_username = adusumLoginEditUsername.getText().toString().trim(),
                            login_password = adusumLoginEditPassword.getText().toString().trim()
                    );

                    loginBrother(userModel);
                }
            }
        });

    }

    private void loginBrother(UserLoginModel userLoginModel) {
        Call<UserLoginReceived> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginBrother(userLoginModel);
        call.enqueue(new Callback<UserLoginReceived>() {
            @Override
            public void onResponse(Call<UserLoginReceived> call, Response<UserLoginReceived> response) {
                if (response.code() == 200) {
                    UserLoginReceived userLogin = response.body();

                    // Check if user is a new user or an old user
                    if (userisNew()) {
                        // Save date received from online database to sqlite
                        if (insertNewBrotherToSQLite(userLogin)) {
                            loginProgress.dismiss();
                            startActivity(new Intent(AdusumLoginActivity.this, AdusumAccountActivity.class));
                            finish();
                        }

                    } else {
                        if(updateBrotherwithToken(userLogin.getLoginbrotherID(), userLogin.getBrotherTOKEN())) {
                            loginProgress.dismiss();
                            startActivity(new Intent(AdusumLoginActivity.this, AdusumAccountActivity.class));
                            finish();
                        }
                    }


                } else if(response.code() == 401) {
                    loginProgress.dismiss();

                    SweetToast.warning(AdusumLoginActivity.this, "Incorrect password", 3000);
                } else if (response.code() == 403) {
                    loginProgress.dismiss();

                    SweetToast.info(AdusumLoginActivity.this, "You account has not been confirmed", 3000);
                } else if (response.code() == 404) {
                    loginProgress.dismiss();

                    SweetToast.warning(AdusumLoginActivity.this, "Unrecognized credentials", 3000);
                } else {
                    loginProgress.dismiss();

                    SweetToast.error(AdusumLoginActivity.this, "Could not login", 3000);
                }
            }

            private boolean userisNew() {
                SQLiteDatabase db = mAdusumAccountSQLHelper.getReadableDatabase();

                String[] projection = {
                        AdusumDatabaseContract.BrotherEntry._ID,
                        AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_STATUS,
                        AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN
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
                    if (cursor.getCount() == 0) {
                        return true;
                    } else {
                        return false;
                    }
                } finally {
                    cursor.close();
                }
            }

            private boolean insertNewBrotherToSQLite(UserLoginReceived userLogin) {
                ContentValues  values = new ContentValues();
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_USER_NAME, userLogin.getUsername());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_FULL_NAME, userLogin.getFullname());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_DATE_OF_BIRTH, userLogin.getDateofbirth());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_DATE_OF_ENTRY, userLogin.getDateofentry());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_NAME_OF_PROVINCIAL, userLogin.getNameofprovincial());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_CURRENT_COMMUNITY, userLogin.getCurrentcommunity());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_PICTURE, "defaultavatar.jpg");
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_PASSWORD, "");
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_AUTH_TOKEN, 0);
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_STATUS, userLogin.getStatus());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID, userLogin.getLoginbrotherID());
                values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN, userLogin.getBrotherTOKEN());

                Uri newUri = getContentResolver().insert(AdusumDatabaseContract.BrotherEntry.CONTENT_URI, values);
                if (newUri == null) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public void onFailure(Call<UserLoginReceived> call, Throwable t) {
                loginProgress.dismiss();
                
                SweetToast.error(AdusumLoginActivity.this, "Error,  try again", 3000);
            }
        });
    }

    private boolean updateBrotherwithToken(String catalogueid, String tokenstring) {
        ContentValues values = new ContentValues();
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN, tokenstring);

        int rowsAffected = getContentResolver().update(
                ContentUris.withAppendedId(AdusumDatabaseContract.BrotherEntry.CONTENT_URI, Long.parseLong(catalogueid)), values, null, null);

        if (rowsAffected == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateLoginDetails() {
        if (adusumLoginEditUsername.getText().toString().isEmpty()) {
            adusumLoginEditUsernameTextInputLayout.setErrorEnabled(true);
            adusumLoginEditUsernameTextInputLayout.setError("Enter your username");
        } else {
            adusumLoginEditUsernameTextInputLayout.setErrorEnabled(false);
        }

        if (adusumLoginEditPassword.getText().toString().isEmpty()) {
            adusumLoginEditPasswordTextInputLayout.setErrorEnabled(true);
            adusumLoginEditPasswordTextInputLayout.setError("Enter your password");
        } else {
            adusumLoginEditPasswordTextInputLayout.setErrorEnabled(false);
        }

        if (!adusumLoginEditUsername.getText().toString().isEmpty() && !adusumLoginEditPassword.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }
}