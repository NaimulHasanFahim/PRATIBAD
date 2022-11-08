package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.os.PersistableBundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.example.myprojectwork.R.*;
import static com.example.myprojectwork.R.layout.*;


public class MainActivity extends AppCompatActivity {

    private Location location;
    private LocationResult currentLocation;
    private String s = "";
    TextView textView;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private static final int Request_code = 101;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1, MY_CALL_PERMISSION_CODE = 1 ;

    DatabaseReference mRoot;
    private String userid;
    FirebaseAuth mAuth;
    ImageView navhimg;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private String numberofcomp;

    private Button button,sndB,complainbutton,chatbutton,policebutton,lawbutton;
    TextView userName,numCom,laststatus,actionNum;

    public static Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        mAuth = FirebaseAuth.getInstance();
        // navhimg =(ImageView) findViewById(R.id.usernavimage);
        FirebaseUser currentUser11 = mAuth.getCurrentUser();

        /*
        if(!currentUser11.getUid().equals(null))
        {
            userid = currentUser11.getUid();
            mRoot = FirebaseDatabase.getInstance().getReference("Users").child(userid);
            mRoot.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    if (user.getImageURL().equals("default")) {
                        navhimg.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        //Glide.with(MainActivity.this).load(user.getImageURL()).into(navhimg);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        */



        myContext = getApplicationContext();

        /// alert
        button = (Button) findViewById(id.alertbutton);
        policebutton = (Button) findViewById(id.policehelp);
        complainbutton= (Button) findViewById(id.givencomp);
        chatbutton = (Button) findViewById(id.chatsuser);
        lawbutton = (Button) findViewById(id.lawhelp);


        userName =(TextView) findViewById(id.username1);
        numCom =(TextView) findViewById(id.compgiven);
        laststatus =(TextView) findViewById(id.lastcompstatus);
        actionNum =(TextView) findViewById(id.actaken);

        //Updatedata();

        complainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Complain_Activity.class);
                String value="class";
                i.putExtra(value,"User");
                startActivity(i);
                finish();
            }
        });

        policebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Police_Activity.class ));
                finish();
            }
        });
        lawbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserLawActivity.class ));
                finish();
            }
        });
        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UsersChatsActivity.class ));
                finish();
            }
        });



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fetchlastLocation();

                SmsManager smsManager = SmsManager.getDefault();
                try {if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    smsManager.sendTextMessage("0183222641", null, "Please Help Me. My Location is Latitude = " + currentLocation.getLastLocation().getLatitude() + " && longtitude =  " + currentLocation.getLastLocation().getLongitude(), null, null);
                    Toast.makeText(getApplicationContext(), "SMS SENT", Toast.LENGTH_SHORT).show();
                } else {
                    request_permission();

                }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "turn on location", Toast.LENGTH_SHORT).show();
                    fetchlastLocation();
                }
            }


        });




        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent alert_intent = new Intent(Intent.ACTION_CALL);
                alert_intent.setData(Uri.parse("tel:0152153252"));


                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(alert_intent);

                    return true;
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CALL_PHONE}, MY_CALL_PERMISSION_CODE);
                    return false;
                }
            }
        });



        dl = (DrawerLayout) findViewById(id.drw);
        logd(dl.toString());
        abdt = new ActionBarDrawerToggle(this,dl, string.Open, string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        NavigationView navview = (NavigationView) findViewById(id.nav_view);
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();


                if(id==R.id.logmout){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,StartActivity.class ));
                    finish();
                    Toast.makeText(MainActivity.this,"User Clicked On the Logout button.",Toast.LENGTH_SHORT).show();
                }

                if(id==R.id.edit){
                    startActivity(new Intent(MainActivity.this,EditProfile_Activity.class ));
                    finish();
                    Toast.makeText(MainActivity.this,"User Clicked On the edit profile button.",Toast.LENGTH_SHORT).show();

                }

                if(id==R.id.addtrusted){
                    startActivity(new Intent(MainActivity.this,Users_Activity.class ));
                    finish();
                    Toast.makeText(MainActivity.this,"User Clicked On the Add trusted friend button.",Toast.LENGTH_SHORT).show();

                }




                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null) {
            Intent In1 = new Intent(MainActivity.this, StartActivity.class);
            startActivity(In1);
            finish();
        }
        else{
            if(currentUser.getUid().equals("AGkytnQmgqZ89HT39wxRZu6gkds1")){
                Intent In1 = new Intent(MainActivity.this,Admin_MainActivity.class);
                startActivity(In1);
                finish();
            }
        }
    }
    private void request_permission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.SEND_SMS)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("You need permission for sending massage.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);

                        }
                    })
                    .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS)
        {
            if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, " ",Toast.LENGTH_SHORT).show();
    }
    private void fetchlastLocation()
    {

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED  ) {

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]
                                {Manifest.permission.ACCESS_FINE_LOCATION},
                        Request_code);
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
                        Request_code);
                return;
            }
        }else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            locationRequest = new LocationRequest();

            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setFastestInterval(200);
            locationRequest.setInterval(500);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    currentLocation = locationResult;

                    return;
                }
            }, getMainLooper());

        }
    }



    private void Updatedata( ){
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
//        if(current_user== null) {
//            setContentView(R.layout.activity_login);
//            return;
//        }
        String uid = current_user.getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Complaints").child(uid);
        DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String username111 = dataSnapshot.child("username").getValue().toString();
                userName.setText(username111);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count=0,tot=0;
                String str="pending";
                String[] countryNames = new String[100];

                boolean ek=true;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String complnst = snapshot.child("status").getValue().toString();
                    String checknum = snapshot.child("number").getValue().toString();
                    tot++;
                    if(complnst.equals("Action Taken")){
                        count++;
                    }
                    countryNames[Integer.parseInt(checknum)]=complnst;

                }
                //logd(count+"  "+tot);

                String yy1 = (String) numCom.getText();
                numCom.setText(yy1 + tot);
                String yy2 = (String) actionNum.getText();
                actionNum.setText(yy2 + count);
                laststatus.setText(laststatus.getText()+countryNames[tot]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void logd (String s)
    {
        android.app.AlertDialog.Builder mad=new android.app.AlertDialog.Builder(this);
        mad.setMessage(s);
        //mad.show();
        //String number = "10";
        //int result = ;
    }

}