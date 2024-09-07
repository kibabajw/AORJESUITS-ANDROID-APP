package org.easternafricajesuits.merchandise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adapters.ProductsAdapter;
import org.easternafricajesuits.adusum.AdusumregisterActivity;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivityMerchandiseBinding;
import org.easternafricajesuits.models.OrderdonateModel;
import org.easternafricajesuits.models.OrderdonateModelReceived;
import org.easternafricajesuits.models.ProductsModel;
import org.easternafricajesuits.models.ProductsReceivedModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchandiseActivity extends AppCompatActivity {

    private ActivityMerchandiseBinding binding;

    private RecyclerView productsrecyclerView;
    private RecyclerView.Adapter productsAdapter;
    private List<ProductsModel> productsModel = new ArrayList<>();

    private String str_full_name = "";
    private String str_email = "";
    private String str_phone = "";
    private String str_description = "";

    private ProgressDialog progressDialogRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMerchandiseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Logic for circular ProgressBar
        progressDialogRegister = new ProgressDialog(MerchandiseActivity.this);

        // set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        productsrecyclerView = binding.recyclerviewMerchandise;
//        productsrecyclerView.addItemDecoration(new DividerItemDecoration(MerchandiseActivity.this, LinearLayout.HORIZONTAL));
        productsrecyclerView.setLayoutManager(new LinearLayoutManager(MerchandiseActivity.this, LinearLayoutManager.HORIZONTAL, false));
        productsAdapter = new ProductsAdapter(productsModel, this, R.layout.products_recycler_item);
        productsrecyclerView.setAdapter(productsAdapter);

        fetchproducts();

        binding.btnmerchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_full_name = binding.editmerchFullname.getText().toString().trim();
                str_email = binding.editmerchEmail.getText().toString().trim();
                str_phone = binding.editmerchPhone.getText().toString().trim();
                str_description = binding.editmerchDescription.getText().toString().trim();

                if (str_full_name.isEmpty() || str_email.isEmpty() || str_phone.isEmpty() || str_description.isEmpty()) {
                    Toast.makeText(MerchandiseActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    OrderdonateModel orderdonateModel = new OrderdonateModel(str_full_name, str_email, str_phone, str_description);

                    progressDialogRegister.show();
                    progressDialogRegister.setContentView(R.layout.progress_dialog);
                    progressDialogRegister.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progressDialogRegister.setCancelable(false);

                    makeorderdonate(orderdonateModel);
                }
            }
        });
    }

    private void fetchproducts() {
        Call<ProductsReceivedModel> call = RetrofitClient.getInstance().getApi().getProducts();

        call.enqueue(new Callback<ProductsReceivedModel>() {
            @Override
            public void onResponse(Call<ProductsReceivedModel> call, Response<ProductsReceivedModel> response) {
                if (response.code() == 200) {
                    productsModel.clear();
                    productsModel.addAll(response.body().getProductsModel());
                    productsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductsReceivedModel> call, Throwable t) {
                if (String.valueOf(t.toString().startsWith("java.net.SocketTimeoutException")).equals("true")) {
                    Toast.makeText(MerchandiseActivity.this, "You are offline", Toast.LENGTH_SHORT).show();
                } else if (String.valueOf(t.toString().startsWith("java.net.ProtocolException")).equals("true")) {
                    Toast.makeText(MerchandiseActivity.this, "Request timedout", Toast.LENGTH_SHORT).show();
                } else if(String.valueOf(t.toString().startsWith("java.net.ConnectException")).equals("true")) {
                    Toast.makeText(MerchandiseActivity.this, "No connection", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MerchandiseActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makeorderdonate(OrderdonateModel orderdonateModel) {
        Call<OrderdonateModelReceived> call = RetrofitClient.getInstance().getApi().neworderordonation(orderdonateModel);
        call.enqueue(new Callback<OrderdonateModelReceived>() {
            @Override
            public void onResponse(Call<OrderdonateModelReceived> call, Response<OrderdonateModelReceived> response) {
                if (response.code() == 201) {
                    progressDialogRegister.dismiss();
                    Toast.makeText(MerchandiseActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    progressDialogRegister.dismiss();
                    Toast.makeText(MerchandiseActivity.this, "Could not complete, please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderdonateModelReceived> call, Throwable t) {
                Toast.makeText(MerchandiseActivity.this, "An error occured, try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}