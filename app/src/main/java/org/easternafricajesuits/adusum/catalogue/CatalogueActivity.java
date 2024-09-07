package org.easternafricajesuits.adusum.catalogue;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.AdusumAdapter;
import org.easternafricajesuits.adusum.model.CatalogueModel;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityCatalogueBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class CatalogueActivity extends AppCompatActivity {
    private ActivityCatalogueBinding binding;
    private RecyclerView recyclerViewCatalogue;
    private View emptyViewCatalogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogueBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbarCatalogue = binding.toolbarCatalogues;
        setSupportActionBar(toolbarCatalogue);

        ActionBar actionBarCatalogue = getSupportActionBar();
        ((ActionBar) actionBarCatalogue).setDisplayHomeAsUpEnabled(true);


        emptyViewCatalogue = binding.emptyViewCatalogues;

        recyclerViewCatalogue = binding.catalogueRecycler;
        recyclerViewCatalogue.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewCatalogue.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewCatalogue.smoothScrollToPosition(0);

        registerForContextMenu(recyclerViewCatalogue);

        fetchCatalogue();
    }

    private void fetchCatalogue() {
        Call<CatalogueModel> catalogueModelCall = RetrofitClient.getInstance().getApi().adusumGetCatalogue();
        catalogueModelCall.enqueue(new Callback<CatalogueModel>() {
            @Override
            public void onResponse(Call<CatalogueModel> call, Response<CatalogueModel> response) {
                emptyViewCatalogue.setVisibility(View.GONE);
                if (response.code() == 200) {
                    List<CatalogueModel> listItems = (List<CatalogueModel>) response.body().getItems();
                    recyclerViewCatalogue.setAdapter(new CataloguesAdapter(getApplicationContext(), listItems));
                    recyclerViewCatalogue.smoothScrollToPosition(0);
                } else {
                    SweetToast.info(getApplicationContext(), "No items to display", 3000);
                }
            }

            @Override
            public void onFailure(Call<CatalogueModel> call, Throwable t) {
                SweetToast.error(getApplicationContext(), "Could not fetch items", 3000);
            }
        });
    }

}