package com.example.myprojectwork;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.telephony.SmsManager;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

public class Police_Activity extends AppCompatActivity {

    private Toolbar mytoolpolice;
    private Button police1,police2,police3,police4,police5,police6;
    private static final int   MY_CALL_PERMISSION_CODE = 1 ;

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

        call_1();
        call_2();
        call_3();
        call_4();
        call_5();
        call_6();


    }

    private  void call_1(){
        police1  = findViewById(R.id.police1);
        police1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert_intent = new Intent(Intent.ACTION_CALL);
                alert_intent.setData(Uri.parse("tel:0152153252"));


                if(ActivityCompat.checkSelfPermission(Police_Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(alert_intent);

                }
                else {
                    ActivityCompat.requestPermissions(Police_Activity.this,new String[] {Manifest.permission.CALL_PHONE}, MY_CALL_PERMISSION_CODE);
                }
            }


        });
    }

    private  void call_2(){
        police1  = findViewById(R.id.police2);
        police1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert_intent = new Intent(Intent.ACTION_CALL);
                alert_intent.setData(Uri.parse("tel:0152153252"));


                if(ActivityCompat.checkSelfPermission(Police_Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(alert_intent);

                }
                else {
                    ActivityCompat.requestPermissions(Police_Activity.this,new String[] {Manifest.permission.CALL_PHONE}, MY_CALL_PERMISSION_CODE);
                }
            }


        });
    }

    private  void call_3(){
        police1  = findViewById(R.id.police3);
        police1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert_intent = new Intent(Intent.ACTION_CALL);
                alert_intent.setData(Uri.parse("tel:0152153252"));


                if(ActivityCompat.checkSelfPermission(Police_Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(alert_intent);

                }
                else {
                    ActivityCompat.requestPermissions(Police_Activity.this,new String[] {Manifest.permission.CALL_PHONE}, MY_CALL_PERMISSION_CODE);
                }
            }


        });
    }

    private  void call_4(){
        police1  = findViewById(R.id.police4);
        police1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert_intent = new Intent(Intent.ACTION_CALL);
                alert_intent.setData(Uri.parse("tel:0152153252"));


                if(ActivityCompat.checkSelfPermission(Police_Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(alert_intent);

                }
                else {
                    ActivityCompat.requestPermissions(Police_Activity.this,new String[] {Manifest.permission.CALL_PHONE}, MY_CALL_PERMISSION_CODE);
                }
            }


        });
    }



    private  void call_5(){
        police1  = findViewById(R.id.police5);
        police1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert_intent = new Intent(Intent.ACTION_CALL);
                alert_intent.setData(Uri.parse("tel:0152153252"));


                if(ActivityCompat.checkSelfPermission(Police_Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(alert_intent);

                }
                else {
                    ActivityCompat.requestPermissions(Police_Activity.this,new String[] {Manifest.permission.CALL_PHONE}, MY_CALL_PERMISSION_CODE);
                }
            }


        });
    }

    private  void call_6(){
        police1  = findViewById(R.id.police6);
        police1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert_intent = new Intent(Intent.ACTION_CALL);
                alert_intent.setData(Uri.parse("tel:0152153252"));


                if(ActivityCompat.checkSelfPermission(Police_Activity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(alert_intent);

                }
                else {
                    ActivityCompat.requestPermissions(Police_Activity.this,new String[] {Manifest.permission.CALL_PHONE}, MY_CALL_PERMISSION_CODE);
                }
            }


        });
    }
}
