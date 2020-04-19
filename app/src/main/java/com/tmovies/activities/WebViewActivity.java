package com.tmovies.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tmovies.R;

public class WebViewActivity extends AppCompatActivity {

    String position;
    String url;
    private WebView webView;
    private FloatingActionButton backward, forward;
    ProgressDialog pd;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setStatusBarColor();

        position = getIntent().getStringExtra("position");
        url = getIntent().getStringExtra("detail_url");

        pd = new ProgressDialog(this);

        webView = findViewById(R.id.web_view);
        backward = findViewById(R.id.btn_backward);
        forward = findViewById(R.id.btn_forward);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        MyWebViewClient webViewClient = new MyWebViewClient();
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(url);

        setBackBtnListener();
        setForwardBtnListener();
    }

    private void setForwardBtnListener() {
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

    private void setBackBtnListener() {
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
    }

    public void showProcessDialog() {
        if (!pd.isShowing()) {
            pd.setTitle("Please Wait...");
            pd.setMessage("Loading Info");
            pd.setCancelable(false);
            pd.show();
        }
    }

    public void hideProcessDialog() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProcessDialog();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProcessDialog();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }

}
