package org.easternafricajesuits.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.models.ProductsModel;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private List<ProductsModel> productsModel;
    private Context context;
    private int rowLayout;

    public ProductsAdapter(List<ProductsModel> productsModel, Context context, int rowLayout) {
        this.productsModel = productsModel;
        this.context = context;
        this.rowLayout = rowLayout;
    }

    public List<ProductsModel> getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(List<ProductsModel> productsModel) {
        this.productsModel = productsModel;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.tvdescription.setText(productsModel.get(position).getProductdescription());
//        holder.tvcontact.setText(R.string.productscontact);

        Glide.with(context)
                .load(AllConstants.IMAGE_URL + productsModel.get(position).getProductimage())
                .placeholder(R.drawable.loadingdots)
                .into(holder.imgproduct);
    }

    @Override
    public int getItemCount() {
        return productsModel.size();
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        LinearLayout productslinearLayout;
        TextView tvdescription;
        ImageView imgproduct;
        TextView tvcontact;


        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            productslinearLayout = itemView.findViewById(R.id.products_linearlayout);
            tvdescription = itemView.findViewById(R.id.productdescription);
            imgproduct = itemView.findViewById(R.id.productsimage);
            tvcontact = itemView.findViewById(R.id.productscontact);
        }
    }

}
