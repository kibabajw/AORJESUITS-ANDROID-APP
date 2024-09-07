package org.easternafricajesuits.adusum.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import org.easternafricajesuits.R;
import org.easternafricajesuits.adusum.AdusumLoginActivity;
import org.easternafricajesuits.adusum.model.AdusumServiceModel;
import org.easternafricajesuits.clients.RetrofitClient;
import org.easternafricajesuits.adusum.databases.AdusumDatabaseContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdusumService extends Service {
    public static boolean IsRunning = false;
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    private long whatID = 0;
    private String currentBrotherLocalId = "";
    private String currentBrotherOnlineId = "";
    private String CHANNEL_ID = "CHANNEL_ID";

    private static final int NOTIFY_ID = 1001;
    //Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (networkRequest() == 1) {
                // -------------- NEW NOTIFICATION CODE, HERE ----------------------------
                NotificationCompat.Builder builder = new NotificationCompat.Builder(AdusumService.this, CHANNEL_ID);
                Intent intent = new Intent(AdusumService.this, AdusumLoginActivity.class);
                intent.putExtra("notifyID", NOTIFY_ID);
                PendingIntent pendingIntent = PendingIntent.getActivity(AdusumService.this, NOTIFY_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setSmallIcon(R.drawable.ic_notify);
                builder.setContentTitle("Account activated");
                builder.setContentText("Your account has been approved and activated, tap to login");
                builder.setAutoCancel(true);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                builder.setSubText("Tap to view more");
                builder.setContentIntent(pendingIntent);

                // Set the lock screen visibility of the notification
                builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

                Notification notification = builder.build();
                NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                mgr.notify(NOTIFY_ID, notification);

                // Update brother's active status inSQLite then destroy the service
                if(updateBroSqlite() == 1) {
                    stopSelf(msg.arg1);
                }

            } else {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                handleMessage(msg);
            }

        }
    }

    private long updateBroSqlite() {
        ContentValues values = new ContentValues();
        values.put(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ACCOUNT_STATUS, 1);

        int rowsAffected = getContentResolver().update(
                ContentUris.withAppendedId(AdusumDatabaseContract.BrotherEntry.CONTENT_URI, Long.parseLong(currentBrotherLocalId)), values, null, null);

        if (rowsAffected == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    private long networkRequest() {
        if (checkNetwork() == true) {

            Call<AdusumServiceModel> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getBrother(currentBrotherOnlineId);
            call.enqueue(new Callback<AdusumServiceModel>() {
                @Override
                public void onResponse(Call<AdusumServiceModel> call, Response<AdusumServiceModel> response) {
                    if (response.code() == 200) {

                        if (response != null) {
                            if (response.body().getUbrostatus().equals("1")) {
                                whatID = 1;
                            } else {
                                whatID = 0;
                            }
                        } else {
                            whatID = 0;
                        }

                    }
                }

                @Override
                public void onFailure(Call<AdusumServiceModel> call, Throwable t) {
                    whatID = 0;
                }
            });
            return whatID;
        } else {

            whatID = 0;
        }
        return whatID;
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    public boolean checkNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) AdusumService.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentBrotherLocalId = intent.getStringExtra(AdusumDatabaseContract.BrotherEntry._ID);
        currentBrotherOnlineId = intent.getStringExtra(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_ONLINE_ID);
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
