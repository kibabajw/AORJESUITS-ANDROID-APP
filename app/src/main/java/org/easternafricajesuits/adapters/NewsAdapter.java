package org.easternafricajesuits.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.FullscreenImageActivity;
import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.models.LikeModel;
import org.easternafricajesuits.models.NewsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     private String imgUri;

     private List<NewsModel> newsModel;
     private Context context;
     private int rowLayout;


     public List<NewsModel> getNewsModel() {
     return newsModel;
     }

     public void setNewsModel(List<NewsModel> newsModel) {
     this.newsModel = newsModel;
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


     public NewsAdapter(List<NewsModel> newsModel, int rowLayout, Context context) {
     this.newsModel = newsModel;
     this.context = context;
     this.rowLayout = rowLayout;
     }

     @NonNull
     @Override
     public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
     return new NewsViewHolder(view);

     }

     @Override
     public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
     holder.tvHomefragnewstitle.setText(Html.fromHtml(newsModel.get(position).getNewsTitle()));
     holder.tvHomefragdate.setText(newsModel.get(position).getNewsPublished());
     holder.tvHomefragnewscontent.setText(Html.fromHtml(newsModel.get(position).getNewsBody()));

     String newsViews = newsModel.get(position).getNewsViews();
     if (newsViews == null) {
     newsViews = String.valueOf(0);
     }
     holder.tvHomefragviews.setText(newsViews);

     String strImage = newsModel.get(position).getNewsImage();

     imgUri = AllConstants.BASE_URL + "storage/" + strImage;

     Picasso.get()
     .load(AllConstants.BASE_URL + "storage/" + strImage)
     .into(holder.imgHomefragimage);

     holder.imgHomefragimage.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
     Intent imgIntent = new Intent(getContext(), FullscreenImageActivity.class);
     imgIntent.putExtra(AllConstants.IMG_CLICKED_URI, imgUri);
     getContext().startActivity(imgIntent);
     }
     });

     }

     @Override
     public int getItemCount() {
     return newsModel.size();
     }

     public static class NewsViewHolder extends RecyclerView.ViewHolder {

     LinearLayout newsfraglinearlayout;
     TextView tvHomefragnewstitle;
     ImageView imgHomefragimage;
     TextView tvHomefragdate;
     TextView tvHomefragviews;
     ReadMoreTextView tvHomefragnewscontent;
     ImageView home_news_btn_like;

     public NewsViewHolder(@NonNull View itemView) {
     super(itemView);

     newsfraglinearlayout = itemView.findViewById(R.id.news_frag_linearlayout);
     tvHomefragnewstitle = itemView.findViewById(R.id.home_frag_news_title);
     imgHomefragimage = itemView.findViewById(R.id.home_frag_image);
     tvHomefragdate = itemView.findViewById(R.id.home_frag_date);
     tvHomefragviews = itemView.findViewById(R.id.home_frag_likes);
     tvHomefragnewscontent = itemView.findViewById(R.id.home_frag_news_content);
     home_news_btn_like = itemView.findViewById(R.id.home_news_btn_like);
     }
     }
     **/


    private String imgUri;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = AllConstants.BASE_URL + "storage/";

    private List<NewsModel> newsModel;
    private Context context;

    private boolean isLoadingAdded = false;

    public NewsAdapter(Context context) {
        this.context = context;
        newsModel = new ArrayList<>();
    }

    public List<NewsModel> getMovies() {
        return newsModel;
    }

    public void setMovies(List<NewsModel> movieResults) {
        this.newsModel = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.news_frag_recycler_item, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AllConstants.MYID, context.MODE_PRIVATE);
        String phoneID = sharedPreferences.getString(AllConstants.MYID, null);

        if (phoneID == null) {
            phoneID = UUID.randomUUID().toString();
            sharedPreferences.edit().putString(AllConstants.MYID, phoneID).apply();
        }

        String finalPhoneID = phoneID;

        NewsModel result = newsModel.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                movieVH.tvHomefragnewstitle.setText(Html.fromHtml(result.getNewsTitle()));
                movieVH.tvHomefragnewspublished.setText(result.getNewsCreatedAt());
                movieVH.tvHomefragnewscontent.setText(Html.fromHtml(result.getNewsBody()));

                Glide.with(context)
                        .load(AllConstants.IMAGE_URL +result.getNewsImage())
                        .centerCrop()
                        .placeholder(R.drawable.loadingdots)
                        .into(movieVH.imgHomefragimage);




                movieVH.imgHomefragimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgUri = AllConstants.IMAGE_URL + result.getNewsImage();
                        Intent imgIntent = new Intent(context, FullscreenImageActivity.class);
                        imgIntent.putExtra(AllConstants.IMG_CLICKED_URI, imgUri);
                        context.startActivity(imgIntent);
                    }
                });

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return newsModel == null ? 0 : newsModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == newsModel.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }




    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(NewsModel r) {
        newsModel.add(r);
        notifyItemInserted(newsModel.size() - 1);
    }

    public void addAll(List<NewsModel> moveResults) {
        for (NewsModel result : moveResults) {
            add(result);
        }
    }

    public void remove(NewsModel r) {
        int position = newsModel.indexOf(r);
        if (position > -1) {
            newsModel.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new NewsModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = newsModel.size() - 1;
        NewsModel result = getItem(position);

        if (result != null) {
            newsModel.remove(position);
            notifyItemRemoved(position);
        }
    }

    public NewsModel getItem(int position) {
        return newsModel.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        LinearLayout newsfraglinearlayout;
        TextView tvHomefragnewstitle;
        ImageView imgHomefragimage;
        TextView tvHomefragnewspublished;
        ExpandableTextView tvHomefragnewscontent;

        private ProgressBar mProgress;

        public MovieVH(View itemView) {
            super(itemView);

            newsfraglinearlayout = itemView.findViewById(R.id.news_frag_linearlayout);
            tvHomefragnewstitle = itemView.findViewById(R.id.home_frag_news_title);
            imgHomefragimage = itemView.findViewById(R.id.home_frag_image);
            tvHomefragnewspublished = itemView.findViewById(R.id.home_frag_date);
            tvHomefragnewscontent = itemView.findViewById(R.id.home_frag_news_content);


            mProgress = itemView.findViewById(R.id.fraghomenewsProgressbar);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
