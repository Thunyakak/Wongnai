package th.ac.kku.cis.lab.wongnai;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Comment_page extends AppCompatActivity {
    EditText comment;
    Button comment_submit;
    RecyclerView recyclerView;
    Query mData;
    DatabaseReference mUser;
    FirebaseDatabase firebaseDatabase;
    String post_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);
        post_key =getIntent().getExtras().getString("PostKey");

        comment = (EditText) findViewById(R.id.edt_comment);
        comment_submit = (Button) findViewById(R.id.btn_comment_submit);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_comment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Comment").child(post_key);
        mUser = firebaseDatabase.getReference("Comment").child(post_key).push();

        comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String commn = comment.getText().toString();
                mUser.child("comment").setValue(commn);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<showComment> options = new FirebaseRecyclerOptions.Builder<showComment>()
                .setQuery(mData,showComment.class)
                .build();

        FirebaseRecyclerAdapter<showComment,Card_View> adapter = new FirebaseRecyclerAdapter<showComment, Card_View>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Card_View holder, int position, @NonNull showComment model) {
                 holder.setComm(model.getComment());
            }
            @NonNull
            @Override
            public Card_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.card_comment,viewGroup,false);
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
            cardView = (CardView) mView.findViewById(R.id.card_comm);
        }
        public void setComm(String comm){
            TextView commSet = (TextView) mView.findViewById(R.id.text_comm);
            commSet.setText(comm);
        }
    }
}
