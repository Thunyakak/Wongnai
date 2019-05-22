package th.ac.kku.cis.lab.wongnai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class show_tw extends AppCompatActivity {
    WebView twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tw);

        twitter = (WebView) findViewById(R.id.twitter);
        twitter.setWebViewClient(new WebViewClient());
        twitter.loadUrl("https://twitter.com/wongnai");
        twitter.getSettings().setJavaScriptEnabled(true);
    }
}
