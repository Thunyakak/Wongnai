package th.ac.kku.cis.lab.wongnai;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailPage extends AppCompatActivity {
    String post_key;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ImageView imgDetail;
    EditText name;
    EditText com;
    EditText how;
    Button btnEdit;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        post_key =getIntent().getExtras().getString("PostKey");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Food").child(post_key);

        imgDetail = (ImageView) findViewById(R.id.image_detail);
        name = (EditText) findViewById(R.id.name_menu_detail);
        com = (EditText) findViewById(R.id.component_menu_detail);
        how = (EditText) findViewById(R.id.how_menu_detail);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnEdit.setVisibility(View.INVISIBLE);
        if (user != null){
            btnEdit.setVisibility(View.VISIBLE);
        }
        else if(user == null){
            btnEdit.setVisibility(View.INVISIBLE);
        }
        Button btn_Com = (Button) findViewById(R.id.btn_comment);
        btn_Com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailPage.this,Comment_page.class);
                intent.putExtra("PostKey",post_key);
                startActivity(intent);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailPage.this,EditAdmin.class);
                intent.putExtra("PostKey",post_key);
                startActivity(intent);
            }
        });
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String img =  dataSnapshot.child("ImageURL").getValue().toString();
                String name1 = dataSnapshot.child("name").getValue().toString();
                String com2 = dataSnapshot.child("component").getValue().toString();
                String howmenu3 =  dataSnapshot.child("howmenu").getValue().toString();

                Picasso.with(getApplicationContext()).load(img).into(imgDetail);
                name.setText(name1);
                com.setText(com2);
                how.setText(howmenu3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
