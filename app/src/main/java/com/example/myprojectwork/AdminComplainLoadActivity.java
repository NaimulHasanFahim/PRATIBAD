package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminComplainLoadActivity extends AppCompatActivity {



    FirebaseUser fuser;
    DatabaseReference mRoot;
    Intent intent;

    ImageButton send_button;
    EditText sendmsg;

    MessageAdapter messageAdapter;
    ArrayList<Chats> mchats;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complain_load);


        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbarcmp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminComplainLoadActivity.this,Admin_MainActivity.class ));
                finish();
            }
        });


        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        recyclerView =(RecyclerView) findViewById(R.id.recycle_view_cmp);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        mRoot = FirebaseDatabase.getInstance().getReference("Complaints");

        String s="";

        readMessage(fuser.getUid(),s);
    }




    private void readMessage(final String userid, final String Imageurl){

        mchats = new ArrayList<>();
        mRoot = FirebaseDatabase.getInstance().getReference().child("Complaints");

        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mchats.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Chats chats = dataSnapshot1.getValue(Chats.class);

                    if(chats.getSender().equals(userid) || chats.getReciever().equals(userid)){
                        mchats.add(chats);

                    }
                    messageAdapter = new MessageAdapter(AdminComplainLoadActivity.this,mchats,Imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
