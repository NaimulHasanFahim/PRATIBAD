package com.example.myprojectwork;

import android.app.AlertDialog;
import android.content.Context;
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


public class ComplainFragment extends Fragment {


    private RecyclerView chatrecview;
    private ComplainRecAdapterAdmin chatAdapter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complain, container, false);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mImageStorage = FirebaseStorage.getInstance().getReference();



        chatrecview =(RecyclerView) view.findViewById(R.id.comfragrecycle_view);
        chatrecview.setHasFixedSize(true);
        chatrecview.setLayoutManager(new LinearLayoutManager(getContext()));

        myComplainList = new ArrayList<>();
        UserList = new ArrayList<>();

        chatref = FirebaseDatabase.getInstance().getReference().child("Complaints");

        chatref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Complaint msg   = snapshot.getValue(Complaint.class);

                    if(msg.getReciever().equals(mCurrentUserId)){
                        myComplainList.add(msg);
                    }
                }

                //logd(" " + myComplainList.size());

                chatAdapter = new ComplainRecAdapterAdmin(getContext(),myComplainList);
                chatrecview.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }




}
