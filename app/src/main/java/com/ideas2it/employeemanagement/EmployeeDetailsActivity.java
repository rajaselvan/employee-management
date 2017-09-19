package com.ideas2it.employeemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import models.Employee;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        Bundle bundle = getIntent().getExtras();
        employee = (Employee) bundle.get("employee");
        TextView textView = (TextView) findViewById(R.id.display_emp_name);
        textView.setText(employee.getEmployeeName());
        textView = (TextView) findViewById(R.id.display_emp_age);
        textView.setText(employee.getEmployeeAge());
        textView = (TextView) findViewById(R.id.display_emp_salary);
        textView.setText(employee.getEmployeeSalary());
        textView = (TextView) findViewById(R.id.display_emp_dept);
        textView.setText(employee.getEmployeeDept());
        textView = (TextView) findViewById(R.id.display_emp_dob);
        textView.setText(employee.getEmployeeDOB());
        textView = (TextView) findViewById(R.id.display_emp_doj);
        textView.setText(employee.getEmployeeDOJ());
    }

    public void onClickEdit(View view){
        Intent intent = new Intent(getApplicationContext(), ModifyEmployeeActivity.class);
        intent.putExtra("employee", employee);
        startActivity(intent);
    }
}
