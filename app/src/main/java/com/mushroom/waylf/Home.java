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
import com.mushroom.waylf.library.JSONParser;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


import com.mushroom.waylf.library.Request;

public class Home extends Activity implements View.OnClickListener  {

    // Progress Dialog
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;
    private EditText mSearch;
    private Button mFind, mList;
    public String response ="";
    final String EXTRA = "test";

    private static String URL_Request;

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
                search = search.replaceAll("\\s","+");
                URL_Request = new Request().SearchListRequest(search);
                AttemptRequest attemptRequest = new AttemptRequest();
                attemptRequest.execute();
                try {
                    response = attemptRequest.get().toString();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Intent i = new Intent(this, MoviesList.class);
                i.putExtra(EXTRA,response);
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

    private void processValue(String myValue)
    {
        this.response = myValue;
        Log.d("Request response", this.response);
    }

    class AttemptRequest extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */

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

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            Log.d("request!", "starting");
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    URL_Request, "GET", params);

            // check your log for json response
            Log.d("Request attempt", json.toString());

            return json.toString();

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String result) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();

            // return result
            if (result != null){
                processValue(result);
            }


        }


    }


}

