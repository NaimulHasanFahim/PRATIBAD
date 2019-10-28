package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText useremails,suserpass,username,def;
    private Button signupbtn;
    private FirebaseAuth mAuth;
    private ProgressDialog signprogress;
    private Toolbar signUptool;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        username = (EditText) findViewById(R.id.myname);
        useremails = (EditText) findViewById(R.id.signemail);
        suserpass = (EditText) findViewById(R.id.signpassword);
        signupbtn = (Button) findViewById(R.id.signbutton);

        signprogress = new ProgressDialog(this);
        signupbtn.setOnClickListener(this);

        signUptool = findViewById(R.id.signuptoolbar);
        setSupportActionBar(signUptool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signUptool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,StartActivity.class ));
                finish();
            }
        });


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.signbutton) {
            String email = useremails.getText().toString().trim();
            String password = suserpass.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            }

            signprogress.setTitle("Registering user.");
            signprogress.setMessage("Please wait whie we create your account !");
            signprogress.setCanceledOnTouchOutside(false);
            signprogress.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = current_user.getUid();

                                String usernames = username.getText().toString().trim();

                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                                HashMap<String,String> userMap = new HashMap<>();

                                userMap.put("name",usernames);
                                userMap.put("image","default");
                                userMap.put("thumb_image","default");

                                mDatabase.setValue(userMap);


                                signprogress.dismiss();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent mainintent = new Intent(SignUpActivity.this, MainActivity.class);
                                mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainintent);
                                finish();

                            } else {
                                signprogress.hide();
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
