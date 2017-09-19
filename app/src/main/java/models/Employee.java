package models;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.id;

public class Employee implements Parcelable {

    private String empId;
    private String employeeName;
    private String employeeAge;
    private String employeeSalary;
    private String employeeDept;
    private String employeeDOB;
    private String employeeDOJ;

    public Employee() {
    }

    public Employee(String empId, String employeeName, String employeeAge, String employeeSalary, String employeeDept, String employeeDOB, String employeeDOJ) {
        this.empId = empId;
        this.employeeName = employeeName;
        this.employeeAge = employeeAge;
        this.employeeSalary = employeeSalary;
        this.employeeDept = employeeDept;
        this.employeeDOB = employeeDOB;
        this.employeeDOJ = employeeDOJ;
    }

    public String getId() {
        return empId;
    }

    public void setId(String empId) {
        this.empId = empId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeAge(String employeeAge) {
        this.employeeAge = employeeAge;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public void setEmployeeDept(String employeeDept) {
        this.employeeDept = employeeDept;
    }

    public void setEmployeeDOB(String employeeDOB) {
        this.employeeDOB = employeeDOB;
    }

    public void setEmployeeDOJ(String employeeDOJ) {
        this.employeeDOJ = employeeDOJ;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeAge() {
        return employeeAge;
    }

    public String getEmployeeSalary() {
        return employeeSalary;
    }

    public String getEmployeeDept() {
        return employeeDept;
    }

    public String getEmployeeDOB() {
        return employeeDOB;
    }

    public String getEmployeeDOJ() {
        return employeeDOJ;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(empId);
        out.writeString(employeeName);
        out.writeString(employeeAge);
        out.writeString(employeeSalary);
        out.writeString(employeeDept);
        out.writeString(employeeDOB);
        out.writeString(employeeDOJ);
    }

    public static final Parcelable.Creator<Employee> CREATOR
            = new Parcelable.Creator<Employee>() {
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public Employee(Parcel in) {
        empId = in.readString();
        employeeName = in.readString();
        employeeAge = in.readString();
        employeeSalary = in.readString();
        employeeDept = in.readString();
        employeeDOB = in.readString();
        employeeDOJ = in.readString();
    }
}
