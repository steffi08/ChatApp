package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

import static android.os.Build.VERSION_CODES.O;

public class Employees extends AppCompatActivity {
   TextView t1,title,dob;
   EditText fname,lname,designation,address;
    ImageButton date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    Button edit,delete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        dob=findViewById(R.id.calendar);
        date=findViewById(R.id.calenderbutton);
       Intent intent=getIntent();
        final String empid =intent.getStringExtra("empid");
        t1=findViewById(R.id.text3);
        title=findViewById(R.id.title1);
        fname=findViewById(R.id.f_name);
        lname=findViewById(R.id.l_name);
        designation=findViewById(R.id.designation);
        address=findViewById(R.id.address);
        edit=findViewById(R.id.edit);
        delete=findViewById(R.id.delete);
        t1.setText(empid);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Employee").child(empid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                title.setText(dataSnapshot.child("Title").getValue().toString());
                fname.setText(dataSnapshot.child("Fname").getValue().toString());
                lname.setText(dataSnapshot.child("Lname").getValue().toString());
                dob.setText(dataSnapshot.child("DOB").getValue().toString());
                designation.setText(dataSnapshot.child("Designation").getValue().toString());
                address.setText(dataSnapshot.child("Address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Employees.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dob.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname1=fname.getText().toString().trim();
                String lname1=lname.getText().toString().trim();
                String date_b=dob.getText().toString().trim();
                String designation1=designation.getText().toString().trim();
                String address1=address.getText().toString().trim();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Employee");
                HashMap hashMap=new HashMap();
                hashMap.put("Fname",fname1);
                hashMap.put("Lname",lname1);
                hashMap.put("DOB",date_b);
                hashMap.put("Designation",designation1);
                hashMap.put("Address",address1);
               reference.child(empid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                   @Override
                   public void onSuccess(Object o) {
                       Toast.makeText(Employees.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                   }
               });


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Employee").child(empid);
                    reference.removeValue();
                    finish();
                    startActivity(new Intent(getApplicationContext(), SearchEmployee.class));//home page



            }
        });




    }
}
