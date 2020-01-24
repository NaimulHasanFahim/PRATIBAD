package com.example.myprojectwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MessageActivity extends AppCompatActivity {

    // CircleImageView profile_image;
    // TextView username;

    FirebaseUser fuser;
    DatabaseReference mRoot;
    Intent intent;

    ImageButton send_button;
    EditText sendmsg;

    MessageAdapter messageAdapter;
    ArrayList<Chats>mchats;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar =(Toolbar) findViewById(R.id.msgtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this,MainActivity.class ));
                finish();
            }
        });

        //profile_image =(CircleImageView) findViewById(R.id.profile_imgmsg);
        //username =(TextView) findViewById(R.id.usermsg);

        send_button =(ImageButton) findViewById(R.id.btn_send);
        sendmsg =(EditText) findViewById(R.id.text_send);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        recyclerView =(RecyclerView) findViewById(R.id.recycle_view_msg);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        mRoot = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = sendmsg.getText().toString();

                if(!msg.equals("")){
                    sendMessage(fuser.getUid(),userid,msg);
                }
                sendmsg.setText("");

            }
        });


        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user1 = dataSnapshot.getValue(User.class);

                //username.setText(user.getUsername());

                //if(user.getImageURL().equals("default")){
                // profile_image.setImageResource(R.mipmap.ic_launcher);
                // }
                // else{
                //Glide.with(MessageActivity.this).load(user.getImageURL()).into(profile_image);
                //}

                String s="";

                readMessage(fuser.getUid(),userid,s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage(String sender,String reciever,String message){

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object>myMap = new HashMap<>();

        myMap.put("sender",sender);
        myMap.put("reciever",reciever);
        myMap.put("message",message);

        mRef.child("Chats").push().setValue(myMap);
    }

    private void readMessage(final String myid, final String userid, final String Imageurl){

        mchats = new ArrayList<>();
        mRoot = FirebaseDatabase.getInstance().getReference().child("Chats");

        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mchats.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Chats chats = dataSnapshot1.getValue(Chats.class);

                    if(chats.getReciever().equals(myid) && chats.getSender().equals(userid) || chats.getReciever().equals(userid) && chats.getSender().equals(myid)){
                        mchats.add(chats);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this,mchats,Imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
