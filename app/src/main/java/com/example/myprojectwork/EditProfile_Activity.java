package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile_Activity extends AppCompatActivity {


    private Uri filePath;

    private Toolbar editprotool;
    private Button changeimg;
    private TextView userName;
    private CircleImageView userProfileImage;
    private String currentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private static final int GalleryPick = 1;
    private StorageReference UserProfileImagesRef;

    private Uri myimagruri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_);
        //logd("On Create");

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile_Images");


        editprotool = (Toolbar) findViewById(R.id.toolbaredit);

        setSupportActionBar(editprotool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editprotool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditProfile_Activity.this,MainActivity.class ));
                finish();
            }
        });
        InitializeFields();


        changeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logd("Inside Change Image");Intent galleryIntent = new Intent();
                chooseImage();
                //uploadImage();
            }
        });

        RetrieveUserInfo();


    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),GalleryPick);
    }

    private void RetrieveUserInfo() {
       // logd("Inside Retrive User");

        RootRef.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                logd(retrieveProfileImage);
                final StorageReference reff = UserProfileImagesRef.child(currentUserId+".jpg");


                ///Glide.with(EditProfile_Activity.this)
                   ///     .load(reff)
                      //  .into(userProfileImage);

                userName.setText(retrieveUserName);
                Picasso.get().load(retrieveProfileImage).into(userProfileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    private void InitializeFields() {
        //logd("Inside Initialize Field");
        userName =(TextView) findViewById(R.id.editnametxt);
        userProfileImage = (CircleImageView) findViewById(R.id.imgespro);
        changeimg =(Button) findViewById(R.id.changebutton);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //logd("Inside On Activity Resul");


        if(requestCode == GalleryPick && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),filePath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataa = baos.toByteArray();

                final StorageReference reff = UserProfileImagesRef.child(currentUserId+".jpg");
                UploadTask uploadTask = reff.putBytes(dataa);





                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL

                        return reff.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            logd(""+downloadUri);
                            RootRef.child("Users").child(currentUserId).child("image").setValue(downloadUri.toString());


                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });

                userProfileImage.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public void logd (String s)
    {
        AlertDialog.Builder mad=new AlertDialog.Builder(this);
        mad.setMessage(s);
        mad.show();
    }


}
