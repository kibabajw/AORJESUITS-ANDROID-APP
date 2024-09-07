package org.easternafricajesuits;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import org.easternafricajesuits.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private ActivityPrivacyPolicyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar privacy_policy_toolbar = binding.privacyPolicyToolbar;
        setSupportActionBar(privacy_policy_toolbar);

        ActionBar actionBarAccount = getSupportActionBar();
        ((ActionBar) actionBarAccount).setDisplayHomeAsUpEnabled(true);
    }
}