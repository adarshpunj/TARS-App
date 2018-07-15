package com.example.tars;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
public class MainActivity extends Activity{

    public static ImageView download;
    public static ImageView github;
    public static TextView text;
    public static EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        download = findViewById(R.id.download);
        text = findViewById(R.id.text);
        input = findViewById(R.id.input);
        github = findViewById(R.id.github);

        download.setBackgroundResource(R.drawable.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ThreadProcess(MainActivity.this).execute();
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/adarshpunj")));
            }
        });
    }
}