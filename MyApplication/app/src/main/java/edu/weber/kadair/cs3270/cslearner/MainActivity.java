package edu.weber.kadair.cs3270.cslearner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
    //global variables
    BlogList blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Log.d("test", "Creating fragments.");
            blogList = new BlogList();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainLayout, blogList, "mainLayout")
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        } else {
            Log.d("test", "Replacing fragments.");
            blogList = new BlogList();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainLayout, blogList, "mainLayout")
                    .addToBackStack(null)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainLayout, new BlogInsert())
                        .addToBackStack(null)
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
                Log.d("test", "Clicked FAB button");
            }
        });
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

        if(id == R.id.import_blogs){
            Log.d("test", "Clicked import_blogs");
            new getBlogs().execute("");
        }

        return super.onOptionsItemSelected(item);
    }

    protected class getBlogs extends AsyncTask<String, Integer, String> {
        //Token used to get data
        String AUTH_TOKEN = " ";
        String rawJson = "";
        //Asynchronous method
        @Override
        protected String doInBackground(String... params){
            Log.d("test", "doInBackground");
            try{
                Log.d("test", "doInBackground");
                URL url = new URL("https://www.cslearner.com/blogEntries.json");
                Log.d("test", "new URL, connecting to URL");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                Log.d("test", "setting requestMethod");
                conn.setRequestMethod("GET");
                Log.d("test", "Setting token, authorization, etc");
                conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
                Log.d("test", "attempting to connect");
                conn.connect();
                Log.d("test", "trying to get a response code");
                int status = conn.getResponseCode();
                Log.d("test", "Did it connect to server? " + status);
                switch(status){
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        rawJson = br.readLine();
                }

            }catch(MalformedURLException e){
                Log.d("test", e.getMessage());
                Log.d("test", "Failed to connect to the server");
            }catch(IOException e){
                Log.d("test", "Failed to connect to the server");
            }
            Log.d("test", rawJson);
            return rawJson;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d("test", "onPostExecute");
            try{
                CSLearnerObjects.blogEntries[] myEntries = jsonParse(result);
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "blogs", null, 1);
                for(CSLearnerObjects.blogEntries blog: myEntries){
                    Log.d("test", "blogEntryID " + blog.blogEntryID);
                    Log.d("test", "blogDate " + blog.blogDate);
                    Log.d("test", "blogTitle " + blog.blogTitle);
                    Log.d("test", "blogText " + blog.blogText);
                    Long rowID = dbHelper.insertBlog(
                            blog.blogEntryID + "",
                            blog.blogDate,
                            blog.blogTitle,
                            blog.blogText
                    );
                    Log.d("test", String.valueOf(rowID));
                }
                //TODO: reloadBlogList();
            }catch(Exception e){

            }
        }

        private CSLearnerObjects.blogEntries[] jsonParse(String rawJson){
            Log.d("test", "jsonParse method starting");
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            CSLearnerObjects.blogEntries[] blogs = null;
            try{
                blogs = gson.fromJson(rawJson, CSLearnerObjects.blogEntries[].class);
                Log.d("test", "parsed Json");
            }catch(Exception e){
                Log.d("test", e.getMessage());
                Log.d("test", "failed to parse Json");
            }
            return blogs;
        }
    }
}
