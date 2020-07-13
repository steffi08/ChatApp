package com.example.mychatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapplication.Employees;
import com.example.mychatapplication.Model.EmployeeDetails;

import com.example.mychatapplication.R;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private Context eContext;
    private List<EmployeeDetails> employeeDetails;

    public EmployeeAdapter(Context eContext, List<EmployeeDetails> employeeDetails) {
        this.eContext = eContext;
        this.employeeDetails = employeeDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(eContext).inflate(R.layout.employee_item,viewGroup,false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
     final EmployeeDetails emp=employeeDetails.get(i);
        viewHolder.eid.setText(emp.getId());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(eContext,Employees.class);
                intent.putExtra("empid",emp.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                eContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eid=itemView.findViewById(R.id.employeeid);

        }
    }
}
