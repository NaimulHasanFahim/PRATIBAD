package com.example.myprojectwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private Button alredy,need,logadmn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        alredy = (Button) findViewById(R.id.alrd_acc);
        need = (Button) findViewById(R.id.need_acc);
        logadmn = (Button) findViewById(R.id.admn);

        alredy.setOnClickListener(this);
        need.setOnClickListener(this);
        logadmn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.alrd_acc){
            startActivity(new Intent(StartActivity.this,LoginActivity.class ));
            finish();
        }

        if(view.getId()==R.id.need_acc){
            startActivity(new Intent(StartActivity.this,SignUpActivity.class ));
            finish();
        }

        if(view.getId()==R.id.admn){
            startActivity(new Intent(StartActivity.this,Admin_login_Activity.class ));
            finish();
        }
    }
}
