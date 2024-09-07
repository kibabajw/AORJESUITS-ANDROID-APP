package org.easternafricajesuits.news;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;

import org.easternafricajesuits.AllConstants;
import org.easternafricajesuits.R;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.models.HeadersModel;
import org.easternafricajesuits.models.NewsModel;
import org.easternafricajesuits.models.NewsReceivedModel;
import org.easternafricajesuits.monthlythought.MonthlyThoughtContract;
import org.easternafricajesuits.monthlythought.MonthlyThoughtDbHelper;
import org.easternafricajesuits.utils.UtilSharedPref;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsService extends IntentService {
    private static NewsDbHelper newsDbHelper;
    private static MonthlyThoughtDbHelper monthlyThoughtDbHelper;

    private static UtilSharedPref utilSharedPref;

    private static final String ACTION_FOO = "org.easternafricajesuits.news.action.FOO";
    private static final String ACTION_POPES_PRAYER = "org.easternafricajesuits.news.action.POPESPRAYER";

    private static final String EXTRA_PARAM1 = "org.easternafricajesuits.news.extra.PARAM1";
    private static String CALLING_ACTIVITY = null;

    private static Intent intentNewsFetch;

    public NewsService() {
        super("NewsService");
    }
    private DateFormat currentMonth;
    private Date date;
    public static void startActionNews(Context context, String param1) {
        intentNewsFetch = new Intent(context, NewsService.class);
        intentNewsFetch.setAction(ACTION_FOO);

        intentNewsFetch.putExtra(EXTRA_PARAM1, param1);

        newsDbHelper = new NewsDbHelper(context);
        monthlyThoughtDbHelper = new MonthlyThoughtDbHelper(context);
        utilSharedPref = new UtilSharedPref(context);

        CALLING_ACTIVITY = context.getClass().getSimpleName();

        context.startService(intentNewsFetch);
    }

    public static void startActionPopesprayer(Context context) {
        intentNewsFetch = new Intent(context, NewsService.class);
        intentNewsFetch.setAction(ACTION_POPES_PRAYER);

        monthlyThoughtDbHelper = new MonthlyThoughtDbHelper(context);
        utilSharedPref = new UtilSharedPref(context);

        CALLING_ACTIVITY = context.getClass().getSimpleName();

        context.startService(intentNewsFetch);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionNews(param1);
            } else if (ACTION_POPES_PRAYER.equals(action)) {
                handleActionPopesPrayer();
            }
        }
    }

    private void handleActionNews(String param1) {
        loadFirstPageFromServer(Integer.parseInt(param1));
    }
    private void handleActionPopesPrayer() {
        initmonthlyrequest();
    }
    private void loadFirstPageFromServer(int firstID) {
        Call<NewsReceivedModel> call = RetrofitClient.getInstance().getApi().getNews(firstID);
        call.enqueue(new Callback<NewsReceivedModel>() {
            @Override
            public void onResponse(Call<NewsReceivedModel> call, Response<NewsReceivedModel> response) {
                if (response.code() == 200) {
                    // new data returned, clear the table
                    emptytablenews(NewsContract.NewsEntry.TABLE_NAME);

                    int incounter = 0;

                    List<NewsModel> results = fetchResults(response);

//                    int lastid = Integer.parseInt(results.get(0).getNewsId()) + 1;
                    int lastid = Integer.parseInt(results.get(0).getNewsId());

                    SQLiteDatabase db = newsDbHelper.getWritableDatabase();
                    db.beginTransaction();

                    for (int i = 0; i < results.size(); i++) {
                        incounter++;
                        ContentValues values = new ContentValues();
                        values.put(NewsContract.NewsEntry.COLUMN_TITLE, results.get(i).getNewsTitle());
                        values.put(NewsContract.NewsEntry.COLUMN_CREATED_AT, results.get(i).getNewsCreatedAt());
                        values.put(NewsContract.NewsEntry.COLUMN_IMAGE, results.get(i).getNewsImage());
                        values.put(NewsContract.NewsEntry.COLUMN_BODY, results.get(i).getNewsBody());

                        db.insert(NewsContract.NewsEntry.TABLE_NAME, null, values);
                    }

                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();

                    if (incounter == 10 || incounter > 0) {
                        if (CALLING_ACTIVITY.equals("SplashScreenActivity")) {
                            sendMessage("200news");
                        } else if (CALLING_ACTIVITY.equals("MainActivity")) {
                            sendMessage("updatednews");
                        }
                        // update shared preferences
                        utilSharedPref.setPref("newsinited", String.valueOf(lastid));
                    }
                } else if (response.code() == 404) {
                    sendMessage("404news");
                } else if (response.code() == 500) {
                    sendMessage("500news");
                } else {
                    sendMessage("500news");
                }

            }

            @Override
            public void onFailure(Call<NewsReceivedModel> call, Throwable t) {
                sendMessage("500news");
            }
        });
    }

    private void initmonthlyrequest() {
        String monthlyprayermonth = utilSharedPref.getStoragePreference("prayerintentionmonth");

        if (CALLING_ACTIVITY.equals("SplashScreenActivity")) {
            fetchmonthlythought();
        } else if(CALLING_ACTIVITY.equals("MainActivity")) {
            currentMonth = new SimpleDateFormat("MMMM");
            date = new Date();
            String thisMonth = currentMonth.format(date);

            // if saved month == currentMonth no need to make network request
            if (!thisMonth.equals(monthlyprayermonth)) {
                fetchmonthlythought();
            }
        }
    }

    private void fetchmonthlythought() {
        SQLiteDatabase db = monthlyThoughtDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MonthlyThoughtContract.ThoughtEntry.TABLE_NAME, null);

        int count = cursor.getCount();

        if (count > 0) {
            emptytablepmonthlyprayers(MonthlyThoughtContract.ThoughtEntry.TABLE_NAME);
        }

        Call<HeadersModel> call = RetrofitClient.getInstance().getApi().getHeader("1");
        call.enqueue(new Callback<HeadersModel>() {
            @Override
            public void onResponse(Call<HeadersModel> call, Response<HeadersModel> response) {
                if (response.code() == 200) {
                    String prayermonth = null;

                    switch (String.valueOf(response.body().getPageupdatedat().split("-")[1].charAt(1))) {
                        case "1":
                            prayermonth = "January";
                            break;
                        case "2":
                            prayermonth = "February";
                            break;
                        case "3":
                            prayermonth = "March";
                            break;
                        case "4":
                            prayermonth = "April";
                            break;
                        case "5":
                            prayermonth = "May";
                            break;
                        case "6":
                            prayermonth = "June";
                            break;
                        case "7":
                            prayermonth = "July";
                            break;
                        case "8":
                            prayermonth = "August";
                            break;
                        case "9":
                            prayermonth = "September";
                            break;
                        case "10":
                            prayermonth = "October";
                            break;
                        case "11":
                            prayermonth = "November";
                            break;
                        case "12":
                            prayermonth = "December";
                            break;
                    }
                    utilSharedPref.setPref("prayerintentionmonth", prayermonth);

                    SQLiteDatabase db = monthlyThoughtDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_HEADING, response.body().getPageheading());
                    values.put(MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_TITLE, response.body().getPagetitle());
                    values.put(MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_SUB_TITLE, response.body().getPagesubtitle());
                    values.put(MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_IMAGE, response.body().getPageimage());
                    values.put(MonthlyThoughtContract.ThoughtEntry.COLUMN_THOUGHT_UPDATED_AT, response.body().getPageupdatedat());

                    long newrow = db.insert(MonthlyThoughtContract.ThoughtEntry.TABLE_NAME, null, values);

                    if (newrow != -1) {
                        getApplicationContext().stopService(intentNewsFetch);
                        db.close();
                        if (CALLING_ACTIVITY.equals("SplashScreenActivity")) {
                            sendMessage("200popesprayer");
                        } else if (CALLING_ACTIVITY.equals("MainActivity")) {
                            sendMessage("updatedprayer");
                        }
                    } else {
                        getApplicationContext().stopService(intentNewsFetch);
                        db.close();
                    }
                } else if (response.code() == 404) {
                    sendMessage("404popesprayer");
                } else if (response.code() == 500) {
                    sendMessage("500popesprayer");
                } else {
                    sendMessage("500popesprayer");
                }
            }

            @Override
            public void onFailure(Call<HeadersModel> call, Throwable t) {

            }
        });

    }

    private void emptytablenews(String tableName) {
        SQLiteDatabase db = newsDbHelper.getWritableDatabase();

        db.execSQL("delete from "+ tableName);
    }

    private void emptytablepmonthlyprayers(String tableName) {
        SQLiteDatabase db = monthlyThoughtDbHelper.getWritableDatabase();

        db.execSQL("delete from "+ tableName);
    }

    private List<NewsModel> fetchResults(Response<NewsReceivedModel> response) {
        NewsReceivedModel receivedModel = response.body();
        return receivedModel.getNewsModel();
    }

    private void sendMessage(String message) {
        Intent intent = new Intent(AllConstants.SERVICE_NEWS);
        intent.putExtra(AllConstants.MESSAGE_KEY, message);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

}