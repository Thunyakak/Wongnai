package th.ac.kku.cis.lab.wongnai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class show_fb extends AppCompatActivity {
    WebView facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fb);

        facebook = (WebView) findViewById(R.id.facebook);
        facebook.setWebViewClient(new WebViewClient());
        facebook.loadUrl("https://www.facebook.com/Wongnai/");
        facebook.getSettings().setJavaScriptEnabled(true);
    }
}
