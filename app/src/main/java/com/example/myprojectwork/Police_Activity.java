package com.example.myprojectwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Police_Activity extends AppCompatActivity {

    private Toolbar mytoolpolice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_);

        mytoolpolice = (Toolbar) findViewById(R.id.policetoolbar);

        setSupportActionBar(mytoolpolice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolpolice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Police_Activity.this,MainActivity.class ));
                finish();
            }
        });
    }
}
