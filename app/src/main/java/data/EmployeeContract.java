package data;

import android.provider.BaseColumns;


public class EmployeeContract {

    private EmployeeContract() {
    }

    public static class EmpEntry implements BaseColumns {
        public static final String TABLE_NAME = "employee";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_DEPT = "dept";
        public static final String COLUMN_SALARY = "salary";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_DOJ = "doj";
    }
}
