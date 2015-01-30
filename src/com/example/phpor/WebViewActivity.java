package com.example.phpor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

/**
 * Created by junjie on 2015/1/30.
 */
public class WebViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        findViewById(R.id.btn_webview_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ((EditText)findViewById(R.id.textUrl)).getText().toString();
                WebView webview = (WebView)findViewById(R.id.webview);
                webview.loadUrl(url);
            }
        });
    }
}