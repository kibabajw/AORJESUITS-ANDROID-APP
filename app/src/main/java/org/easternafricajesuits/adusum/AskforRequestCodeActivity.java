package org.easternafricajesuits.adusum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.model.AskcodeModel;
import org.easternafricajesuits.adusum.model.AskcodeReceivedModel;
import org.easternafricajesuits.clients.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskforRequestCodeActivity extends AppCompatActivity {

    public static final String EMAIL_FOR_RESET = "EMAIL_FOR_RESET";
    public static final String CODE_FOR_RESET = "CODE_FOR_RESET";
    private EditText editresetcode;
    private Button btnconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_request_code);

        Bundle bundle = getIntent().getExtras();
        String emailforreset = bundle.getString(GetResetEmailActivity.THE_EMAIL_FOR_RESET);
        Toast.makeText(this, "EM: " + emailforreset, Toast.LENGTH_SHORT).show();

        editresetcode = findViewById(R.id.ask_for_reset_code_edt_reset_code);

        btnconfirm = findViewById(R.id.ask_for_reset_code_btn_enter);

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_reset_code = editresetcode.getText().toString().trim();
                validateResetCode(str_reset_code);
            }

            private void validateResetCode(String str_reset_code) {
                if (str_reset_code.isEmpty()) {
                    Toast.makeText(AskforRequestCodeActivity.this, "Enter reset code", Toast.LENGTH_SHORT).show();
                } else if (!str_reset_code.isEmpty() && str_reset_code.length() < 4) {
                    Toast.makeText(AskforRequestCodeActivity.this, "Reset code is incomplete", Toast.LENGTH_SHORT).show();
                } else {
                    crosscheckresetcodeandemail(emailforreset, str_reset_code);
                }
            }
        });
    }

    private void crosscheckresetcodeandemail(String emailforreset, String str_reset_code) {
        AskcodeModel askcodeModel = new AskcodeModel(str_reset_code, emailforreset);

        Call<AskcodeReceivedModel> call = RetrofitClient.getInstance().getApi().askforrequestCode(askcodeModel);

        call.enqueue(new Callback<AskcodeReceivedModel>() {
            @Override
            public void onResponse(Call<AskcodeReceivedModel> call, Response<AskcodeReceivedModel> response) {
                if (response.code() == 200) {
                    if (response.body().getResetStatusCode().equals("1")) {
                        // take user to the last activity to choose a new password
                        Intent intent = new Intent(AskforRequestCodeActivity.this, ChooseNewPasswordActivity.class);
                        intent.putExtra(EMAIL_FOR_RESET, emailforreset);
                        intent.putExtra(CODE_FOR_RESET, str_reset_code);
                        startActivity(intent);
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(AskforRequestCodeActivity.this, "Reset code is wrong.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AskforRequestCodeActivity.this, "Error, occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AskcodeReceivedModel> call, Throwable t) {
                Toast.makeText(AskforRequestCodeActivity.this, "An error occured, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}