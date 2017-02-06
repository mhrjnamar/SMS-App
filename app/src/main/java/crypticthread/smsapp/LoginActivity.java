package crypticthread.smsapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            finish();
            startActivity(new Intent(LoginActivity.this, SelectChildrensActivity.class));
        }
    };
    private TextInputEditText et_emailID;
    private TextInputEditText et_password;
    private Button btn_login;
    private Button btn_forgot;
    private ScrollView sv_login_holder;
    private ImageView img_logo;
    private JsonApi loginAsync;
    private RelativeLayout loading_view;
    private LoadingFrag frag;
    private UserSessionManager manager;
    private JsonApi.JsonApiListener listener = new JsonApi.JsonApiListener() {
        @Override
        public void onSuccess(String method, String response) {
            loginAsync = null;

            try {
                JSONObject object = new JSONObject(response);
                JSONArray data = object.getJSONArray("data");
                JSONObject datum = data.getJSONObject(0);
                ParentsDetails details = new ParentsDetails(datum.getString("id"),
                        datum.getString("first_name"),
                        datum.getString("last_name"),
                        datum.getString("username"),
                        datum.getString("role"),
                        datum.getString("email"),
                        datum.getString("student_id"),
                        datum.getString("photo"));
                manager.setParentsDetails(details);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "onSuccess:response " + response);
            //frag.dismiss();
            Handler h = new Handler();
            h.postDelayed(runnable, 400);


        }

        @Override
        public void onError(String error) {
            loginAsync = null;
            showLoading(false);
            frag.dismiss();
            showDialog(error);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginScreenTheme);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.activity_in,0);

        //initializing views
        et_emailID = (TextInputEditText) findViewById(R.id.et_emailId);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgot = (Button) findViewById(R.id.btn_forgot);
        sv_login_holder = (ScrollView) findViewById(R.id.sv_login_holder);
        img_logo = (ImageView) findViewById(R.id.app_icon);
        loading_view = (RelativeLayout) findViewById(R.id.loadingView);
        et_emailID.clearFocus();
        et_password.clearFocus();

        et_emailID.setText("sanjeev.budha@deerwalk.edu.np");
        et_password.setText("123");

        manager = new UserSessionManager(LoginActivity.this);

        //for animation
        sv_login_holder.setVisibility(View.INVISIBLE);
        animateLogin(img_logo, sv_login_holder);

        btn_login.setOnClickListener(this);


    }

    private String getText(TextInputEditText et) {
        return et.getText().toString().trim();
    }

    private void showDialog(String msg) {
        new AlertDialog.Builder(LoginActivity.this).setTitle("Sorry").setMessage(msg)
                .setPositiveButton("Retry", null).create().show();
    }


    private void showLoading(Boolean isShow) {

        loading_view.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        et_emailID.clearFocus();
        et_password.clearFocus();
        et_emailID.setEnabled(!isShow);
        et_password.setEnabled(!isShow);
        btn_login.setText(isShow ? "Logging in..." : "Login");
        btn_forgot.setVisibility(!isShow ? View.VISIBLE : View.GONE);
        btn_login.setEnabled(!isShow);
        btn_forgot.setEnabled(!isShow);

    }

    private void animateLogin(ImageView logo, final ScrollView sv_login_holder) {

        logo.setY(500f);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        ObjectAnimator logo_translateY = ObjectAnimator.ofFloat(logo, "translationY", 500f, 0);
        logo_translateY.setInterpolator(new OvershootInterpolator());
        logo_translateY.setDuration(1000);
        logo_translateY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                sv_login_holder.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ObjectAnimator sv_translateY = ObjectAnimator.ofFloat(sv_login_holder, "translationY", (float) height, 0f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playSequentially(logo_translateY, sv_translateY);
        set.setStartDelay(1000);
        set.start();

    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        if (v.equals(btn_login)) {
           // if (getText(et_emailID).matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z].+[a-z]+") && getText(et_emailID).length() > 0) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(getText(et_emailID)).matches()&& getText(et_emailID).length() > 0) {
                frag = LoadingFrag.newInstance("Logging in please wait...");
                frag.show(getSupportFragmentManager(), "Loading");
                showLoading(true);
                if (loginAsync == null) {
                    loginAsync = new JsonApi("http://www.crypticthread.com.np/smsapi/parents.php?code=ABCD_1124&email=" + getText(et_emailID) + "&password=" + getText(et_password), listener);
                    loginAsync.execute();
                }
            } else if (getText(et_password).isEmpty()) {
                showDialog("Password is empty");
            } else {
                showDialog(" Email Id is invalid \n Please enter email in correct format\n Eg. rahul@gmail.com");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginAsync != null) {
            loginAsync.cancel(true);
            loginAsync = null;
        }
    }
}
