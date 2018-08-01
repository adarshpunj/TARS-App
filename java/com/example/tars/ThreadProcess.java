package com.example.tars;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.MalformedInputException;

import static android.content.Intent.ACTION_VIEW;

public class ThreadProcess extends AsyncTask<Void,Void,Void> {

    private String data=" ";

    Context context;
    public ThreadProcess(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.download.setBackgroundResource(R.drawable.wait);

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        boolean wasActivityStarted = false;

        MainActivity.download.setBackgroundResource(R.drawable.download);
        try {
            JSONObject reader = new JSONObject(data);
            String link = reader.getString("link");
            try{
                context.startActivity(new Intent(ACTION_VIEW).setData(Uri.parse(link)));
                wasActivityStarted = true;
            }catch (Exception e){
                //
            }
            if(wasActivityStarted==true){
                Handler handler =  new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Subtitle Located",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //TODO: Push a toast message upon failure.
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String userInput = String.valueOf(MainActivity.input.getText());
            userInput.replaceAll("\\s","+");
            URL url = new URL("http://adarshpunj.pythonanywhere.com/subtitle/"+userInput);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                data = data + line;
            }
        }catch (MalformedInputException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
