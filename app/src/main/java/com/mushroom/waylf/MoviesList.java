package com.mushroom.waylf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mushroom.waylf.library.Request;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by antoinerose on 01/02/2015.
 */

public class MoviesList extends Activity {

    ListView liste = null;
    TextView test = null;
    List<String>  idMovie= null;
    final String EXTRA = "test";
    final String EXTRA_ID="MovieId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);


        liste = (ListView) findViewById(R.id.listView);
        test = (TextView) findViewById(R.id.textView);
        Request request = new Request();

        // A changer
        //String json = "{\"Search\":[{\"Title\":\"Iron Man\",\"Year\":\"2008\",\"imdbID\":\"tt0371746\",\"Type\":\"movie\"},{\"Title\":\"Iron Man 3\",\"Year\":\"2013\",\"imdbID\":\"tt1300854\",\"Type\":\"movie\"}]}";
        List<String> Movie = null;

        Intent intent = getIntent();
        String json = intent.getExtras().getString(EXTRA);


        try {
            Movie = NewListMovie(request.ReadJsonList(json));
            idMovie = NewIdList(request.ReadJsonList(json));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Movie);
            liste.setAdapter(adapter);
            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view,
                                        int position,
                                        long id) {
                    Intent intentMovie = new Intent(MoviesList.this,MovieDescription.class);
                    intentMovie.putExtra(EXTRA_ID,idMovie.get(position));
                    startActivity(intentMovie);
                    //ouvrir fenetre avec changement envois de l'id
                    //test.setText(idMovie.get(position));
                    // Que faire quand on clique sur un élément de la liste ?

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<String> NewListMovie(List<String> list)
    {
        List<String> Movie = new ArrayList<String>();
        for(int i = 0; i<list.size();i++){
            String delims = "[;]+";
            String[] tokens = list.get(i).split(delims);
            Movie.add(i,tokens[0] +" (" + tokens[1]+")");
    }
        return Movie;
    }

    public List<String> NewIdList(List<String> list)
    {
        List<String> Movie = new ArrayList<String>();
        for(int i = 0; i<list.size();i++){
            String delims = "[;]+";
            String[] tokens = list.get(i).split(delims);
            Movie.add(i,tokens[2]);
        }
        return Movie;
    }



}

