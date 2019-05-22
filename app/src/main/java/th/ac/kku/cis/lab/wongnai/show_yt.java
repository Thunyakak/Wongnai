package th.ac.kku.cis.lab.wongnai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class show_yt extends AppCompatActivity {
    WebView youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_yt);

        youtube = (WebView) findViewById(R.id.youtube);
        youtube.setWebViewClient(new WebViewClient());
        youtube.loadUrl("https://www.youtube.com/playlist?list=PLIct_7-s_ihElrMsRzlIX24dbCri0AvMe");
        youtube.getSettings().setJavaScriptEnabled(true);
    }
}
