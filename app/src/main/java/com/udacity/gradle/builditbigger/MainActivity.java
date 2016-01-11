package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import edu.udacity.android.android_joke_activity.JokeDisplayActivity;
import edu.udacity.model.Joke;
import edu.udacity.provider.jokes.JokeProvider;


public class MainActivity extends ActionBarActivity {
    private static final String JOKE_CONTENT_ATTR_NAME = "jokeContent";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void tellJoke(View view){
        Joke joke = JokeProvider.getJoke();
        Log.i(TAG, String.format("The retrieved joke is : %s", joke));

        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JOKE_CONTENT_ATTR_NAME, joke.getContent());
        startActivity(intent);
    }
}
