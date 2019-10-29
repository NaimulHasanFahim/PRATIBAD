package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.myprojectwork.R.*;
import static com.example.myprojectwork.R.layout.*;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    private FirebaseAuth mAuth;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1, MY_CALL_PERMISSION_CODE = 1 ;
    private Button button,sndB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        /// alert
        button =  findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                {
                    smsManager.sendTextMessage("0183222641",null,"I am in danger",null,null);
                    Toast.makeText(getApplicationContext(),"SMS SENT",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    request_permission();

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

        mAuth = FirebaseAuth.getInstance();

        dl = (DrawerLayout) findViewById(id.drw);
        abdt = new ActionBarDrawerToggle(this,dl, string.Open, string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        NavigationView navview = (NavigationView) findViewById(id.nav_view);
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();

                if(id==R.id.complainbox){
                    Toast.makeText(MainActivity.this,"User Clicked On the Complain Box.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Complain_Activity.class ));
                    finish();
                }
                if(id==R.id.police){
                    Toast.makeText(MainActivity.this,"User Clicked On the police Button.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Police_Activity.class ));
                    finish();
                }
                if(id==R.id.logmout){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,StartActivity.class ));
                    finish();
                    Toast.makeText(MainActivity.this,"User Clicked On the Logout button.",Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.graph){
                    Toast.makeText(MainActivity.this,"User Clicked On the graph button.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,GraphShow_Activity.class ));
                    finish();
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

        if(currentUser==null){
            Intent In1 = new Intent(MainActivity.this,StartActivity.class);
            startActivity(In1);
            finish();
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
            Toast.makeText(this, "Denied",Toast.LENGTH_SHORT).show();
    }

}
