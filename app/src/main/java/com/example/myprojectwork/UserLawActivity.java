package com.example.myprojectwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserLawActivity extends AppCompatActivity {

    
    private Toolbar lawtool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_law);


        lawtool = (Toolbar) findViewById(R.id.lawtoolbar);
        setSupportActionBar(lawtool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lawtool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLawActivity.this,MainActivity.class ));
                finish();
            }
        });
    }
}
