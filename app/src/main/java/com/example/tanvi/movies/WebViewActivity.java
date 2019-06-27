package com.example.tanvi.movies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    String position;
    String url;
    private WebView webView;
    private FloatingActionButton backward, forward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        position = getIntent().getStringExtra("position");
        url = getIntent().getStringExtra("detail_url");
        this.webView = findViewById(R.id.web_view);
        backward = findViewById(R.id.btn_backward);
        forward = findViewById(R.id.btn_forward);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        MyWebViewClient webViewClient = new MyWebViewClient();
        webView.setWebViewClient(webViewClient);

        webView.loadUrl(url);


        Toast.makeText(this, "Clecked " + position + " with url " + url, Toast.LENGTH_SHORT).show();
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = Integer.parseInt(position);

                if (Integer.parseInt(position) < 0) {
                    backward.setClickable(false);
                    Toast.makeText(WebViewActivity.this, "There is no previous movie.", Toast.LENGTH_SHORT).show();
                } else {
                    url = MovieScreen.adapter.getItem(--currentPosition).getDetail_url();
                    webView.loadUrl(url);
                }

                position = String.valueOf(currentPosition);

            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = Integer.parseInt(position);
                if (Integer.parseInt(position) >= 19) {
                    forward.setClickable(false);
                    Toast.makeText(WebViewActivity.this, "There is no next movie.", Toast.LENGTH_SHORT).show();
                } else {

                    url = MovieScreen.adapter.getItem(++currentPosition).getDetail_url();
                    webView.loadUrl(url);
                }

                position = String.valueOf(currentPosition);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
