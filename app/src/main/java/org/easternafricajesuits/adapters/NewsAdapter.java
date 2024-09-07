package org.easternafricajesuits.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.FullscreenImageActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.models.NewsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

                movieVH.tvNewsItemAuthor.setText(result.getAuthor());
                movieVH.tvNewsItemTimestamp.setText(result.getNewsCreatedAt());

                movieVH.tvHomefragnewstitle.setText( Html.fromHtml(result.getNewsTitle()).toString() );

                movieVH.tvHomefragnewscontent.setText( Html.fromHtml(result.getNewsBody()).toString() );

                Glide.with(context)
                        .load(AllConstants.IMAGE_URL +result.getNewsImage())
                        .centerCrop()
                        .placeholder(R.drawable.loadingdots)
                        .into(movieVH.imgHomefragimage);

//                Log.i("TAGA", result.getNewsTitle());


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
        TextView tvNewsItemAuthor;
        TextView tvNewsItemTimestamp;
        TextView tvHomefragnewstitle;
        ImageView imgHomefragimage;

        ExpandableTextView tvHomefragnewscontent;

        private ProgressBar mProgress;

        public MovieVH(View itemView) {
            super(itemView);

            newsfraglinearlayout = itemView.findViewById(R.id.news_frag_linearlayout);
            tvNewsItemAuthor = itemView.findViewById(R.id.news_item_author_text_view);
            tvNewsItemTimestamp = itemView.findViewById(R.id.news_item_timestamp_text_view);
            tvHomefragnewstitle = itemView.findViewById(R.id.home_frag_news_title);
            imgHomefragimage = itemView.findViewById(R.id.home_frag_image);

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
