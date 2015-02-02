package com.mushroom.waylf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mushroom.waylf.library.Request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MovieDescription extends ActionBarActivity {
    final String EXTRA_ID="MovieId";
    private String MovieId;
    private String MovieName;
    private String MovieYear;
    private String MovieRuntime;
    private String MovieGenre;
    private String MoviePlot;
    private String MoviePoster;
    private Request MovieRequest = new Request();

    final String EXTRA_USERID = "userid";
    private String userIdP = "";

    TextView NameTv;
    TextView YearTv;
    TextView RuntimeTv;
    TextView GenreTv;
    TextView PlotTv;
    private ProgressDialog pDialog;
    private static String URL_Request;
    public String response ="";
    com.mushroom.waylf.library.JSONParser jsonParser = new com.mushroom.waylf.library.JSONParser();
    CheckBox FilmVu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        Intent intent = getIntent();
        MovieId = intent.getStringExtra(EXTRA_ID);
        userIdP = intent.getStringExtra(EXTRA_USERID);


        URL_Request = MovieRequest.SearchIdRequest(MovieId);
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

        try {
            InitializeMovieData(MovieId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FilmVu = (CheckBox) findViewById(R.id.checkBox);
        FilmVu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                new AttemptCheckVu().execute();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   public void InitializeMovieData(String id) throws JSONException {
       //String idMovie = MovieRequest.SearchMovieRequest(id);

       //String Json = "{\"Title\":\"Frozen\",\"Year\":\"2013\",\"Rated\":\"PG\",\"Released\":\"27 Nov 2013\",\"Runtime\":\"102 min\",\"Genre\":\"Animation, Adventure, Comedy\",\"Director\":\"Chris Buck, Jennifer Lee\",\"Writer\":\"Jennifer Lee (screenplay), Hans Christian Andersen (inspired by the story \\\"The Snow Queen\\\" by), Chris Buck (story), Jennifer Lee (story), Shane Morris (story), Dean Wellins (additional story)\",\"Actors\":\"Kristen Bell, Idina Menzel, Jonathan Groff, Josh Gad\",\"Plot\":\"When a princess with the power to turn things into ice curses her home in infinite winter, her sister, Anna teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.\",\"Language\":\"English, Icelandic\",\"Country\":\"USA\",\"Awards\":\"Won 2 Oscars. Another 68 wins & 53 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQ1MjQwMTE5OF5BMl5BanBnXkFtZTgwNjk3MTcyMDE@._V1_SX300.jpg\",\"Metascore\":\"74\",\"imdbRating\":\"7.7\",\"imdbVotes\":\"300,466\",\"imdbID\":\"tt2294629\",\"Type\":\"movie\",\"Response\":\"True\"}";
       String Json = response;
       Log.d("json", Json);
       //connexion base de donnée
       //Récupération du JSON
       JSONObject JsonObj = new JSONObject();

       //Récupération des données parser par json
       JsonObj = MovieRequest.ReadJsonMovie(Json);

       //Mise en place des données
       NameTv = (TextView) findViewById(R.id.textViewTitle);
       YearTv = (TextView) findViewById(R.id.textViewYear);
       GenreTv = (TextView) findViewById(R.id.textViewGenre);
       RuntimeTv = (TextView) findViewById(R.id.textViewRuntime);
       PlotTv = (TextView) findViewById(R.id.textViewPlot);

       NameTv.setText(JsonObj.getString("Title"));
       YearTv.setText(JsonObj.getString("Year"));
       GenreTv.setText(JsonObj.getString("Genre"));
       RuntimeTv.setText(JsonObj.getString("Runtime"));
       PlotTv.setText(JsonObj.getString("Plot"));

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
            pDialog = new ProgressDialog(MovieDescription.this);
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

    class AttemptCheckVu extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MovieDescription.this);
            pDialog.setMessage("Save Movie...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            String LOGIN_URL = "http://10.0.2.2:8888/webservice/check.php";
            final String TAG_SUCCESS = "success";
            final String TAG_MESSAGE = "message";
            String omdbId = MovieId;
            String userId = userIdP;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("omdbId", omdbId));
                params.add(new BasicNameValuePair("userId", userId));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d(" success", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
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

            }

        }

    }
}
