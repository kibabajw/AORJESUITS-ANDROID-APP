package org.easternafricajesuits;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import org.easternafricajesuits.adusum.AdusumAdapter;
import org.easternafricajesuits.adusum.model.AdusumDocuments;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityOtherDocumentsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherDocumentsActivity extends AppCompatActivity {
    private ActivityOtherDocumentsBinding binding;
    private RecyclerView recyclerView;
    private View emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtherDocumentsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar_other_documents = binding.toolbarOtherDocuments;
        setSupportActionBar(toolbar_other_documents);

        ActionBar actionBarAccount = getSupportActionBar();
        ((ActionBar) actionBarAccount).setDisplayHomeAsUpEnabled(true);

        emptyView = binding.othersEmptyView;

        recyclerView = binding.otherdocumentsRecycler;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);

        registerForContextMenu(recyclerView);

        fetchDocuments();
    }

    private void fetchDocuments() {
        Call<AdusumDocuments> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDocuments("Others");
        call.enqueue(new Callback<AdusumDocuments>() {
            @Override
            public void onResponse(Call<AdusumDocuments> call, Response<AdusumDocuments> response) {
                long statuscode = response.code();

                if (statuscode == 200) {
                    emptyView.setVisibility(View.INVISIBLE);

                    List<AdusumDocuments> items = (List<AdusumDocuments>) response.body().getItems();
                    recyclerView.setAdapter(new AdusumAdapter(getApplicationContext(), items));
                    recyclerView.smoothScrollToPosition(0);
                } else if (statuscode == 404) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AdusumDocuments> call, Throwable t) {
                emptyView.setVisibility(View.VISIBLE);
            }
        });
    }

}