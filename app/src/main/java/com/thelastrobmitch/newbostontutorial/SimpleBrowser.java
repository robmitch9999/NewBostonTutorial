package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Robert on 17/09/2017.
 */

public class SimpleBrowser extends Activity implements View.OnClickListener {

    EditText url;
    WebView ourBrowser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simplebrowser);

        ourBrowser = (WebView) findViewById(R.id.wvBrowser);
        ourBrowser.getSettings().setJavaScriptEnabled(true);
        ourBrowser.getSettings().setLoadWithOverviewMode(true);
        ourBrowser.getSettings().setUseWideViewPort(true);

        ourBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        try {
            ourBrowser.loadUrl("http://www.smh.com.au");
        }catch (Exception e) {
            e.printStackTrace();
        }

        Button go = (Button) findViewById(R.id.bGo);
        Button back = (Button) findViewById(R.id.bGoBack);
        Button refresh = (Button) findViewById(R.id.bRefresh);
        Button forward = (Button) findViewById(R.id.bGoForwrd);
        Button clearHistory = (Button) findViewById(R.id.bClearHistory);
        url = (EditText) findViewById(R.id.etURL);

        go.setOnClickListener(this);
        back.setOnClickListener(this);
        refresh.setOnClickListener(this);
        forward.setOnClickListener(this);
        clearHistory.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bGo:
                String theWebSite = url.getText().toString();

                try {
                    ourBrowser.loadUrl(theWebSite);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                // lets hide the keyboard now
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(url.getWindowToken(), 0);

                break;
            case R.id.bGoForwrd:
                if (ourBrowser.canGoForward())  ourBrowser.goForward();
                break;
            case R.id.bGoBack:
                if (ourBrowser.canGoBack()) ourBrowser.goBack();
                break;
            case R.id.bRefresh:
                ourBrowser.reload();
                break;
            case R.id.bClearHistory:
                ourBrowser.clearHistory();
                break;
        }
    }
}
