package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mychatapplication.Adapter.EmployeeAdapter;
import com.example.mychatapplication.Model.EmployeeDetails;
import com.example.mychatapplication.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployee extends AppCompatActivity {
    private RecyclerView recyclerView;

    private List<EmployeeDetails> employee;
    private EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_employee);
        recyclerView=findViewById(R.id.recycleview1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        employee=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Employee");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    EmployeeDetails emp=snapshot.getValue(EmployeeDetails.class);
                    assert emp !=null;
                        employee.add(emp);
                }
             employeeAdapter=new EmployeeAdapter(getApplicationContext(),employee);
                recyclerView.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
