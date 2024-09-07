package org.easternafricajesuits.summary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.AdusumAccountActivity;
import org.easternafricajesuits.adusum.AdusumLoginActivity;
import org.easternafricajesuits.adusum.ProbationActivity;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.databinding.ActivitySummaryBinding;
import org.easternafricajesuits.models.NewsModel;
import org.easternafricajesuits.models.NewsReceivedModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.sweettoast.SweetToast;

public class SummaryActivity extends AppCompatActivity {
    private ActivitySummaryBinding binding;
    private TextView tv_popesprayer_heading, tv_popesprayer_title, tv_popesprayer_body, tv_popesprayer_day, tv_popesprayer_month;
    private ImageView img_popesprayer;
    private ShimmerFrameLayout shimmerPopesPrayer;
    private ShimmerFrameLayout shimmerNewsSummary;

    private DateFormat currentMonth;
    private DateFormat currentDay;
    private Date date;

    private RecyclerView recycler_news_summary;
    private List<NewsSummaryModel> newsModel = new ArrayList<>();
    RecyclerView.Adapter adapter;
    private NewsSummaryAdapter newsSummaryAdapter;
    private LinearLayoutManager linearLayoutManager;

    private AdusumAccountSQLHelper adusumAccountSQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySummaryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbarInSummary;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_popesprayer_heading = binding.popesPrayerItemHeading;
        tv_popesprayer_title = binding.popesPrayerItemTitle;
        tv_popesprayer_body = binding.popesPrayerBody;
        img_popesprayer = binding.popesPrayerImage;

        shimmerPopesPrayer = binding.shimmerLayout;
        shimmerPopesPrayer.startShimmer();

        shimmerNewsSummary = binding.shimmerLayoutRecyclerNewsSummary;
        shimmerNewsSummary.startShimmer();

        tv_popesprayer_day = binding.popesPrayerItemDay;
        tv_popesprayer_month = binding.popesPrayerItemMonth;

       recycler_news_summary = binding.recyclerNewsSummary;
       recycler_news_summary.setLayoutManager(new LinearLayoutManager(this));
       adapter = new NewsSummaryAdapter(newsModel, R.layout.news_summary_item, SummaryActivity.this);

       recycler_news_summary.setAdapter(adapter);

        binding.fabInSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SummaryActivity.this, MainActivity.class));
            }
        });

        displaymonthlydate();

        getpopesprayerintentions();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadnewsSummary(0);
            }
        }, 1500);


        adusumAccountSQLHelper = new AdusumAccountSQLHelper(this);
    }

    private void displaymonthlydate() {
        currentMonth = new SimpleDateFormat("MMMM");
        currentDay = new SimpleDateFormat("d");

        date = new Date();

        // set month-text and month-digit
        String thisMonth = currentMonth.format(date).toUpperCase();
        String today = currentDay.format(date);

        tv_popesprayer_day.setText(today);
        tv_popesprayer_month.setText(thisMonth);
    }

    private void processadusum() {
        SQLiteDatabase db = adusumAccountSQLHelper.getReadableDatabase();

        String[] projection = {
                AdusumDatabaseContract.BrotherEntry._ID,
                AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_STATUS,
                AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN
        };

        Cursor cursor = db.query(
                AdusumDatabaseContract.BrotherEntry.ADUSUMACCOUNT_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            if (cursor.getCount() == 0) {
                startActivity(new Intent(SummaryActivity.this, AdusumLoginActivity.class));
            } else {
                int tokenColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_COOKIE_TOKEN);
                int statusColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_STATUS);

                String tkn = "";
                String status = "";

                while (cursor.moveToNext()) {
                    tkn = cursor.getString(tokenColumnIndex);
                    status = cursor.getString(statusColumnIndex);
                }

                if (status.equals("0") && tkn.equals("0")) {
                    startActivity(new Intent(SummaryActivity.this, ProbationActivity.class));
                } else if (status.equals("1") && tkn.equals("0")) {
                    startActivity(new Intent(SummaryActivity.this, AdusumLoginActivity.class));
                } else {
                    startActivity(new Intent(SummaryActivity.this, AdusumAccountActivity.class));
                }
            }
        } finally {
            cursor.close();
        }
    }

    private void getpopesprayerintentions() {
        Call<PopesPrayerModel> call = RetrofitClient.getInstance().getApi().getPopesprayerintention();
        call.enqueue(new Callback<PopesPrayerModel>() {
            @Override
            public void onResponse(Call<PopesPrayerModel> call, Response<PopesPrayerModel> response) {
                tv_popesprayer_heading.append(response.body().getPrayer_month().toUpperCase());
                tv_popesprayer_title.setText(response.body().getPrayer_name());
                tv_popesprayer_body.setText(response.body().getPrayer_item());

                Glide.with(SummaryActivity.this)
                        .load(AllConstants.IMAGE_URL + response.body().getPrayer_image())
                        .centerInside()
                        .placeholder(R.drawable.loadingdots)
                        .into(img_popesprayer);


                shimmerPopesPrayer.stopShimmer();
                shimmerPopesPrayer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PopesPrayerModel> call, Throwable t) {
                if (t.toString().startsWith("java.net.UnknownHostException")) {
                    SweetToast.error(SummaryActivity.this, "No internet access");
                }
            }
        });
    }

    private void loadnewsSummary(int firstID) {
        Call<NewsSummaryReceivedModel> call = RetrofitClient.getInstance().getApi().getNewsSummary(firstID);
        call.enqueue(new Callback<NewsSummaryReceivedModel>() {
            @Override
            public void onResponse(Call<NewsSummaryReceivedModel> call, Response<NewsSummaryReceivedModel> response) {
                if (response.code() == 200) {
                    newsModel.clear();
                    newsModel.addAll(response.body().getNewsModel());
                    adapter.notifyDataSetChanged();

                    shimmerNewsSummary.stopShimmer();
                    shimmerNewsSummary.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NewsSummaryReceivedModel> call, Throwable t) {

            }
        });
    }

    private List<NewsModel> fetchResults(Response<NewsReceivedModel> response) {
        NewsReceivedModel receivedModel = response.body();

        return receivedModel.getNewsModel();
    }



}