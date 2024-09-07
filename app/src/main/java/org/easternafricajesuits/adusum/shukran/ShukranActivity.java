package org.easternafricajesuits.adusum.shukran;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import org.easternafricajesuits.R;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityShukranBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class ShukranActivity extends AppCompatActivity {

    private ActivityShukranBinding binding;
    private View emptyViewShukran;
    private RecyclerView recyclerViewShukran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShukranBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbarShukran = binding.toolbarShukran;
        setSupportActionBar(toolbarShukran);

        ActionBar actionBar = getSupportActionBar();
        ((ActionBar) actionBar).setDisplayHomeAsUpEnabled(true);

        emptyViewShukran = binding.emptyViewShukrans;

        recyclerViewShukran = binding.shukransRecycler;
        recyclerViewShukran.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewShukran.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewShukran.smoothScrollToPosition(0);

        registerForContextMenu(recyclerViewShukran);

        fetchshukrans();
    }

    private void fetchshukrans() {
        Call<ShukranModel> call = RetrofitClient.getInstance().getApi().adusumGetShukrans();

        call.enqueue(new Callback<ShukranModel>() {
            @Override
            public void onResponse(Call<ShukranModel> call, Response<ShukranModel> response) {
                emptyViewShukran.setVisibility(View.GONE);
                if (response.code() == 200) {
                    List<ShukranModel> listItems = (List<ShukranModel>) response.body().getItems();

                    recyclerViewShukran.setAdapter(new ShukransAdapter(getApplicationContext(), listItems));
                    recyclerViewShukran.smoothScrollToPosition(0);
                } else {
                    SweetToast.info(getApplicationContext(), "No items to display", 3000);
                }
            }

            @Override
            public void onFailure(Call<ShukranModel> call, Throwable t) {
                SweetToast.error(getApplicationContext(), "Could not fetch items", 3000);
            }
        });
    }
}