package crypticthread.smsapp;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectChildrensActivity extends AppCompatActivity {
    private static final String TAG = "SelectChildrensActivity";

    private RecyclerView studentList;
    private StudentListAdapter adapter;
    private int id_count = 0;
    private UserSessionManager manager;
    private JsonApi studentInfoAsync;
    private ArrayList<StudentDetails> student;
    private StudentDetails details;
    private LoadingFrag frag;
    private JsonApi.JsonApiListener listener = new JsonApi.JsonApiListener() {
        @Override
        public void onSuccess(String method, String response) {
            try {
                JSONObject data = new JSONObject(response);
                JSONArray array = data.getJSONArray("data");
                JSONObject datum = array.getJSONObject(0);
                details = new StudentDetails(datum.getString("id"),
                        datum.getString("first_name"),
                        datum.getString("last_name"),
                        datum.getString("roll_number"),
                        datum.getString("grade"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ContentValues cv = new ContentValues();
            cv.put(StudentDatabase.COL_STUDENT_ID, details.getStudent_id());
            cv.put(StudentDatabase.COL_STUDENT_FN, details.getFirstName());
            cv.put(StudentDatabase.COL_STUDENT_LN, details.getLastName());
            cv.put(StudentDatabase.COL_STUDENT_ROLL, details.getRollNo());
            cv.put(StudentDatabase.COL_STUDENT_GRADE, details.getGrade());
            getContentResolver().insert(DataProvider.STUDENT_URI, cv);
            student.add(details);
            studentInfoAsync = null;
            Log.i(TAG, "onSuccess:Ssize "+student.size());
            Log.i(TAG, "onSuccess:idCount "+id_count);
            if (student.size()==id_count){
                frag.dismiss();
                adapter.updateInfo(student);
            }

        }

        @Override
        public void onError(String error) {
            frag.dismiss();
            studentInfoAsync = null;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_childrens);
        overridePendingTransition(0, R.anim.activity_out);

        frag = LoadingFrag.newInstance("Getting Student infos....");
        frag.show(getSupportFragmentManager(),"StudentInfos");

        studentList = (RecyclerView) findViewById(R.id.childrensList);
        studentList.setLayoutManager(new LinearLayoutManager(SelectChildrensActivity.this));
        adapter = new StudentListAdapter();
        studentList.setAdapter(adapter);

        student = new ArrayList<>();
        manager = new UserSessionManager(SelectChildrensActivity.this);

        String childId = manager.getChildIds();
        Log.i(TAG, "onCreate: "+childId);
        if (childId.contains(",")) {
            String cIds[] = manager.getChildIds().split(",");
            id_count = cIds.length;
            for (String cId : cIds) {
                studentInfoAsync = new JsonApi("http://www.crypticthread.com.np/smsapi/students.php?code=ABCD_1124&id=" + cId
                        , listener);
                studentInfoAsync.execute();
            }
        } else {
            id_count= 1;
            if (studentInfoAsync == null) {
                studentInfoAsync = new JsonApi("http://www.crypticthread.com.np/smsapi/students.php?code=ABCD_1124&id=" + childId
                        , listener);
                studentInfoAsync.execute();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (studentInfoAsync != null) {
            studentInfoAsync.cancel(true);
            studentInfoAsync = null;
        }
    }

    @Override
    public void onBackPressed() {

    }

    class StudentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<StudentDetails> details;

        StudentListAdapter() {
            details = new ArrayList<>();
        }

        void updateInfo(ArrayList<StudentDetails> details) {
            this.details = details;
            notifyDataSetChanged();

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(SelectChildrensActivity.this).inflate(R.layout.childrens_list_view, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Holder h = (Holder) holder;
            final StudentDetails detail;
            detail = details.get(position);
            h.name.setText(detail.getFirstName() + " " + detail.getLastName());
            h.class_no.setText(detail.getGrade());
            h.roll_no.setText(detail.getRollNo());
            ObjectAnimator anim = ObjectAnimator.ofFloat(h.itemView,"translationY",800,0);
            anim.setDuration(200);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent i = new Intent(SelectChildrensActivity.this,MenuActivity.class);
                    i.putExtra(StaticVariables.KEY_STUDENT_ID,detail.getStudent_id());
                    startActivity(i);
                }
            });

        }


        @Override
        public int getItemCount() {
            return details.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            TextView name;
            TextView class_no;
            TextView roll_no;

            Holder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                class_no = (TextView) itemView.findViewById(R.id.class_no);
                roll_no = (TextView) itemView.findViewById(R.id.roll_no);
            }
        }
    }
}
