package org.easternafricajesuits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.easternafricajesuits.databinding.ActivitySplashScreenBinding;
import org.easternafricajesuits.news.NewsContract;
import org.easternafricajesuits.news.NewsDbHelper;
import org.easternafricajesuits.news.NewsService;
import org.easternafricajesuits.utils.UtilSharedPref;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private UtilSharedPref utilSharedPref;

    private NewsDbHelper newsDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        newsDbHelper = new NewsDbHelper(SplashScreenActivity.this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        }, 2000);

        utilSharedPref = new UtilSharedPref(SplashScreenActivity.this);

//        isnewsfetchinitialised();
    }

    private void isnewsfetchinitialised() {
        String strinitedvalue = utilSharedPref.getStoragePreference("newsinited");

//        NewsService.startActionNews(this, strinitedvalue, prayerintentionval);

        if(strinitedvalue.equals("0")) {
            // news fetch has not been initiated
            NewsService.startActionNews(this, strinitedvalue);
        } else {
            // news fetch has been initiated, so expect to find news items in news table
//            checknewsitems();
        }

    }

    private void checknewsitems() {
        SQLiteDatabase db = newsDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + NewsContract.NewsEntry.TABLE_NAME, null);

        int count = cursor.getCount();

        try {
            if(cursor.getCount() == 0) {
                // make network request and save 10 news items to sqlite
                Toast.makeText(SplashScreenActivity.this, "COUNT: " + count, Toast.LENGTH_SHORT).show();
            } else {
                // we have news locally
                Toast.makeText(SplashScreenActivity.this, "COUNT: " + count, Toast.LENGTH_SHORT).show();
            }
        } finally {
            cursor.close();
        }
    }
}