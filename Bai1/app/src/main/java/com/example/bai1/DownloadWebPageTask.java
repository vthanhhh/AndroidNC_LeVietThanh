package com.example.bai1;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    private TextView textView;
    public DownloadWebPageTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            for (String url : urls) {
                URL urlObj = new URL(url);
                urlConnection = (HttpURLConnection) urlObj.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n"); // Thêm dòng mới để dễ đọc hơn
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if (textView != null) {
            textView.setText(result);
        }
    }
}
