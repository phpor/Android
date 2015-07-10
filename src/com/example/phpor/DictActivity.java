package com.example.phpor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DictActivity extends Activity {
    private String key = "100447363";
    private String keyfrom = "phporblog";
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dict);

        Intent intent = getIntent();

        String action = intent.getAction();
        String intentType = intent.getType();
        String data = intent.getStringExtra(Intent.EXTRA_TEXT);
        Log.d("phpor", "action: " + action);
        Log.d("phpor", "intenttype: " + intentType);
        Log.d("phpor", "data: " + data);
        translate(data);
    }

    private void translate(String word) {
        String url = "http://fanyi.youdao.com/openapi.do?keyfrom=phporblog&key=100447363&type=data&doctype=json&version=1.1&q="+word.replace(" ", "%20");
        WebView webview = (WebView)findViewById(R.id.webviewdict);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);
    }

}
