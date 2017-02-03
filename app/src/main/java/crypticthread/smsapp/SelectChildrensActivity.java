package crypticthread.smsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SelectChildrensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_childrens);
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);

    }
}
