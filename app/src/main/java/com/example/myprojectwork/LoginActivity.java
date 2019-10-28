package com.example.myprojectwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText useremail,userpass;
    private Button logbtn;
    private FirebaseAuth mAuth;
    private ProgressDialog loginprogress;
    private Toolbar mytool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        useremail = (EditText) findViewById(R.id.youremail);
        userpass = (EditText) findViewById(R.id.logpassword);
        logbtn = (Button) findViewById(R.id.logbutton);

        logbtn.setOnClickListener(this);

        loginprogress = new ProgressDialog(this);

        mytool = (Toolbar) findViewById(R.id.logintoolbar);
        setSupportActionBar(mytool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,StartActivity.class ));
                finish();
            }
        });


    }

    @Override
    public void onClick(View view) {

        String email = useremail.getText().toString().trim();
        String password = userpass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this,"Please Enter The Email.",Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Please Enter The Password.",Toast.LENGTH_SHORT);
            return;
        }
        if(view.getId()==R.id.logbutton){

            loginprogress.setTitle("Registering user.");
            loginprogress.setMessage("Please wait whie we create your account !");
            loginprogress.setCanceledOnTouchOutside(false);
            loginprogress.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                loginprogress.dismiss();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Authentication Successfully Finished.", Toast.LENGTH_SHORT).show();
                                Intent mainintent = new Intent(LoginActivity.this,MainActivity.class );
                                mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainintent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                loginprogress.hide();
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}
