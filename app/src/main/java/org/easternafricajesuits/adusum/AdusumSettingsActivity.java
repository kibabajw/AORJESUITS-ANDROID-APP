package org.easternafricajesuits.adusum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.model.AdusumModel;
import org.easternafricajesuits.adusum.model.UpdatemeModel;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityAdusumSettingsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdusumSettingsActivity extends AppCompatActivity {

    private ActivityAdusumSettingsBinding binding;
    private ProgressDialog progressDialog;

    TextInputLayout inputLayoutUsername;
    TextInputLayout inputLayoutPassword;
    EditText editTextusername;
    EditText editTextpassword;

    private TextView tv_username, tv_fullname, tv_email;

    String myId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdusumSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbarAccountSettings;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        ActionBar actionBarAccount = getSupportActionBar();
        ((ActionBar) actionBarAccount).setDisplayHomeAsUpEnabled(true);

        inputLayoutUsername = binding.settingsusernameTextInputLayout;
        inputLayoutPassword = binding.settingspasswordTextInputLayout;

        editTextusername = binding.settingsEditusername;
        editTextpassword = binding.settingsEditpassword;

        // Get user's id from intent extra
        Bundle bundle = getIntent().getExtras();
        myId = bundle.getString(AdusumAccountActivity.MY_USER_ID);

        progressDialog = new ProgressDialog(AdusumSettingsActivity.this);

        binding.buttonSettingsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evtsubmit();
            }
        });

        tv_username = binding.tvInfoUsername;
        tv_fullname = binding.tvInfoFullname;
        tv_email = binding.tvInfoEmail;

        fetchMyOnlineInfo(myId);
    }

    private void evtsubmit() {
        if (editTextusername.getText().toString().isEmpty() && editTextpassword.getText().toString().isEmpty()) {
            Toast.makeText(AdusumSettingsActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
        } else if (editTextusername.getText().toString().isEmpty() && !editTextpassword.getText().toString().isEmpty()) {
            inputLayoutUsername.setError("enter a username, current or new.");
        } else if (!editTextusername.getText().toString().isEmpty() && editTextpassword.getText().toString().isEmpty()) {
            inputLayoutPassword.setError("enter a password, current or new.");
        } else {
            inputLayoutUsername.setErrorEnabled(false);
            inputLayoutPassword.setErrorEnabled(false);

            UpdatemeModel updatemeModel = new UpdatemeModel(
                    myId, editTextusername.getText().toString(), editTextpassword.getText().toString()
            );
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);

            updateData(updatemeModel);
        }
    }

    private void updateData(UpdatemeModel updatemeModel) {
        Call<UpdatemeModel> updatemeModelCall = RetrofitClient.getInstance().getApi().updateMe(updatemeModel);
        updatemeModelCall.enqueue(new Callback<UpdatemeModel>() {
            @Override
            public void onResponse(Call<UpdatemeModel> call, Response<UpdatemeModel> response) {
                if (response.code() == 200) {
                    progressDialog.dismiss();

                    UpdatemeModel umodel = response.body();
                    Toast.makeText(AdusumSettingsActivity.this, umodel.getUpdresponse(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdusumSettingsActivity.this, AdusumAccountActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UpdatemeModel> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(AdusumSettingsActivity.this, "Could not update your data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchMyOnlineInfo(String userOnlineID) {
        Call<AdusumModel> adusumModelCall = RetrofitClient.getInstance().getApi().adusumGetMyInfo(userOnlineID);
        adusumModelCall.enqueue(new Callback<AdusumModel>() {
            @Override
            public void onResponse(Call<AdusumModel> call, Response<AdusumModel> response) {
                if (response.code() == 200) {
                    tv_username.setText(response.body().getUsername());
                    tv_fullname.setText(response.body().getFullname());
                    tv_email.setText(response.body().getEmail_address());
                }
            }

            @Override
            public void onFailure(Call<AdusumModel> call, Throwable t) {
                if (String.valueOf(t.toString().startsWith("java.net.SocketTimeoutException")).equals("true")) {
                    Toast.makeText(AdusumSettingsActivity.this, "You are offline", Toast.LENGTH_SHORT).show();
                } else if (String.valueOf(t.toString().startsWith("java.net.ProtocolException")).equals("true")) {
                    Toast.makeText(AdusumSettingsActivity.this, "Request timedout", Toast.LENGTH_SHORT).show();
                } else if(String.valueOf(t.toString().startsWith("java.net.ConnectException")).equals("true")) {
                    Toast.makeText(AdusumSettingsActivity.this, "No connection", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AdusumSettingsActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}