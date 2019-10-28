package com.example.myprojectwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static com.example.myprojectwork.R.id.complaintoolbar;
public class Complain_Activity extends AppCompatActivity {

    private Toolbar complaintool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_);


        complaintool = (Toolbar) findViewById(complaintoolbar);

        setSupportActionBar(complaintool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        complaintool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Complain_Activity.this,MainActivity.class ));
                finish();
            }
        });
    }
}
