package crypticthread.smsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 2/3/2017.
 */

public class StudentDatabase extends SQLiteOpenHelper {
    public static final String COL_STUDENT_ID = "col_student_id";
    public static final String COL_STUDENT_FN = "col_student_fn";
    public static final String COL_STUDENT_LN = "col_student_ln";
    public static final String COL_STUDENT_ROLL = "col_student_roll";
    public static final String COL_STUDENT_GRADE = "col_student_grade";
    private static final String TAG = "StudentDatabase";
    private static final String DATABASE_NAME = "students.db";
    private static final String STUDENT_TABLE = "tbl_student";

    public StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + STUDENT_TABLE + "(" + COL_STUDENT_ID + " INTEGER PRIMARY KEY, "
                + COL_STUDENT_FN + " TEXT, "
                + COL_STUDENT_LN + " TEXT, "
                + COL_STUDENT_ROLL + " TEXT, "
                + COL_STUDENT_GRADE + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + STUDENT_TABLE);
        onCreate(db);
    }

    public long insertStudentInfo(ContentValues contentValues) {
        return getWritableDatabase().insert(STUDENT_TABLE, null, contentValues);
    }

    public long deleteStudentInfo(String s){
        return getWritableDatabase().delete(STUDENT_TABLE,s,null);
    }

    public Cursor loadStudentData(String s){
        return getReadableDatabase().rawQuery("SELECT * FROM "+STUDENT_TABLE+" WHERE "+s,null);
    }
}
