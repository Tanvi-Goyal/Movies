package com.tmovies.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.tmovies.R;
import com.tmovies.utils.MyWebViewClient;

public class WebViewActivity extends AppCompatActivity {

    String position;
    String url;
    private WebView webView;
    private FloatingActionButton backward, forward;
    ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        showProcessDialog();
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

        int DELAY = 5000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProcessDialog();

            }
        }, DELAY);

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = Integer.parseInt(position);

                if (Integer.parseInt(position) <= 0) {
                    backward.setClickable(false);
                    Toast.makeText(WebViewActivity.this, getString(R.string.no_previous_movie), Toast.LENGTH_SHORT).show();
                } else {
                    if (MovieScreen.adapter.getItemCount() > --currentPosition) {
                        url = MovieScreen.adapter.getItem(currentPosition).getDetail_url();
                        webView.loadUrl(url);
                    } else {
                        Toast.makeText(WebViewActivity.this, getString(R.string.no_previous_movie), Toast.LENGTH_SHORT).show();
                    }

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
                    Toast.makeText(WebViewActivity.this, getString(R.string.no_next_movie), Toast.LENGTH_SHORT).show();
                } else {
                    if (MovieScreen.adapter.getItemCount() > ++currentPosition) {
                        url = MovieScreen.adapter.getItem(currentPosition).getDetail_url();
                        webView.loadUrl(url);
                    } else {
                        Toast.makeText(WebViewActivity.this, getString(R.string.no_next_movie), Toast.LENGTH_SHORT).show();
                    }

                }
                position = String.valueOf(currentPosition);
            }
        });
    }

    public void showProcessDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait...");
        pd.setMessage("Loading Info");
        pd.setCancelable(false);
        pd.show();
    }

    public void hideProcessDialog() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

}
