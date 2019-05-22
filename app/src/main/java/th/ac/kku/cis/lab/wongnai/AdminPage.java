package th.ac.kku.cis.lab.wongnai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminPage extends AppCompatActivity {
    LinearLayout food_se;
    LinearLayout food_sweet;
    LinearLayout food_drink;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        food_se = (LinearLayout) findViewById(R.id.se_food);
        food_sweet = (LinearLayout) findViewById(R.id.sweet_food);
        food_drink = (LinearLayout) findViewById(R.id.drink_food);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        food_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent sefood = new Intent(AdminPage.this,savoryFood.class);
              startActivity(sefood);
              finish();
            }
        });
        food_sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sweetfood = new Intent(AdminPage.this,SweetFood.class);
                startActivity(Sweetfood);
                finish();
            }
        });
        food_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drinkfood = new Intent(AdminPage.this,DrinkFood.class);
                startActivity(drinkfood);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_menu:
                mAuth.signOut();
                Intent intent = new Intent(AdminPage.this,MainActivity.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
