package com.mushroom.waylf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/**
 * Created by antoinerose on 01/02/2015.
 */

public class MoviesList extends Activity {

    final String EXTRA = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);

        Intent intent = getIntent();
        TextView test = (TextView) findViewById(R.id.textReponse);
        if (intent != null) {
            test.setText(intent.getStringExtra(EXTRA));
        }
    }
}

