package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import static com.example.myprojectwork.R.id.complaintoolbar;
public class Complain_Activity extends AppCompatActivity{

    private Toolbar complaintool;

    private Button foltbtn,submit;

    private RecyclerView chatrecview;
    private ComplaintRecAdapter chatAdapter;
    private ArrayList<Complaint> myComplainList;

    FirebaseUser fuserchat;
    DatabaseReference chatref,retuser;
    private ArrayList<String>UserList;
    String cmp;

    private EditText complaintmsg;

    private String mChatUser;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;
    User dukabo;
    private StorageReference mImageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_);

        Intent intent = getIntent();

        final String fName = intent.getStringExtra("class");

        logd("On Create of Complain activity");


        complaintool = (Toolbar) findViewById(complaintoolbar);
        foltbtn = (Button) findViewById(R.id.buttonff);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mImageStorage = FirebaseStorage.getInstance().getReference();



        setSupportActionBar(complaintool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        complaintool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Complain_Activity.this,MainActivity.class ));
                finish();
            }
        });



        foltbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Complain_Activity.this,New_complain_Activity.class );
                String value="class";
                i.putExtra(value,fName);
                startActivity(i);
                finish();
            }
        });


        chatrecview =(RecyclerView) findViewById(R.id.recycle_view_chat);
        chatrecview.setHasFixedSize(true);
        chatrecview.setLayoutManager(new LinearLayoutManager(this));

        myComplainList = new ArrayList<>();
        UserList = new ArrayList<>();

        chatref = FirebaseDatabase.getInstance().getReference().child("Complaints").child(mCurrentUserId);

        chatref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Complaint msg   = snapshot.getValue(Complaint.class);
                    String sk = snapshot.getKey();
                    msg.setKey(sk);

                    if(msg.getSender().equals(mCurrentUserId)){
                        myComplainList.add(msg);
                    }
                }

                logd("  "+  myComplainList.size());

                chatAdapter = new ComplaintRecAdapter(Complain_Activity.this,myComplainList);
                chatrecview.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void logd (String s)
    {
        android.app.AlertDialog.Builder mad=new AlertDialog.Builder(this);
        mad.setMessage(s);
        //mad.show();
    }


}
