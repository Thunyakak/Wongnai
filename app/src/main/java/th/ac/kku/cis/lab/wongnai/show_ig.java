package th.ac.kku.cis.lab.wongnai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class show_ig extends AppCompatActivity {
    WebView instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ig);

        instagram = (WebView) findViewById(R.id.instagram);
        instagram.setWebViewClient(new WebViewClient());
        instagram.loadUrl("https://www.instagram.com/wongnai/?hl=th");
        instagram.getSettings().setJavaScriptEnabled(true);
    }
}
