package th.ac.kku.cis.lab.wongnai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class EditAdmin extends AppCompatActivity {
    ImageView img1;
    ImageView img2;
    EditText name_edt;
    EditText com_edt;
    EditText howMenu;
    Button edtBtn;
    DatabaseReference mData;
    FirebaseDatabase firebaseDatabase;

    public static int RESULT_IMAGE = 1;
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    String post_key;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);
        post_key =getIntent().getExtras().getString("PostKey");

        img1 = (ImageView) findViewById(R.id.image_edit1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_IMAGE);
            }
        });
        img2 = (ImageView) findViewById(R.id.image_edit2);
        img2.setVisibility(View.INVISIBLE);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_IMAGE);
            }
        });
        name_edt = (EditText) findViewById(R.id.name_menu_edit);
        com_edt = (EditText) findViewById(R.id.component_menu_edit);
        howMenu = (EditText) findViewById(R.id.how_menu_edit);
        edtBtn = (Button) findViewById(R.id.btn_edit);
        edtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Food").child(post_key);
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String img =  dataSnapshot.child("ImageURL").getValue().toString();
               String name = dataSnapshot.child("name").getValue().toString();
               String com = dataSnapshot.child("component").getValue().toString();
               String howmenu =  dataSnapshot.child("howmenu").getValue().toString();

               Picasso.with(getApplicationContext()).load(img).into(img1);
               name_edt.setText(name);
               com_edt.setText(com);
               howMenu.setText(howmenu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            img2.setVisibility(View.VISIBLE);
            img1.setVisibility(View.INVISIBLE);
            selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                img2.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void UploadImage(){
        if(selectedImage != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    String  name = name_edt.getText().toString();
                                    String compo = com_edt.getText().toString();
                                    String howmenu = howMenu.getText().toString();
                                    mData.child("ImageURL").setValue(downloadUrl);
                                    mData.child("name").setValue(name);
                                    mData.child("component").setValue(compo);
                                    mData.child("howmenu").setValue(howmenu);
                                }
                            });
                            progressDialog.dismiss();
                            Intent intent = new Intent(EditAdmin.this,AdminPage.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(EditAdmin.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditAdmin.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
        else{
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String  name = name_edt.getText().toString();
            String compo = com_edt.getText().toString();
            String howmenu = howMenu.getText().toString();
            mData.child("name").setValue(name);
            mData.child("component").setValue(compo);
            mData.child("howmenu").setValue(howmenu);
            progressDialog.dismiss();
            Intent intent = new Intent(EditAdmin.this,AdminPage.class);
            startActivity(intent);
            finish();
            Toast.makeText(EditAdmin.this, "Uploaded", Toast.LENGTH_SHORT).show();
        }
    }
}
