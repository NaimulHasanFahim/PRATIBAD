package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import java.util.Map;


public class UsersChatsActivity extends AppCompatActivity {

    private Toolbar userchatstool;

    private RecyclerView userchatrecview;
    private RecycleViewAdpter userchatadapter;
    private ArrayList<User> userchatUserList,userchatstust;

    FirebaseUser userchatsfuserchat;
    DatabaseReference userchatschatref,userchatsretuser;
    private ArrayList<String>UserList;
    String cmp;

    private String mChatUser;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;
    User dukabo;
    private StorageReference mImageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_chats);


        userchatstool = (Toolbar) findViewById(R.id.userchatstoolbar);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mImageStorage = FirebaseStorage.getInstance().getReference();



        setSupportActionBar(userchatstool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chats");


        userchatstool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UsersChatsActivity.this,MainActivity.class ));
                finish();
            }
        });


        userchatrecview =(RecyclerView) findViewById(R.id.UserChatrecycle_view_chat);
        userchatrecview.setHasFixedSize(true);
        userchatrecview.setLayoutManager(new LinearLayoutManager(this));

        userchatUserList = new ArrayList<>();
        UserList = new ArrayList<>();
        userchatstust = new ArrayList<>();
        dukabo = new User();

        userchatschatref = FirebaseDatabase.getInstance().getReference().child("Chats");
        userchatsretuser = FirebaseDatabase.getInstance().getReference().child("Users");
        final Map<String,Integer> countmap = new HashMap<>();
        final Map<String,Integer> countmap1 = new HashMap<>();



        userchatsretuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                   ///logd(user.getUsername());
                    userchatstust.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userchatschatref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userchatUserList.clear();
                //logd("Inside the loop");
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageId msgid = snapshot.getValue(MessageId.class);

                    if(countmap1.get(msgid.getReciever())==null){//&& (msgid.getReciever().equals(mCurrentUserId)||msgid.getReciever().equals(mCurrentUserId))){
                        UserList.add(msgid.getReciever());

                        countmap1.put(msgid.getReciever(),1);
                    }
                    if(countmap1.get(msgid.getSender())==null){//&& (msgid.getSender().equals(mCurrentUserId)||msgid.getSender().equals(mCurrentUserId))){
                        UserList.add(msgid.getSender());
                        countmap1.put(msgid.getSender(),1);
                    }
                }


                String ck = mCurrentUserId;

                for (String id:UserList){

                    if(countmap.get(id)==null&&!id.equals(ck)){
                        countmap.put(id,1);
                        for(User mmm : userchatstust){
                            if(mmm.getUid().equals(id)){
                                System.out.println(mmm.getUid()+"  "+id);
                                userchatUserList.add(mmm);
                            }
                        }
                    }
                }


                userchatadapter =new RecycleViewAdpter(UsersChatsActivity.this,userchatUserList);
                userchatrecview.setAdapter(userchatadapter);

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
