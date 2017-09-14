package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Employee implements Parcelable {

    private int id;
    private String employeeName;
    private int employeeAge;
    private int employeeSalary;
    private String employeeDept;
    private String employeeDOB;
    private String employeeDOJ;

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }

    public void setEmployeeSalary(int employeeSalary) {
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

    public int getEmployeeAge() {
        return employeeAge;
    }

    public int getEmployeeSalary() {
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
        out.writeInt(id);
        out.writeString(employeeName);
        out.writeInt(employeeAge);
        out.writeInt(employeeSalary);
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
        id = in.readInt();
        employeeName = in.readString();
        employeeAge = in.readInt();
        employeeSalary = in.readInt();
        employeeDept = in.readString();
        employeeDOB = in.readString();
        employeeDOJ = in.readString();
    }
}
