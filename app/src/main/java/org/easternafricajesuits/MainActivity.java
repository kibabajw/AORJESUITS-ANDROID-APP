package org.easternafricajesuits;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.easternafricajesuits.adapters.HomePageAdapter;
import org.easternafricajesuits.adusum.AdusumAccountActivity;
import org.easternafricajesuits.adusum.AdusumLoginActivity;
import org.easternafricajesuits.adusum.AdusumregisterActivity;
import org.easternafricajesuits.adusum.ProbationActivity;
import org.easternafricajesuits.adusum.databases.AdusumAccountSQLHelper;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;
import org.easternafricajesuits.databinding.ActivityMainBinding;
import org.easternafricajesuits.fragments.HomeFragmentAboutus;
import org.easternafricajesuits.fragments.HomeFragmentDailyExamen;
import org.easternafricajesuits.fragments.HomeFragmentEvents;
import org.easternafricajesuits.fragments.HomeFragmentJoinus;
import org.easternafricajesuits.fragments.HomeFragmentNews;
import org.easternafricajesuits.fragments.HomeFragmentPrayers;
import org.easternafricajesuits.fragments.HomeFragmentUAPs;
import org.easternafricajesuits.jesuitjargon.JesuitJargonActivity;
import org.easternafricajesuits.merchandise.MerchandiseActivity;
import org.easternafricajesuits.spiritualexercises.SpiritualExercisesActivity;
import org.easternafricajesuits.thepilgrim.ThePilgrimActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private long pressedTime = 0;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private ViewPager viewPager;
    private HomePageAdapter homePageAdapter;
    private TabLayout hometablayout;

    // SQLite database
    private AdusumAccountSQLHelper mAdusumAccountSQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        getWindow().setBackgroundDrawable(null);

        initialise();
        prepareDataResource();

        if (homePageAdapter == null) {
            homePageAdapter = new HomePageAdapter(getSupportFragmentManager(), fragmentList, titleList);
        }
        viewPager.setAdapter(homePageAdapter);

        hometablayout.setupWithViewPager(viewPager);
        mAdusumAccountSQLHelper = new AdusumAccountSQLHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_item_spiritual_exercises:
                startActivity(new Intent(MainActivity.this, SpiritualExercisesActivity.class));
                return true;
            case R.id.home_menu_item_ad_usum:
                // check if there is an account in local storage and whether or not the account is active
                SQLiteDatabase db = mAdusumAccountSQLHelper.getReadableDatabase();

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
                        startActivity(new Intent(MainActivity.this, AdusumregisterActivity.class));
                        return true;
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
                            startActivity(new Intent(MainActivity.this, ProbationActivity.class));
                            return true;
                        } else if (status.equals("1") && tkn.equals("0")) {
                            startActivity(new Intent(MainActivity.this, AdusumLoginActivity.class));
                            return true;
                        } else {
                            startActivity(new Intent(MainActivity.this, AdusumAccountActivity.class));
                            return true;
                        }
                    }
                } finally {
                    cursor.close();
                }
            case R.id.home_menu_item_the_pilgrim:
                startActivity(new Intent(MainActivity.this, ThePilgrimActivity.class));
                return true;
            case  R.id.home_menu_item_jesuit_jargon:
                startActivity(new Intent(MainActivity.this, JesuitJargonActivity.class));
                return true;
            case R.id.home_menu_item_merchandise:
                startActivity(new Intent(MainActivity.this, MerchandiseActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initialise() {
//        Toolbar main_home_toolbar = (Toolbar) findViewById(R.id.main_home_toolbar);
        Toolbar main_home_toolbar = binding.mainHomeToolbar;
        setSupportActionBar(main_home_toolbar);
        main_home_toolbar.setTitle("AOR JESUITS");

        //set Toolbar's transparency
//        main_home_toolbar.getBackground().setAlpha(2);

//        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        hometablayout = (TabLayout) findViewById(R.id.tabsHome);

        viewPager = binding.viewPager;
        hometablayout = binding.tabsHome;
    }

    private void prepareDataResource() {
        addData(new HomeFragmentNews(), "News");
        addData(new HomeFragmentAboutus(), "About us");
        addData(new HomeFragmentEvents(), "Events");
        addData(new HomeFragmentDailyExamen(), "Daily Examen");
        addData(new HomeFragmentUAPs(), "UAPs");
        addData(new HomeFragmentPrayers(), "Prayers");
        addData(new HomeFragmentJoinus(), "Join us");
    }

    private void addData(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}