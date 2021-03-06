package com.ideas2it.employeemanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import models.Employee;

import static com.ideas2it.employeemanagement.ModifyEmployeeActivity.DatePickerDialogFragment.FLAG_DOB_DATE;
import static com.ideas2it.employeemanagement.ModifyEmployeeActivity.DatePickerDialogFragment.FLAG_DOJ_DATE;

public class ModifyEmployeeActivity extends AppCompatActivity {

    private Employee employee = null;
    private static EditText dobTxt;
    private static EditText dojTxt;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_employee);
        Bundle bundle = getIntent().getExtras();
        employee = (Employee) bundle.get("employee");
        EditText editText = (EditText) findViewById(R.id.edit_emp_name);
        editText.setText(employee.getEmployeeName());
        editText = (EditText) findViewById(R.id.edit_emp_age);
        editText.setText(employee.getEmployeeAge());
        editText = (EditText) findViewById(R.id.edit_emp_salary);
        editText.setText(employee.getEmployeeSalary());
        editText = (EditText) findViewById(R.id.edit_emp_dept);
        editText.setText(employee.getEmployeeDept());
        dobTxt = (EditText) findViewById(R.id.edit_emp_dob);
        dobTxt.setText(employee.getEmployeeDOB());
        dojTxt = (EditText) findViewById(R.id.edit_emp_doj);
        dojTxt.setText(employee.getEmployeeDOJ());
        mDatabase = FirebaseDatabase.getInstance().getReference("employees");

    }


    public void update(View view){

        EditText editText = (EditText) findViewById(R.id.edit_emp_name);
        String name = editText.getText().toString().trim();
        editText = (EditText) findViewById(R.id.edit_emp_age);
        String age = editText.getText().toString().trim();
        editText = (EditText) findViewById(R.id.edit_emp_salary);
        String salary = editText.getText().toString().trim();
        editText = (EditText) findViewById(R.id.edit_emp_dept);
        String dept = editText.getText().toString().trim();
        dobTxt= (EditText) findViewById(R.id.edit_emp_dob);
        String dob = dobTxt.getText().toString().trim();
        dojTxt = (EditText) findViewById(R.id.edit_emp_doj);
        String doj = dojTxt.getText().toString().trim();

        Employee emp = new Employee(employee.getId(), name, age, salary, dept, dob, doj);

        mDatabase.child(employee.getId()).setValue(emp);

        Context context = getApplicationContext();
        CharSequence text = String.format("Employee updated with employee id %1$s", employee.getId());
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
        DatePickerDialogFragment editEmployeeDatePickerDialogFragment = new DatePickerDialogFragment();
        if (id == R.id.edit_emp_dob_picker) {
            editEmployeeDatePickerDialogFragment.setFlag(FLAG_DOB_DATE);
            editEmployeeDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (id == R.id.edit_emp_doj_picker) {
            editEmployeeDatePickerDialogFragment.setFlag(FLAG_DOJ_DATE);
            editEmployeeDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
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
