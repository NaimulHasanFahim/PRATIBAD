package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile_Activity extends AppCompatActivity {

    private Toolbar editprotool;
    private DatabaseReference editdatabsref;
    private FirebaseUser editfirus;

    private TextView sname;
    private CircleImageView simage;

    private ProgressDialog mProgressDialog;


    private Button changeimg;

    private  static  final int GALLERY_PICK=1;


    private StorageReference mystorage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_);
    }

    /*
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_);

        editprotool = (Toolbar) findViewById(R.id.toolbaredit);

        sname =(TextView) findViewById(R.id.editnametxt);
        simage =(CircleImageView) findViewById(R.id.imgespro);

        changeimg = (Button) findViewById(R.id.changeimagebtn);

        mystorage = FirebaseStorage.getInstance().getReference();
        editfirus = FirebaseAuth.getInstance().getCurrentUser();

        setSupportActionBar(editprotool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editprotool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EditProfile_Activity.this,MainActivity.class ));
                finish();
            }
        });


        String eduid = editfirus.getUid();
        editdatabsref = FirebaseDatabase.getInstance().getReference().child("Users").child(eduid);
        editdatabsref .keepSynced(true);

        editdatabsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String edname = dataSnapshot.child("name").getValue().toString();
                String  edimage= dataSnapshot.child("image").getValue().toString();
                String edthimage = dataSnapshot.child("thumb_image").getValue().toString();
                String chmessage = dataSnapshot.child("messages").getValue().toString();

                sname.setText(edname);

                Picasso.get().load("edimage").into(simage);
                Picasso.get().setLoggingEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        changeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(EditProfile_Activity.this,"Chanege Image.",Toast.LENGTH_SHORT).show();

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent,"SELECT IMAGE"),GALLERY_PICK);


                // start picker to get image for cropping and then use the image in cropping activity
                //CropImage.activity()
                  //      .setGuidelines(CropImageView.Guidelines.ON)
                    //    .start(EditProfile_Activity.this);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK ){

            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setAspectRatio(1,1)
            .setMinCropWindowSize(500, 500).start(this);
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode==RESULT_OK){

                mProgressDialog = new ProgressDialog(EditProfile_Activity.this);
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setMessage("Please wait while we upload and process the image.");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                Uri resultUri = result.getUri();
                String eduid11 = editfirus.getUid();

                StorageReference filepath = mystorage.child("Profile_Images").child(eduid11+".jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){
                            final String download_url =  task.getResult().getMetadata().getReference().getDownloadUrl().toString();

                            editdatabsref.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mProgressDialog.dismiss();
                                }
                            });


                            Toast.makeText(EditProfile_Activity.this,"Working.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mProgressDialog.hide();
                            Toast.makeText(EditProfile_Activity.this,"Error in Uploading !.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
            else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                Exception error = result.getError();

            }

        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    */
}
