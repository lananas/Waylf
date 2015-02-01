package com.mushroom.waylf;

/**
 * Created by antoinerose on 31/01/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mushroom.waylf.library.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import com.mushroom.waylf.library.Request;

public class Home extends Activity implements View.OnClickListener {

    // Progress Dialog
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    private EditText mSearch;
    private Button mFind, mList;
    //final String EXTRA_LOGIN = "user_login";
    private String result;

    private static String URL_Request;
    //= "http://www.omdbapi.com/?t=iron+man&y=&plot=short&r=json";


    //JSON element ids from repsonse of php script:
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //setup input fields
        mSearch = (EditText)findViewById(R.id.search);

        //setup buttons
        mFind = (Button)findViewById(R.id.find);
        mList = (Button)findViewById(R.id.listAlreadyWatch);

        //register listeners
        mFind.setOnClickListener(this);
        mList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.find:
                String search = mSearch.getText().toString();
                URL_Request = new Request().SearchListRequest(search);
                //new RequestTask().execute("http://www.omdbapi.com/?t=iron+man&y=&plot=short&r=json");
                new AttemptRequest().execute();
                Intent i = new Intent(this, Film.class);
                //i.putExtra(res, result);
                startActivity(i);
                break;
            case R.id.listAlreadyWatch:
                //Intent j = new Intent(this, Serie.class);
                //startActivity(j);
                break;

            default:
                break;
        }
    }
    /*
    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    Log.d("Login Successful!", out.toString());
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }
    */
    class AttemptRequest extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Search...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        URL_Request, "GET", params);

                // check your log for json response
                Log.d("Request attempt", json.toString());
                result = json.toString();
                Log.d("Request Successful!", json.toString());
                return json.getString(TAG_MESSAGE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(Home.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}

