package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;

public class New_complain_Activity extends AppCompatActivity{


    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private EditText my_complaint;
    private ImageButton sendComp;
    String idd="";
    int num;

    private Toolbar nctool;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complain_);

        Intent ii = getIntent();
        final String fName = ii.getStringExtra("class");

        nctool = (Toolbar) findViewById(R.id.new_colm_toolbar);

        setSupportActionBar(nctool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nctool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(New_complain_Activity.this,Complain_Activity.class);
                String value="class";
                i.putExtra(value,fName);
                startActivity(i);
                finish();
            }
        });



        my_complaint = (EditText) findViewById(R.id.mycomplaint_msg);;
        sendComp =(ImageButton) findViewById(R.id.subbutton);

        sendComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser mus = FirebaseAuth.getInstance().getCurrentUser();
                String sen=mus.getUid().toString();

                String msg="";
                radioGroup = (RadioGroup) findViewById(R.id.radioid);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                if(!my_complaint.getText().equals("")){
                    msg = my_complaint.getText().toString();
                }
                if(radioButton.getText().equals("Vice chancellor")){
                    String rec="AGkytnQmgqZ89HT39wxRZu6gkds1";
                    sendMessage(sen,rec,msg,"Vice chancellor");
                }
                else if(radioButton.getText().equals("Proctor")){
                    String rec="SyJbf7khpXdN8ITN0BKFUxfYKI43";
                    sendMessage(sen,rec,msg,"Proctor");
                }
                else if(radioButton.getText().equals("Anti-harassment cell")){
                    String rec="kY8QjzQ5ojZkyBHcyPII04k1Yqm2";
                    sendMessage(sen,rec,msg,"Anti-harassment cell");
                }
                else if(radioButton.getText().equals("Adviser")){
                    String rec="xphHPATHpldaKkRukUtnsgmAdDo2";
                    sendMessage(sen,rec,msg,"Adviser");
                }
            }
        });

    }


    private void sendMessage(String sender,String reciever,String message,String toi){
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Complaints").child(uid);
        DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);


        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String username111 = dataSnapshot.child("complaintsNumber").getValue().toString();
                num=Integer.parseInt(username111);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        num++;

        HashMap<String,Object> myMap = new HashMap<>();

        myMap.put("sender",sender);
        myMap.put("reciever",reciever);
        myMap.put("message",message);
        myMap.put("status","pending");
        myMap.put("number",Integer.toString(num));
        myMap.put("to",toi);

        mRef.push().setValue(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(New_complain_Activity.this,"Your complaint has been recieved.",Toast.LENGTH_SHORT).show();
                my_complaint.setText("");
            }
        });
    }

}
