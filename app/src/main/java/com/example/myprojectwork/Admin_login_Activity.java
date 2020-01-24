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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin_login_Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText useremailadmin,userpassadmin;
    private Button logbtnadmin;
    private FirebaseAuth mAuthadmin;
    private ProgressDialog loginprogressadmin;
    private Toolbar mytooladmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_);



        mAuthadmin = FirebaseAuth.getInstance();

        useremailadmin = (EditText) findViewById(R.id.youremailadmin);
        userpassadmin = (EditText) findViewById(R.id.logpasswordadmin);
        logbtnadmin = (Button) findViewById(R.id.logbuttonadmin);

        loginprogressadmin = new ProgressDialog(this);

        mytooladmin = (Toolbar) findViewById(R.id.logintoolbar);
        setSupportActionBar(mytooladmin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytooladmin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_login_Activity.this,StartActivity.class ));
                finish();
            }
        });

        logbtnadmin.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        String email = useremailadmin.getText().toString().trim();
        String password = userpassadmin.getText().toString().trim();

        Toast.makeText(Admin_login_Activity.this,"Please Enter The Password.",Toast.LENGTH_SHORT);

        if(TextUtils.isEmpty(email)){
            Toast.makeText(Admin_login_Activity.this,"Please Enter The Email.",Toast.LENGTH_SHORT);
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(Admin_login_Activity.this,"Please Enter The Password.",Toast.LENGTH_SHORT);
            return;
        }
        if(view.getId()==R.id.logbuttonadmin){

            loginprogressadmin.setTitle("Loging admin.");
            loginprogressadmin.setMessage("Please wait while we get you in !");
            loginprogressadmin.setCanceledOnTouchOutside(false);
            loginprogressadmin.show();

            mAuthadmin.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                loginprogressadmin.dismiss();
                                FirebaseUser user = mAuthadmin.getCurrentUser();
                                Toast.makeText(Admin_login_Activity.this, "Authentication Successfully Finished.", Toast.LENGTH_SHORT).show();
                                Intent mainintent = new Intent(Admin_login_Activity.this,Admin_MainActivity.class );
                                mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainintent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                loginprogressadmin.hide();
                                Toast.makeText(Admin_login_Activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }


}
