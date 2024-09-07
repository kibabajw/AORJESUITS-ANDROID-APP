package org.easternafricajesuits.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class UtilDownloadFile extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String path = params[0];
        String filename = null;

        try {
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            File newsfolder = new File("news");

            if (!newsfolder.exists()) {
                newsfolder.mkdir();
            } else {
                Log.i("NEWSADAPTER", "Folder IKO");
            }

            filename = "newsitem" + new Date().getTime() + ".jpg";

            File inputfile = new File(newsfolder, filename);
            InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
            byte[] data = new byte[1024];

            int total = 0;
            int count = 0;

            OutputStream outputStream = new FileOutputStream(inputfile);

            while ((count = inputStream.read(data)) != -1) {
                total += count;
                outputStream.write(data, 0, count);
            }

            inputStream.close();
            outputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filename;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String unused) {
        Log.i("NEWSADAPTER", "Ndani ya UtilDownloadFile: " + unused);
    }
}
