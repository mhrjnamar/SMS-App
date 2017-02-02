package crypticthread.smsapp;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

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
    private String method;
    private JsonApiListener listener;

    public JsonApi(String method,JsonApiListener listener) {
        this.method = method;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(),10000);
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
            result = e.toString();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s.isEmpty()){
            listener.onError("Could not connect to the Server");
        }else {
            listener.onSuccess(method,s);
        }
    }


    interface JsonApiListener{
        void onSuccess(String method,String response);
        void onError(String error);
    }
}
