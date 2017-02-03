package crypticthread.smsapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by User on 2/3/2017.
 */

public class DataProvider extends ContentProvider {
    private static final String TAG = "DataProvider";

    private static final String PROVIDER_NAME = "crypticthread.smsapp.provider";
    private static final int STUDENT = 0X10;
    public static final Uri STUDENT_URI = Uri.parse("content://"+PROVIDER_NAME+"/students");
    private static UriMatcher uriMatcher;
    private StudentDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"students",STUDENT);
    }


    @Override
    public boolean onCreate() {
        db = new StudentDatabase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri)==STUDENT){
            return db.loadStudentData(selection);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri)==STUDENT){
             db.insertStudentInfo(values);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri)==STUDENT){
            return (int) db.deleteStudentInfo(selection);
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
