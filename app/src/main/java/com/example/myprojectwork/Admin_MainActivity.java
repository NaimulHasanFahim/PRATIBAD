package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin_MainActivity extends AppCompatActivity {

    private DrawerLayout dladmin;
    private ActionBarDrawerToggle abdtadmin;
    private FirebaseAuth mAuthadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__main);


        mAuthadmin = FirebaseAuth.getInstance();

        dladmin = (DrawerLayout) findViewById(R.id.drwadmin);
        abdtadmin = new ActionBarDrawerToggle(this,dladmin, R.string.Open, R.string.Close);
        abdtadmin.setDrawerIndicatorEnabled(true);
        dladmin.addDrawerListener(abdtadmin);
        abdtadmin.syncState();
        NavigationView navviewadmin = (NavigationView) findViewById(R.id.nav_viewadmin);
        navviewadmin.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();

                if(id==R.id.complainboxadmin){
                    Toast.makeText(Admin_MainActivity.this,"User Clicked On the Complain Box.",Toast.LENGTH_SHORT).show();

                }
                if(id==R.id.policeadmin){
                    Toast.makeText(Admin_MainActivity.this,"User Clicked On the police Button.",Toast.LENGTH_SHORT).show();

                }
                if(id==R.id.logmoutadmin){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Admin_MainActivity.this,StartActivity.class ));
                    finish();
                    Toast.makeText(Admin_MainActivity.this,"User Clicked On the Logout button.",Toast.LENGTH_SHORT).show();
                }
                if(id==R.id.graphadmin){
                    Toast.makeText(Admin_MainActivity.this,"User Clicked On the graph button.",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdtadmin.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuthadmin.getCurrentUser();

        if(currentUser==null){
            Intent In1 = new Intent(Admin_MainActivity.this,StartActivity.class);
            startActivity(In1);
            finish();
        }
    }
}
