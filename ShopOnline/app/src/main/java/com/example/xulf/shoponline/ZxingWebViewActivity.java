package com.example.xulf.shoponline;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by XuLF on 2017/3/4.
 * 二维码扫描
 */
public class ZxingWebViewActivity extends AppCompatActivity {

    private WebView webView;

    private TextView textView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView=(WebView)findViewById(R.id.wbv_zxing);
        textView=(TextView)findViewById(R.id.tv_getContent);

        String zxinUrl=this.getIntent().getStringExtra("zxingurl");
        Log.i("122121",zxinUrl);
        textView.setText(zxinUrl);
        webView.loadUrl(URLUtil.guessUrl(zxinUrl));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
