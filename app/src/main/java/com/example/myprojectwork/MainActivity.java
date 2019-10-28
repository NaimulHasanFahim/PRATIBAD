package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);


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
}
