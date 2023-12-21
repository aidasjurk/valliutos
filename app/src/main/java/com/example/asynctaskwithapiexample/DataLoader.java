package com.example.asynctaskwithapiexample;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataLoader extends AsyncTask<String, Void, List<String>> {

    private DataLoaderListener listener;

    public DataLoader(DataLoaderListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<String> doInBackground(@NonNull String... urls) {
        List<String> result = new ArrayList<>();
        if (urls.length > 0) {
            String url = urls[0];
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.add(line);
                    }
                    bufferedReader.close();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception as needed
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        if (listener != null) {
            listener.onDataLoaded(result);
        }
    }

    public interface DataLoaderListener {
        void onDataLoaded(List<String> data);
    }
}
