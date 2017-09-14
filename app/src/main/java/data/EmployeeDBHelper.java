package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public final class EmployeeDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Employee.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + EmployeeContract.EmpEntry.TABLE_NAME + " (" +
                    EmployeeContract.EmpEntry._ID+ " INTEGER PRIMARY KEY," +
                    EmployeeContract.EmpEntry.COLUMN_NAME + " TEXT," +
                    EmployeeContract.EmpEntry.COLUMN_AGE + " INT," +
                    EmployeeContract.EmpEntry.COLUMN_SALARY + " INT," +
                    EmployeeContract.EmpEntry.COLUMN_DEPT + " TEXT," +
                    EmployeeContract.EmpEntry.COLUMN_DOB + " DATE," +
                    EmployeeContract.EmpEntry.COLUMN_DOJ + " DATETIME"+ ");";


    public EmployeeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
