package crypticthread.smsapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by User on 2/2/2017.
 */

public class JsonApi extends AsyncTask<String,Void,String> {
    private static final String TAG = "JsonApi";
    private String result = null;
    private final String method;
    private JsonApiListener listener;
    private String error = null;

    public JsonApi(String method,JsonApiListener listener) {
        this.method = method;
        this.listener = listener;
        Log.i(TAG, "JsonApi: method"+method);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(),10000);
        synchronized (method){

        }
        HttpPost post = new HttpPost(method);
        try {
            HttpResponse response = client.execute(post);
            if (response!=null){
                InputStream in = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine())!=null){
                    sb.append(line).append("\n");
                }
                result = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            error = e.toString();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s==null){
            listener.onError("Could not connect to the Server. \nPlease Check your Internet Connection and try again.\n\nDetails:\n"+error);
            return;
        }

        Log.i(TAG, "onPostExecute:s "+s);
        String msg = "";
        String status = "";
        try {
            JSONObject object = new JSONObject(s);
            status = object.getString("status");
            msg = object.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (status.equals("0")) {
//            assert data != null;
//            if (data == null) {
//                listener.onError("No Data");
//            } else {
                listener.onSuccess(method, s);
//            }
        }else {
            listener.onError(msg);
        }
    }


    interface JsonApiListener{
        void onSuccess(String method,String response);
        void onError(String error);
    }
}
