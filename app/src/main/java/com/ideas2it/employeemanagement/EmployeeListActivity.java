package com.ideas2it.employeemanagement;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.EmployeeListAdapter;
import data.EmployeeContract;
import data.EmployeeDBHelper;
import listeners.RecyclerItemClickListener;
import models.Employee;


public class EmployeeListActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener{
    private EmployeeDBHelper employeeDBHelper;
    private EmployeeListAdapter employeeListAdapter;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor employeesCursor;
    private SearchView mSearchView;
    private List<Employee> employeesList;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);
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
                employeesList = employeeListAdapter.fetchAll();
                Collections.sort(employeesList, new Comparator<Employee>() {
                    public int compare(Employee e1, Employee e2) {
                        return e1.getEmployeeName().compareTo(e2.getEmployeeName());
                    }
                });
                mRecyclerView.setAdapter(employeeListAdapter);
                return true;
            case R.id.sortByDOB:
                employeesList = employeeListAdapter.fetchAll();
                Collections.sort(employeesList, new Comparator<Employee>() {
                    public int compare(Employee e1, Employee e2) {
                        return e1.getEmployeeDOB().compareTo(e2.getEmployeeDOB());
                    }
                });
                mRecyclerView.setAdapter(employeeListAdapter);
                return true;
            case R.id.sortByDOJ:
                employeesList = employeeListAdapter.fetchAll();
                Collections.sort(employeesList, new Comparator<Employee>() {
                    public int compare(Employee e1, Employee e2) {
                        return e1.getEmployeeDOJ().compareTo(e2.getEmployeeDOJ());
                    }
                });
                mRecyclerView.setAdapter(employeeListAdapter);
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
        mRecyclerView.setAdapter(employeeListAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Employee selectedEmployee = (Employee) employeesList.get(position);
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

                })
        );
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            employeeListAdapter.getFilter().filter("");
            employeesList = employeeListAdapter.fetchAll();
            mRecyclerView.setAdapter(employeeListAdapter);
        }
        else {
            employeeListAdapter.getFilter().filter(newText.toString());
            employeesList = employeeListAdapter.fetchAll();
            mRecyclerView.setAdapter(employeeListAdapter);
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
        employeesList = employeeListAdapter.fetchAll();
        mRecyclerView.setAdapter(employeeListAdapter);
        return(true);
    }

}
