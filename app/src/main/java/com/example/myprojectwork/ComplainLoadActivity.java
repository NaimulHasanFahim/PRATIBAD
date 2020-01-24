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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class ComplainLoadActivity extends AppCompatActivity {



    FirebaseUser fuser;
    DatabaseReference mRoot;
    Intent intent;

    ImageButton send_button;
    EditText sendmsg;
    String title="";

    MessageAdapter messageAdapter;
    ArrayList<Chats>mchats;

    TextView msgtext,status;

    String keys="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_load);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            keys =(String) b.get("key");
        }

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbarcmp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComplainLoadActivity.this,Complain_Activity.class ));
                finish();
            }
        });


        intent = getIntent();
        final String userid = intent.getStringExtra("userid");

        //recyclerView =(RecyclerView) findViewById(R.id.recycle_view_cmp);
        //recyclerView.setHasFixedSize(true);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setStackFromEnd(true);
        //recyclerView.setLayoutManager(linearLayoutManager);

        msgtext =(TextView) findViewById(R.id.textView11);
        status =(TextView) findViewById(R.id.textViewstatus);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        mRoot = FirebaseDatabase.getInstance().getReference("Complaints");

        String s="";

        readMessage(fuser.getUid(),s);
    }




    private void readMessage(final String userid, final String Imageurl){

        mchats = new ArrayList<>();
        mRoot = FirebaseDatabase.getInstance().getReference().child("Complaints").child(fuser.getUid()).child(keys);

        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mchats.clear();

                String reciever = dataSnapshot.child("reciever").getValue().toString();
                title=reciever;
                String sender = dataSnapshot.child("sender").getValue().toString();
                String message = dataSnapshot.child("message").getValue().toString();
                String statusstr = dataSnapshot.child("status").getValue().toString();
                status.setText(statusstr);
                logd(message);
                //Chats chats = new Chats(reciever,sender,message);
                 msgtext.setText(message);
                //mchats.add(chats);
                //messageAdapter = new MessageAdapter(ComplainLoadActivity.this,mchats,Imageurl);
                //recyclerView.setAdapter(messageAdapter);

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
