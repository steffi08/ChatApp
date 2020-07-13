package com.example.mychatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Empinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button add,search;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empinfo);
        add=findViewById(R.id.add);
        search=findViewById(R.id.search);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddEmployee.class);
                startActivity(intent);

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SearchEmployee.class);
                startActivity(intent);
            }
        });

    }
}
