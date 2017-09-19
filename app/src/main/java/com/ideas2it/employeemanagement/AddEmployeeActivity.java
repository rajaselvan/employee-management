package com.ideas2it.employeemanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import models.Employee;


import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEmployeeActivity extends AppCompatActivity  {

    private static EditText dobTxt;
    private static EditText dojTxt;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        dobTxt = (EditText) findViewById(R.id.new_emp_dob);
        dojTxt = (EditText) findViewById(R.id.new_emp_doj);
        mDatabase = FirebaseDatabase.getInstance().getReference("employees");
    }


    public void insert(View view) {

        EditText editText = (EditText) findViewById(R.id.new_emp_name);
        String name = editText.getText().toString().trim();
        editText = (EditText) findViewById(R.id.new_emp_age);
        String age = editText.getText().toString().trim();
        editText = (EditText) findViewById(R.id.new_emp_salary);
        String salary = editText.getText().toString().trim();
        editText = (EditText) findViewById(R.id.new_emp_dept);
        String dept = editText.getText().toString().trim();
        editText= (EditText) findViewById(R.id.new_emp_dob);
        String dob = editText.getText().toString().trim();
        editText = (EditText) findViewById(R.id.new_emp_doj);
        String doj = editText.getText().toString().trim();

        String empId = mDatabase.push().getKey();

        Employee employee = new Employee(empId, name, age, salary, dept, dob, doj);

        mDatabase.child(empId).setValue(employee);

        Context context = getApplicationContext();
        CharSequence text = String.format("Employee added with employee id %1$s", empId);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent intent = new Intent(this, EmployeeListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void datePicker(View view) {
        int id = view.getId();
        DatePickerDialogFragment addEmployeeDatePickerDialogFragment = new DatePickerDialogFragment();

        if (id == R.id.new_emp_dob_picker) {
            addEmployeeDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_DOB_DATE);
            addEmployeeDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.new_emp_doj_picker) {
            addEmployeeDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_DOJ_DATE);
            addEmployeeDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        }
    }

    public static class DatePickerDialogFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        public static final int FLAG_DOB_DATE = 0;
        public static final int FLAG_DOJ_DATE = 1;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (flag == FLAG_DOB_DATE) {
                dobTxt.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_DOJ_DATE) {
                dojTxt.setText(format.format(calendar.getTime()));
            }
        }
    }
}
