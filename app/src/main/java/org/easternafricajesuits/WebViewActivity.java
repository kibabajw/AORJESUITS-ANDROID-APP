package org.easternafricajesuits;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import org.easternafricajesuits.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {
    private ActivityWebViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String fileLocation = null;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            fileLocation = extras.getString("CATALOGUE_NAME");
        }

        String CATALOGUE_URL = AllConstants.STORAGE_URL + fileLocation;

        String url = "http://docs.google.com/gview?embedded=true&url=" + CATALOGUE_URL;
        String doc="<iframe src='"+url+"' width='100%' height='100%' style='border: none;'></iframe>";

        binding.webviewForFiles.getSettings().setJavaScriptEnabled(true);
        binding.webviewForFiles.loadData(doc, "text/html", "UTF-8");
    }
}