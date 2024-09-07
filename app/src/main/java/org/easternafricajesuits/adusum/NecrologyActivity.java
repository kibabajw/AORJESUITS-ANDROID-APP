package org.easternafricajesuits.adusum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.model.AdusumDocuments;
import org.easternafricajesuits.adusum.necrology.NecrologyAdapter;
import org.easternafricajesuits.adusum.necrology.NecrologyModel;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityNecrologyBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NecrologyActivity extends AppCompatActivity {
    private ActivityNecrologyBinding binding;
    private RecyclerView recyclerView;
    private View emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNecrologyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar_necrology = binding.toolbarNecrology;
        setSupportActionBar(toolbar_necrology);

        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).setDisplayHomeAsUpEnabled(true);

        emptyView = binding.necrologyEmptyView;

        recyclerView = binding.necrologyRecycler;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);

        registerForContextMenu(recyclerView);

        fetchnecrology();
    }

    private void fetchnecrology() {
        Call<NecrologyModel> call = RetrofitClient
                .getInstance()
                .getApi()
                .getNecrologies();
        call.enqueue(new Callback<NecrologyModel>() {
            @Override
            public void onResponse(Call<NecrologyModel> call, Response<NecrologyModel> response) {
                long statuscode = response.code();

                if (statuscode == 200) {
                    emptyView.setVisibility(View.INVISIBLE);

                    List<NecrologyModel> items = (List<NecrologyModel>) response.body().getItems();
                    recyclerView.setAdapter(new NecrologyAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                } else if (statuscode == 404) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<NecrologyModel> call, Throwable t) {
                emptyView.setVisibility(View.VISIBLE);
            }
        });
    }
}