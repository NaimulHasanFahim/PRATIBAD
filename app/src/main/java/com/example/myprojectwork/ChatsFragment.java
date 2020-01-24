package com.example.myprojectwork;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ChatsFragment extends Fragment {


    private RecyclerView adminchatrecview;
    private RecycleViewAdpterAdmin adminchatadapter;
    private ArrayList<User> adminchatUserList,adminchatstust;

    DatabaseReference adminchatschatref,adminchatsretuser;
    private ArrayList<String>UserList;
    String cmp;

    private String adminmChatUser;
    private DatabaseReference adminmRootRef;
    private FirebaseAuth adminmAuth;
    private String adminmCurrentUserId;

    private StorageReference mImageStorage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);


        adminmRootRef = FirebaseDatabase.getInstance().getReference();
        adminmAuth = FirebaseAuth.getInstance();
        adminmCurrentUserId = adminmAuth.getCurrentUser().getUid();

        mImageStorage = FirebaseStorage.getInstance().getReference();



        adminchatrecview =(RecyclerView) view.findViewById(R.id.chatfragrecycle_view);
        adminchatrecview.setHasFixedSize(true);
        adminchatrecview.setLayoutManager(new LinearLayoutManager(getContext()));

        adminchatUserList = new ArrayList<>();
        UserList = new ArrayList<>();
        adminchatstust = new ArrayList<>();

        adminchatschatref = FirebaseDatabase.getInstance().getReference().child("Chats");
        adminchatsretuser = FirebaseDatabase.getInstance().getReference().child("Users");
        final Map<String,Integer> countmap = new HashMap<>();
        final Map<String,Integer> countmap1 = new HashMap<>();



        adminchatsretuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    adminchatstust.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adminchatschatref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adminchatUserList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageId msgid = snapshot.getValue(MessageId.class);

                    if(countmap1.get(msgid.getReciever())==null){//&& (msgid.getReciever().equals(adminmCurrentUserId)||msgid.getReciever().equals(adminmCurrentUserId))){
                        UserList.add(msgid.getReciever());

                        countmap1.put(msgid.getReciever(),1);
                    }
                    if(countmap1.get(msgid.getSender())==null){//&& (msgid.getSender().equals(adminmCurrentUserId)||msgid.getSender().equals(adminmCurrentUserId))){
                        UserList.add(msgid.getSender());
                        countmap1.put(msgid.getSender(),1);
                    }
                }


                String ck = adminmCurrentUserId.toString();

                for (String id:UserList){

                    if(countmap.get(id)==null&&!id.equals(ck)){
                        countmap.put(id,1);

                        for(User mmm : adminchatstust){
                            if(mmm.getUid().equals(id)){
                                System.out.println(mmm.getUid()+"  "+id);
                                adminchatUserList.add(mmm);
                            }
                        }
                    }


                }

                //logd("  "+adminchatUserList.size());
                adminchatadapter =new RecycleViewAdpterAdmin(getContext(),adminchatUserList);
                adminchatrecview.setAdapter(adminchatadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    public void logd (String s)
    {
        android.app.AlertDialog.Builder mad=new AlertDialog.Builder(getContext());
        mad.setMessage(s);
        //mad.show();
    }

}
