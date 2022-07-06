package org.easternafricajesuits.adusum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import org.easternafricajesuits.R;
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

    String myId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdusumSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbarAccountSettings;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.adusum_settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_menu_item_enter) {
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
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}