package com.example.myprojectwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GraphShow_Activity extends AppCompatActivity {

    private Toolbar mytoolgraph1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_show_);

        mytoolgraph1 = (Toolbar) findViewById(R.id.graphtoolbar1);

        setSupportActionBar(mytoolgraph1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoolgraph1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphShow_Activity.this,MainActivity.class ));
                finish();
            }
        });
    }
}
