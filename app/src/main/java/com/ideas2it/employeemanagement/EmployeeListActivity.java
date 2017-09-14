package com.ideas2it.employeemanagement;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.EmployeeListAdapter;
import data.EmployeeContract;
import data.EmployeeDBHelper;
import models.Employee;


public class EmployeeListActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener{
    private EmployeeDBHelper employeeDBHelper;
    private EmployeeListAdapter employeeListAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor employeesCursor;
    private ListView listView;
    private SearchView mSearchView;
    private List<Employee> employeesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.emp_list);
        listView.setTextFilterEnabled(true);
    }


    @Override
    protected void onResume(){
        super.onResume();
        retrieve();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee_list, menu);
        MenuItem search=menu.findItem(R.id.searchEmp);
        mSearchView=(SearchView)search.getActionView();
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addEmp:
                Intent intent = new Intent(this, AddEmployeeActivity.class);
                startActivity(intent);
                return true;
            case R.id.sortByName:
                Collections.sort(employeesList, new Comparator<Employee>() {
                    public int compare(Employee e1, Employee e2) {
                        return e1.getEmployeeName().compareTo(e2.getEmployeeName());
                    }
                });

                listView.setAdapter(employeeListAdapter);
                return true;
            case R.id.sortByDOB:
                Collections.sort(employeesList, new Comparator<Employee>() {
                    public int compare(Employee e1, Employee e2) {
                        return e1.getEmployeeDOB().compareTo(e2.getEmployeeDOB());
                    }
                });

                listView.setAdapter(employeeListAdapter);
                return true;
            case R.id.sortByDOJ:
                Collections.sort(employeesList, new Comparator<Employee>() {
                    public int compare(Employee e1, Employee e2) {
                        return e1.getEmployeeDOJ().compareTo(e2.getEmployeeDOJ());
                    }
                });

                listView.setAdapter(employeeListAdapter);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void retrieve(){
        employeeDBHelper = new EmployeeDBHelper(getApplicationContext());
        sqLiteDatabase = employeeDBHelper.getReadableDatabase();
        employeesCursor = sqLiteDatabase.rawQuery("SELECT  * FROM "+ EmployeeContract.EmpEntry.TABLE_NAME, null);
        employeeListAdapter= new EmployeeListAdapter(this, employeesCursor);
        employeesList = employeeListAdapter.fetchAll();
        listView = (ListView) findViewById(R.id.emp_list);
        listView.setAdapter(employeeListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee selectedEmployee = employeesList.get(position);
                Employee employee = new Employee();
                employee.setId(selectedEmployee.getId());
                employee.setEmployeeName(selectedEmployee.getEmployeeName());
                employee.setEmployeeAge(selectedEmployee.getEmployeeAge());
                employee.setEmployeeSalary(selectedEmployee.getEmployeeSalary());
                employee.setEmployeeDept(selectedEmployee.getEmployeeDept());
                employee.setEmployeeDOB(selectedEmployee.getEmployeeDOB());
                employee.setEmployeeDOJ(selectedEmployee.getEmployeeDOJ());
                Intent detailIntent = new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                detailIntent.putExtra("employee", employee);
                startActivity(detailIntent);
            }

        });
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            employeeListAdapter.getFilter().filter("");
        }
        else {
            employeeListAdapter.getFilter().filter(newText.toString());
        }

        return(true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return(false);
    }

    @Override
    public boolean onClose() {
        employeeListAdapter.getFilter().filter("");
        return(true);
    }

}
