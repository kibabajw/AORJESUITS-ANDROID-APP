package org.easternafricajesuits.summary;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.FullscreenImageActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.models.NewsModel;

import java.util.List;

public class NewsSummaryAdapter extends RecyclerView.Adapter<NewsSummaryAdapter.NewsSummaryViewHolder> {
    private List<NewsSummaryModel> newsModel;
    private int rowLayout;
    private Context context;
    private String imgURI;

    public NewsSummaryAdapter(List<NewsSummaryModel> newsModel, int rowLayout, Context context) {
        this.setNewsModel(newsModel);
        this.setRowLayout(rowLayout);
        this.setContext(context);
    }

    public List<NewsSummaryModel> getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(List<NewsSummaryModel> newsModel) {
        this.newsModel = newsModel;
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NewsSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);
        return new NewsSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsSummaryViewHolder holder, int position) {
        NewsSummaryModel itemposition = newsModel.get(position);

        Glide.with(context)
                .load(AllConstants.IMAGE_URL + itemposition.getNewsImage())
                .centerCrop()
                .placeholder(R.drawable.loadingdots)
                .into(holder.imageView);

        holder.tv_news_summary_heading.setText( Html.fromHtml(itemposition.getNewsTitle()) );
        holder.tv_news_summary_body.setText( Html.fromHtml(itemposition.getNewsBody()).toString() );

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgURI = AllConstants.IMAGE_URL + itemposition.getNewsImage();
                Intent intent = new Intent(context, FullscreenImageActivity.class);
                intent.putExtra(AllConstants.IMG_CLICKED_URI, imgURI);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModel.size();
    }


    public static class NewsSummaryViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;
        TextView tv_news_summary_heading, tv_news_summary_body;
        public NewsSummaryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_news_summary);
            tv_news_summary_heading = itemView.findViewById(R.id.tv_news_summary_heading);
            tv_news_summary_body = itemView.findViewById(R.id.tv_news_summary_body);
        }
    }
}
