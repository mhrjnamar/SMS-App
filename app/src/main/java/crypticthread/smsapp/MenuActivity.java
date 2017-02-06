package crypticthread.smsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "MenuActivity";

    private Toolbar tb;
    private String name;
    private TextView student_name;
    private TextView class_no;
    private TextView roll_no;
    private DrawerLayout dLayout;
    private ActionBarDrawerToggle toogle;
    private NavigationView navView;
    private CardView cv_attendance,cv_marksSheet,cv_fee,cv_subjects,cv_routine,cv_notice;
    private JsonApi async;
    private JsonApi.JsonApiListener listener = new JsonApi.JsonApiListener() {
        @Override
        public void onSuccess(String method, String response) {

        }

        @Override
        public void onError(String error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        overridePendingTransition(R.anim.slide_in_loading, R.anim.slide_out_loading);
        CollapsingToolbarLayout cb = (CollapsingToolbarLayout) findViewById(R.id.cb);
        cb.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        tb = (Toolbar) findViewById(R.id.tb);
        student_name = (TextView) findViewById(R.id.name);
        class_no = (TextView) findViewById(R.id.class_no);
        roll_no = (TextView) findViewById(R.id.roll_no);
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        cv_attendance = (CardView) findViewById(R.id.attendance);
        cv_marksSheet = (CardView) findViewById(R.id.marks_sheet);
        cv_fee = (CardView) findViewById(R.id.fee);
        cv_subjects = (CardView) findViewById(R.id.subject);
        cv_routine = (CardView) findViewById(R.id.routine);
        cv_notice = (CardView) findViewById(R.id.notice);

        cv_attendance.setOnClickListener(this);
        cv_marksSheet.setOnClickListener(this);
        cv_fee.setOnClickListener(this);
        cv_subjects.setOnClickListener(this);
        cv_routine.setOnClickListener(this);
        cv_notice.setOnClickListener(this);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cursor cursor = getContentResolver().query(DataProvider.STUDENT_URI, null, StudentDatabase.COL_STUDENT_ID + " = " + getIntent().getStringExtra(StaticVariables.KEY_STUDENT_ID), null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            name = cursor.getString(cursor.getColumnIndex(StudentDatabase.COL_STUDENT_FN)) + " " + cursor.getString(cursor.getColumnIndex(StudentDatabase.COL_STUDENT_LN));
            getSupportActionBar().setTitle(name);
            student_name.setText(name);
            class_no.setText(cursor.getString(cursor.getColumnIndex(StudentDatabase.COL_STUDENT_GRADE)));
            roll_no.setText(cursor.getString(cursor.getColumnIndex(StudentDatabase.COL_STUDENT_ROLL)));
            cursor.close();
        }

        toogle = new ActionBarDrawerToggle(MenuActivity.this,dLayout,tb,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        navView.setNavigationItemSelectedListener(this);
        dLayout.addDrawerListener(toogle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            toogle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toogle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (dLayout.isDrawerOpen(GravityCompat.START)){
            dLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout){
            getContentResolver().delete(DataProvider.STUDENT_URI,null,null);
            finish();
            Intent i = new Intent(MenuActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }
        return false;
    }

    private void callApis(){

    }


    @Override
    public void onClick(View v) {
        if (v.equals(cv_attendance)){
            startActivity(new Intent(MenuActivity.this,AttendanceActivity.class));
        }else if (v.equals(cv_marksSheet)){
            startActivity(new Intent(MenuActivity.this,MarksSheetActivity.class));
        }else if (v.equals(cv_fee)){
            startActivity(new Intent(MenuActivity.this,FeeActivity.class));
        }else if (v.equals(cv_subjects)){
            startActivity(new Intent(MenuActivity.this,SubjectsActivity.class));
        }else if (v.equals(cv_routine)){
            startActivity(new Intent(MenuActivity.this,RoutineActivity.class));
        }else if (v.equals(cv_notice)){
            startActivity(new Intent(MenuActivity.this,NoticeActivity.class));
        }

    }
}
