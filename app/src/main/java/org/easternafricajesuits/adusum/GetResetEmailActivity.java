package org.easternafricajesuits.adusum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.adusum.model.RequestPassResetModel;
import org.easternafricajesuits.adusum.model.RequestPasswordResetReceived;
import org.easternafricajesuits.clients.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetResetEmailActivity extends AppCompatActivity {

    public static final String THE_EMAIL_FOR_RESET = "THE_EMAIL_FOR_RESET";
    private EditText editTextEmail;
    private Button buttonEnter;
    private String userTouseEmail = "";

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reset_email);

        progressDialog = new ProgressDialog(GetResetEmailActivity.this);

        editTextEmail = findViewById(R.id.get_reset_email_edt_email);

        buttonEnter = findViewById(R.id.get_reset_email_btn_enter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEmail();
            }

            private void validateEmail() {
                userTouseEmail = editTextEmail.getText().toString().trim();
                if (userTouseEmail.isEmpty()) {
                    Toast.makeText(GetResetEmailActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                } else if(!userTouseEmail.matches(emailPattern)) {
                    Toast.makeText(GetResetEmailActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else {
//                    email is good, check online if email corresponds to an active account
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progressDialog.setCancelable(false);

                    checkemaillCorrespondence(userTouseEmail);
                }
            }
        });
    }

    private void checkemaillCorrespondence(String str_reset_email) {
        RequestPassResetModel requestPassResetModel = new RequestPassResetModel(str_reset_email);
        Call<RequestPasswordResetReceived> call = RetrofitClient.getInstance().getApi().requestpasswordReset(requestPassResetModel);

        call.enqueue(new Callback<RequestPasswordResetReceived>() {
            @Override
            public void onResponse(Call<RequestPasswordResetReceived> call, Response<RequestPasswordResetReceived> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode().equals("1")) {
                        // email is available and an mail with rest-code has been sent
                        progressDialog.dismiss();
                        Toast.makeText(GetResetEmailActivity.this, "A reset code has been sent to your email-address", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(GetResetEmailActivity.this, AskforRequestCodeActivity.class);
                        intent.putExtra(THE_EMAIL_FOR_RESET, userTouseEmail);
                        startActivity(intent);
                    }
                } else if(response.code() == 404) {
                    progressDialog.dismiss();
                    Toast.makeText(GetResetEmailActivity.this, "Email is unrecognized", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(GetResetEmailActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestPasswordResetReceived> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GetResetEmailActivity.this, "An error occured, try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


}