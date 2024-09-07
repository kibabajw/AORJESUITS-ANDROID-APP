package org.easternafricajesuits.adusum;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.adusum.model.NewUserModel;
import org.easternafricajesuits.adusum.model.NewUserModelReceived;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityAdusumregisterBinding;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdusumregisterActivity extends AppCompatActivity {

    private ActivityAdusumregisterBinding binding;

    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutUsername;
    private TextInputLayout inputLayoutFullname;
    private TextInputLayout inputLayoutDateofbirth;
    private TextInputLayout inputLayoutDateofentry;
    private TextInputLayout inputLayoutNameOfProvincial;
    private TextInputLayout inputLayoutCurrentCommunity;
    private TextInputLayout inputLayoutPassword;

    private EditText register_email;
    private EditText register_username;
    private EditText register_fullname;
    private EditText register_date_of_birth;
    private EditText register_date_of_entry;
    private EditText register_name_ofProvincial;
    private EditText register_current_community;
    private EditText register_password;
    private Button buttonEnter;

    private String str_register_email = "";
    private String str_register_username = "";
    private String str_register_fullname = "";
    private String str_register_date_of_birth = "";
    private String str_register_date_of_entry = "";
    private String str_register_name_ofProvincial = "";
    private String str_register_current_community = "";
    private String str_register_password = "";

    private ProgressDialog progressDialogRegister;

    //SQLite database logic here
    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdusumregisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Instantiate variables
        Toolbar toolbar = binding.toolbarAccount;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        // set up button
        ActionBar actionBaradusumRegister = getSupportActionBar();
        ((ActionBar) actionBaradusumRegister).setDisplayHomeAsUpEnabled(true);
        // set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Logic for circular ProgressBar
        progressDialogRegister = new ProgressDialog(AdusumregisterActivity.this);

        //    adusum registration variables
        TextView linklogin = binding.adusumlinklogin;
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdusumregisterActivity.this, AdusumLoginActivity.class));
            }
        });

        TextView linkResetPass = binding.adusumResetPasswordlink;
        linkResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdusumregisterActivity.this, GetResetEmailActivity.class));
            }
        });
        // sqlite helper
        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(this);

        // Edittext
          inputLayoutEmail = binding.inputLayoutemail;
          inputLayoutUsername = binding.inputLayoutusername;
          inputLayoutFullname = binding.inputLayoutFullname;
          inputLayoutDateofbirth = binding.inputLayoutDateofbirth;
          inputLayoutDateofentry = binding.inputLayoutDateofentry;
          inputLayoutNameOfProvincial = binding.inputLayoutNameofyourprovincial;
          inputLayoutCurrentCommunity = binding.inputLayoutCurrentcommunity;
          inputLayoutPassword = binding.inputLayoutPassword;

          register_email = binding.editAdusumRegisterEmail;
          register_username = binding.editAdusumRegisterUsername;
          register_fullname = binding.editAdusumRegisterFullname;
          register_date_of_birth = binding.editAdusumRegisterDateOfBirth;
          register_date_of_entry = binding.editAdusumRegisterDateOfEntry;
          register_name_ofProvincial = binding.editAdusumRegisterNameOfProvincial;
          register_current_community = binding.editAdusumRegisterCurrentCommunity;
          register_password = binding.editAdusumRegisterPassword;

          binding.editAdusumRegisterDateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
              @Override
              public void onFocusChange(View v, boolean hasFocus) {
                  if (binding.editAdusumRegisterDateOfBirth.hasFocus()) {
                      showCalendar(binding.editAdusumRegisterDateOfBirth);
                  }
              }
          });

          binding.editAdusumRegisterDateOfEntry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
              @Override
              public void onFocusChange(View v, boolean hasFocus) {
                  if(binding.editAdusumRegisterDateOfEntry.hasFocus()) {
                    showCalendar(binding.editAdusumRegisterDateOfEntry);
                  }
              }
          });

          buttonEnter = binding.btnAdusumRegisterEnter;
          buttonEnter.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startRegistration();
              }
          });
    }

    private void showCalendar(TextView theTextview) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(AdusumregisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                theTextview.setText(String.format(dayOfMonth + "/" + month + "/" + year));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setTitle("Select a date");
        datePicker.show();
    }

    private void startRegistration() {
        str_register_email = register_email.getText().toString().trim();
        str_register_username = register_username.getText().toString().trim();
        str_register_fullname = register_fullname.getText().toString().trim();
        str_register_date_of_birth = register_date_of_birth.getText().toString().trim();
        str_register_date_of_entry = register_date_of_entry.getText().toString().trim();
        str_register_name_ofProvincial = register_name_ofProvincial.getText().toString().trim();
        str_register_current_community = register_current_community.getText().toString().trim();
        str_register_password = register_password.getText().toString().trim();

        if (str_register_email.isEmpty() && str_register_username.isEmpty() && str_register_fullname.isEmpty() && str_register_date_of_birth.isEmpty() && str_register_date_of_entry.isEmpty()
                && str_register_name_ofProvincial.isEmpty() && str_register_current_community.isEmpty() && str_register_password.isEmpty()) {
            Toast.makeText(AdusumregisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else  if (!str_register_email.isEmpty() && !str_register_username.isEmpty() && !str_register_fullname.isEmpty() && !str_register_date_of_birth.isEmpty()
                && !str_register_date_of_entry.isEmpty() && !str_register_name_ofProvincial.isEmpty() && !str_register_current_community.isEmpty() && !str_register_password.isEmpty()) {
            // All fields are okay, now check validity of the emailaddress
            // Create userdata object

            if (str_register_email.matches(emailPattern)) {
                inputLayoutEmail.setErrorEnabled(false);

                NewUserModel newUser = new NewUserModel(
                        str_register_email,
                        str_register_username,
                        str_register_fullname,
                        str_register_date_of_birth,
                        str_register_date_of_entry,
                        str_register_name_ofProvincial,
                        str_register_current_community,
                        str_register_password
                );

                progressDialogRegister.show();
                progressDialogRegister.setContentView(R.layout.progress_dialog);
                progressDialogRegister.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialogRegister.setCancelable(false);

                sendRequest(newUser);

            } else {
                inputLayoutEmail.setErrorEnabled(true);
                inputLayoutEmail.setError("enter a valid email");
                Toast.makeText(AdusumregisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
            }


        } else {
            if (str_register_email.isEmpty()) {
                inputLayoutEmail.setError("email is required");
            } else {
                inputLayoutEmail.setErrorEnabled(false);
            }

            if (str_register_email.matches(emailPattern)) {
                inputLayoutEmail.setErrorEnabled(false);
            } else {
                inputLayoutEmail.setErrorEnabled(true);
                inputLayoutEmail.setError("enter a valid email");
            }

            if (str_register_username.isEmpty()) {
                inputLayoutUsername.setError("username is required");
            } else {
                inputLayoutUsername.setErrorEnabled(false);
            }

            if (str_register_fullname.isEmpty()) {
                inputLayoutFullname.setError("Your name is required");
            } else {
                inputLayoutFullname.setErrorEnabled(false);
            }

            if (str_register_date_of_birth.isEmpty()) {
                inputLayoutDateofbirth.setError("Date of birth is required");
            } else {
                inputLayoutDateofbirth.setErrorEnabled(false);
            }

            if (str_register_date_of_entry.isEmpty()) {
                inputLayoutDateofentry.setError("Date of entry is required");
            } else {
                inputLayoutDateofentry.setErrorEnabled(false);
            }

            if (str_register_name_ofProvincial.isEmpty()) {
                inputLayoutNameOfProvincial.setError("Name of your provincial is required.");
            } else {
                inputLayoutNameOfProvincial.setErrorEnabled(false);
            }

            if (str_register_current_community.isEmpty()) {
                inputLayoutCurrentCommunity.setError("Current community is required.");
            } else {
                inputLayoutCurrentCommunity.setErrorEnabled(false);
            }

            if (str_register_password.isEmpty()) {
                inputLayoutPassword.setError("Set a new password.");
            } else {
                inputLayoutPassword.setErrorEnabled(false);
            }
        }


    }

    private void sendRequest(NewUserModel newuserData) {
        Call<NewUserModelReceived> call = RetrofitClient.getInstance().getApi().createAccount(newuserData);
        call.enqueue(new Callback<NewUserModelReceived>() {
            @Override
            public void onResponse(Call<NewUserModelReceived> call, Response<NewUserModelReceived> response) {
                if (response.code() == 201) {
                    NewUserModelReceived us = response.body();
                    String onlineId = "";

                    try {
                        JSONObject jsonObject = new JSONObject((Map) us.getBrotherObj());
                        if (jsonObject instanceof Object) {
                            onlineId = jsonObject.getString("insertId");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (insertNewBrotherToSQLite()) {
                        if(updateOnlineId(onlineId)) {
                            progressDialogRegister.dismiss();
                            Toast.makeText(AdusumregisterActivity.this, "Registration was successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AdusumregisterActivity.this, ProbationActivity.class));
                            finish();
                        } else {
                            progressDialogRegister.dismiss();
                            Toast.makeText(AdusumregisterActivity.this, "Could not complete registration", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialogRegister.dismiss();
                        Toast.makeText(AdusumregisterActivity.this, "Could not complete registration", Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 409) {
                    progressDialogRegister.dismiss();
                    Toast.makeText(AdusumregisterActivity.this, "email or username already taken", Toast.LENGTH_LONG).show();
                } else {
                    progressDialogRegister.dismiss();
                    Toast.makeText(AdusumregisterActivity.this, "Could not complete registration", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewUserModelReceived> call, Throwable t) {
                progressDialogRegister.dismiss();
                Toast.makeText(AdusumregisterActivity.this, "Could not register, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean updateOnlineId(String actualID) {
        ContentValues values = new ContentValues();
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID, actualID);

        int rowsAffected = getContentResolver().update(
                ContentUris.withAppendedId(AdusumDatabaseContract.BrotherEntry.CONTENT_URI, 1), values, null, null);

        if (rowsAffected == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean insertNewBrotherToSQLite() {
        ContentValues  values = new ContentValues();
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_USER_NAME, str_register_username);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_FULL_NAME, str_register_fullname);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_DATE_OF_BIRTH, str_register_date_of_birth);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_DATE_OF_ENTRY, str_register_date_of_entry);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_NAME_OF_PROVINCIAL, str_register_name_ofProvincial);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_CURRENT_COMMUNITY, str_register_current_community);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_PICTURE, "defaultavatar.jpg");
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_PASSWORD, str_register_password);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_AUTH_TOKEN, 0);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_STATUS, 0);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID, 0);
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN, 0);

        Uri newUri = getContentResolver().insert(AdusumDatabaseContract.BrotherEntry.CONTENT_URI, values);
        if (newUri == null) {
            return false;
        } else {
            return true;
        }
    }
}