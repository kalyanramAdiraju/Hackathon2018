package com.appraisalmanagement.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.appraisalmanagement.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PdfViewActivity extends AppCompatActivity {

    private String pdfFileViewName,pdfFile;
    private static final int PROGRESS_MAX = 100;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.pdf_webview)
    WebView webView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        ButterKnife.bind(this);
        setUpToolBar();
        pdfWebViewUrl();


    }

    private void pdfWebViewUrl() {
        webView.setWebViewClient(new PdfWebBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        pdfFile = getIntent().getExtras().getString("pdfUrl");
        String pdf = pdfFile;
        try {
            pdf = URLEncoder.encode(pdfFile, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("Exception", String.valueOf(e));
        }
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < PROGRESS_MAX) {
                    webView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    webView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        pdfFileViewName = getIntent().getExtras().getString("pdfFileViewName");
        getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDelegate().getSupportActionBar().setDisplayShowTitleEnabled(true);
        getDelegate().getSupportActionBar().setTitle(pdfFileViewName);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private class PdfWebBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
