package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mychatapplication.Model.EmployeeDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AddEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button add;
    TextView dob;
    EditText fname,lname,designation,address;
    Spinner title;
    ImageButton date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String title1,Id;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        dob=findViewById(R.id.calendar);
        date=findViewById(R.id.calenderbutton);
        title=findViewById(R.id.title);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.title,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title.setAdapter(adapter);
        title.setOnItemSelectedListener(this);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddEmployee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dob.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        fname=findViewById(R.id.f_name);
        lname=findViewById(R.id.l_name);
        designation=findViewById(R.id.designation);
        address=findViewById(R.id.address);
        add=findViewById(R.id.adde);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String fname1=fname.getText().toString().trim();
               String lname1=lname.getText().toString().trim();
               String date_b=dob.getText().toString().trim();
               String designation1=designation.getText().toString().trim();
               String address1=address.getText().toString().trim();
               String title2=title1.trim();

                Id=fname1+designation1;
             //EmployeeDetails employeeDetails=new EmployeeDetails();

                reference= FirebaseDatabase.getInstance().getReference("Employee").child(Id);
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("id",Id);
                hashMap.put("Title",title2);
                hashMap.put("Fname",fname1);
                hashMap.put("Lname",lname1);
                hashMap.put("DOB",date_b);
                hashMap.put("Designation",designation1);
                hashMap.put("Address",address1);
            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddEmployee.this, "Successfully registered", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), Empinfo.class));//home page
                    }

                }
            });


            }
        });



    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item=parent.getItemAtPosition(position).toString();
        if(item.equals("Title")) {
            Toast.makeText(this, "Choose Title", Toast.LENGTH_SHORT).show();
        }
        else{
            title1=item;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
