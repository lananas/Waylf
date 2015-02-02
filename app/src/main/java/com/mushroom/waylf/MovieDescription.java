package com.mushroom.waylf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mushroom.waylf.library.Request;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class MovieDescription extends ActionBarActivity {
    final String EXTRA_ID="MovieId";
    private String MovieId;
    private String MovieName;
    private String MovieYear;
    private String MovieRuntime;
    private String MovieGenre;
    private String MoviePlot;
    private String MoviePoster;
    private Request MovieRequest;

    TextView NameTv;
    TextView YearTv;
    TextView RuntimeTv;
    TextView GenreTv;
    TextView PlotTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);


        Intent intent = getIntent();
        MovieId = intent.getStringExtra(EXTRA_ID);
        try {
            InitializeMovieData(MovieId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
       String Json = "{\"Title\":\"Frozen\",\"Year\":\"2013\",\"Rated\":\"PG\",\"Released\":\"27 Nov 2013\",\"Runtime\":\"102 min\",\"Genre\":\"Animation, Adventure, Comedy\",\"Director\":\"Chris Buck, Jennifer Lee\",\"Writer\":\"Jennifer Lee (screenplay), Hans Christian Andersen (inspired by the story \\\"The Snow Queen\\\" by), Chris Buck (story), Jennifer Lee (story), Shane Morris (story), Dean Wellins (additional story)\",\"Actors\":\"Kristen Bell, Idina Menzel, Jonathan Groff, Josh Gad\",\"Plot\":\"When a princess with the power to turn things into ice curses her home in infinite winter, her sister, Anna teams up with a mountain man, his playful reindeer, and a snowman to change the weather condition.\",\"Language\":\"English, Icelandic\",\"Country\":\"USA\",\"Awards\":\"Won 2 Oscars. Another 68 wins & 53 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQ1MjQwMTE5OF5BMl5BanBnXkFtZTgwNjk3MTcyMDE@._V1_SX300.jpg\",\"Metascore\":\"74\",\"imdbRating\":\"7.7\",\"imdbVotes\":\"300,466\",\"imdbID\":\"tt2294629\",\"Type\":\"movie\",\"Response\":\"True\"}";
       //connexion base de donnée
       //Récupération du JSON
       JSONObject JsonObj = new JSONObject();

       //Récupération des données parser par json
       JsonObj = ReadJsonMovie(Json);

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
    public JSONObject ReadJsonMovie(String JsonMovie) throws JSONException {
        JSONObject json = null;
        try {
            json = (JSONObject)new JSONParser().parse(JsonMovie);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }
}
