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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class PostAdmin extends AppCompatActivity {
    ImageView imagePost;
    EditText nameM;
    EditText ComM;
    EditText howMenu;
    Button btnPost;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    EditText category;

    public static int RESULT_IMAGE = 1;
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_admin);

        imagePost = (ImageView) findViewById(R.id.image_post);
        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_IMAGE);
            }
        });
        nameM = (EditText) findViewById(R.id.name_menu);
        ComM = (EditText) findViewById(R.id.component_menu);
        howMenu = (EditText) findViewById(R.id.how_menu);
        category = (EditText) findViewById(R.id.category_menu);
        btnPost = (Button) findViewById(R.id.btn_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Food").push();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                imagePost.setImageBitmap(bitmap);
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
                                   String  name = nameM.getText().toString();
                                   String compo = ComM.getText().toString();
                                   String howmenu = howMenu.getText().toString();
                                   String cate = category.getText().toString();
                                   mData.child("ImageURL").setValue(downloadUrl);
                                   mData.child("name").setValue(name);
                                   mData.child("component").setValue(compo);
                                   mData.child("howmenu").setValue(howmenu);
                                   mData.child("category").setValue(cate);
                               }
                           });
                            progressDialog.dismiss();
                            Intent intent = new Intent(PostAdmin.this,AdminPage.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(PostAdmin.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostAdmin.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
}
