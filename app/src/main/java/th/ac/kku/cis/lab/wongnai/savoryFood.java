package th.ac.kku.cis.lab.wongnai;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class savoryFood extends AppCompatActivity {
    Button btn_post;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Query mData;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savory_food);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btn_post = (Button) findViewById(R.id.btn_post_admin_1);
        btn_post.setVisibility(View.INVISIBLE);
        if(user != null){
            btn_post.setVisibility(View.VISIBLE);
        }
        else if(user == null){
            btn_post.setVisibility(View.INVISIBLE);
        }
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent post = new Intent(savoryFood.this,PostAdmin.class);
                startActivity(post);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_savory);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Food").orderByChild("category").equalTo("อาหารคาว");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(mData,Food.class)
                .build();

        FirebaseRecyclerAdapter<Food,Card_View> adapter = new FirebaseRecyclerAdapter<Food, Card_View>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Card_View holder, int position, @NonNull Food model) {
                final String post_key = getRef(position).getKey();

                holder.setImage(getApplicationContext(),model.getImageURL());
                holder.setName(model.getName());

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),DetailPage.class);
                        intent.putExtra("PostKey",post_key);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public Card_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
               View view = LayoutInflater.from(viewGroup.getContext())
                       .inflate(R.layout.content_card,viewGroup,false);
                return new Card_View(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class Card_View extends RecyclerView.ViewHolder{
        View mView;
        CardView cardView;

        public Card_View(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            cardView = (CardView) mView.findViewById(R.id.card_view);
        }
        public void setName(String name){
            TextView textView = (TextView) mView.findViewById(R.id.name_food_card);
            textView.setText(name);
        }
        public void setImage(Context context,String image){
            ImageView imageView = (ImageView) mView.findViewById(R.id.img_food);
            Picasso.with(context).load(image).into(imageView);
        }
    }
}
