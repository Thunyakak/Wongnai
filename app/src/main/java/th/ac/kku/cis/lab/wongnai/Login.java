package th.ac.kku.cis.lab.wongnai;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    EditText in_email;
    EditText in_pass;
    Button login_btn;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        in_email = (EditText) findViewById(R.id.edit_email);
        in_pass = (EditText) findViewById(R.id.edit_pass);
        login_btn = (Button) findViewById(R.id.btn_login);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = in_email.getText().toString();
                String pass = in_pass.getText().toString();
                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Login.this,"Field is empty !!!",Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(task.isSuccessful()){
                                         Intent intent = new Intent(Login.this,AdminPage.class);
                                         startActivity(intent);
                                     }
                                     else{
                                         Toast.makeText(Login.this,"Login error !!!",Toast.LENGTH_LONG).show();
                                     }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user != null){
            Intent intent = new Intent(Login.this,AdminPage.class);
            startActivity(intent);
        }
    }
}
