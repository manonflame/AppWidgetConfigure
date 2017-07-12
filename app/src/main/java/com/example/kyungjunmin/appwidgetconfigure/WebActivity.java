package com.example.kyungjunmin.appwidgetconfigure;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity {


    //웹뷰와 웹뷰 세팅
    private WebView mWebView;
    private WebSettings mWebSettings;

    //progressbar
    ProgressBar progressBar;

    //액티비티에서 받을 textString : 검색할 문자열
    String word;
    String daumURL = "https://m.search.daum.net/search?w=tot&nil_mtopsearch=btn&DA=YZR&q=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);





        //인텐트와 문자열 받음
        progressBar = (ProgressBar)this.findViewById(R.id.ProgressBar);


        Uri data =getIntent().getData();
        word = data.toString();

        System.out.println("what does WEB ACTIVITY take??? " + word);

        //웹뷰 연결
        mWebView = (WebView)findViewById(R.id.webView);

        //웹뷰 기본 설정
        mWebView.canGoBack();
        mWebView.reload();
        mWebView.setWebViewClient(new WebViewClient(){
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

        });

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(false);

        mWebView.loadUrl(daumURL + word);


    }


    //뒤로가기 버튼 만들기
    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event){
        if((KeyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(KeyCode, event);
    }
}
