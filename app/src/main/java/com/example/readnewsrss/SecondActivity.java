package com.example.readnewsrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SecondActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        String link = intent.getStringExtra("linkNews");

        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}