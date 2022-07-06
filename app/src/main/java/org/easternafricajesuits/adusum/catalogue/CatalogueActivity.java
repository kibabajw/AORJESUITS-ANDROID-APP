package org.easternafricajesuits.adusum.catalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.ActivityCatalogueBinding;

public class CatalogueActivity extends AppCompatActivity {
    private ActivityCatalogueBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogueBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String url = "http://docs.google.com/gview?embedded=true&url=" + AllConstants.cataloguedocumentUrl;
        String doc="<iframe src='"+url+"' width='100%' height='100%' style='border: none;'></iframe>";

        binding.adusumwebviewcatalogue.getSettings().setJavaScriptEnabled(true);
        binding.adusumwebviewcatalogue.loadData(doc, "text/html", "UTF-8");
    }
}