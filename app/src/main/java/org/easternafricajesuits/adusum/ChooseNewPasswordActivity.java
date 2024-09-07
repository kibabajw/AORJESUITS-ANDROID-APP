package org.easternafricajesuits.adusum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.model.NewPasswordModel;
import org.easternafricajesuits.adusum.model.NewPasswordReceivedModel;
import org.easternafricajesuits.clients.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseNewPasswordActivity extends AppCompatActivity {

    private EditText editnewpassword;
    private Button btnfinish;
    private ProgressDialog newpasswordProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_new_password);

        newpasswordProgress = new ProgressDialog(ChooseNewPasswordActivity.this);

        Bundle bundle = getIntent().getExtras();
        String useremail = bundle.getString(AskforRequestCodeActivity.EMAIL_FOR_RESET);
        String resetcode = bundle.getString(AskforRequestCodeActivity.CODE_FOR_RESET);

        editnewpassword = findViewById(R.id.choose_new_password_edt_new_password);
        btnfinish = findViewById(R.id.choose_new_password_btn_finish);

        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editnewpassword.getText().toString().trim();
                if (newPassword.isEmpty()) {
                    Toast.makeText(ChooseNewPasswordActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.isEmpty() && newPassword.length() < 4){
                    Toast.makeText(ChooseNewPasswordActivity.this, "Password is very short.", Toast.LENGTH_SHORT).show();
                } else {
                    newpasswordProgress.show();
                    newpasswordProgress.setContentView(R.layout.progress_dialog);
                    newpasswordProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    newpasswordProgress.setCancelable(false);

                    updateNewpassword(useremail, resetcode, newPassword);
                }
            }
        });
    }

    private void updateNewpassword(String newuseremail, String newresetcode, String newnewPassword) {
        NewPasswordModel newPasswordModel = new NewPasswordModel(newuseremail, newresetcode, newnewPassword);
        Call<NewPasswordReceivedModel> call = RetrofitClient.getInstance().getApi().newPassword(newPasswordModel);

        call.enqueue(new Callback<NewPasswordReceivedModel>() {
            @Override
            public void onResponse(Call<NewPasswordReceivedModel> call, Response<NewPasswordReceivedModel> response) {
                if (response.code() == 200) {
                    newpasswordProgress.dismiss();
                    Toast.makeText(ChooseNewPasswordActivity.this, "You can now login.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ChooseNewPasswordActivity.this, AdusumLoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<NewPasswordReceivedModel> call, Throwable t) {
                newpasswordProgress.dismiss();
                Toast.makeText(ChooseNewPasswordActivity.this, "An error occured, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}