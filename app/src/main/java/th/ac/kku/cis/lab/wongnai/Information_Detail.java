package th.ac.kku.cis.lab.wongnai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Information_Detail extends AppCompatActivity {
    Button facebook;
    Button instagram;
    Button twitter;
    Button youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information__detail);

        facebook = (Button) findViewById(R.id.btn_fb);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent facebook = new Intent(Information_Detail.this, show_fb.class);
                startActivity(facebook);
            }
        });
        instagram = (Button) findViewById(R.id.btn_ig);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instagram = new Intent(Information_Detail.this, show_ig.class);
                startActivity(instagram);
            }
        });
        twitter = (Button) findViewById(R.id.btn_tw);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent twitter = new Intent(Information_Detail.this, show_tw.class);
                startActivity(twitter);
            }
        });
        youtube = (Button) findViewById(R.id.btn_yt);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent youtube = new Intent(Information_Detail.this, show_yt.class);
                startActivity(youtube);
            }
        });
    }
}
