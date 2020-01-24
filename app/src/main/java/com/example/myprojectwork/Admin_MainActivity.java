package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_MainActivity extends AppCompatActivity {

    private DrawerLayout dladmin;
    private ActionBarDrawerToggle abdtadmin;
    private FirebaseAuth mAuthadmin;
    ImageView navhimg;
    DatabaseReference mRoot;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__main);


        navhimg =(ImageView) findViewById(R.id.adminnavimage);

        mAuthadmin = FirebaseAuth.getInstance();


        userid = mAuthadmin.getCurrentUser().getUid();

        mRoot = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        mRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user.getImageURL().equals("default")) {
                    navhimg.setImageResource(R.mipmap.ic_launcher);
                } else {
                    //Glide.with(Admin_MainActivity.this).load(user.getImageURL()).into(navhimg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

                if(id==R.id.lawadmin){

                    Toast.makeText(Admin_MainActivity.this,"User Clicked On the Complain Box.",Toast.LENGTH_SHORT).show();

                }
                if(id==R.id.policeadmin){
                    startActivity(new Intent(Admin_MainActivity.this,Admin_Police_Activity.class ));
                    finish();

                }
                if(id==R.id.logmoutadmin){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Admin_MainActivity.this,StartActivity.class ));
                    finish();
                    Toast.makeText(Admin_MainActivity.this,"User Clicked On the Logout button.",Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });



        TabLayout tabLayout =(TabLayout) findViewById(R.id.tab_lay);
        ViewPager viewPager =(ViewPager) findViewById(R.id.viewpage);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ChatsFragment(),"Chats");
        viewPagerAdapter.addFragment(new ComplainFragment(),"Complaints");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
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


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String>titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            //logd("Inside Fragment get Item");
            return fragments.get(position);
        }

        @Override
        public int getCount() {

            // logd("Inside Fragment get count "+fragments.size());
            return fragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }



}
