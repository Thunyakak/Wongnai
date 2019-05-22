package th.ac.kku.cis.lab.wongnai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Page extends AppCompatActivity {

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_page);

        linearLayout1 = (LinearLayout) findViewById(R.id.se_food);
        linearLayout2 = (LinearLayout) findViewById(R.id.sweet_food);
        linearLayout3 = (LinearLayout) findViewById(R.id.drink_food);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent se = new Intent(Main2Page.this,savoryFood.class);
                startActivity(se);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sweet = new Intent(Main2Page.this,SweetFood.class);
                startActivity(sweet);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drink = new Intent(Main2Page.this,DrinkFood.class);
                startActivity(drink);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent = new Intent(Main2Page.this,MainActivity.class);
                        startActivity(intent);

                        return true;
                    case R.id.navigation_detail:
                        Intent detail = new Intent(Main2Page.this,Information_Detail.class);
                        startActivity(detail);

                        return true;
                    case R.id.navigation_back:
                        Intent intent1 = new Intent(Main2Page.this,MainActivity.class);
                        startActivity(intent1);

                        return true;
                }
                return false;
            }
        });
    }
}
