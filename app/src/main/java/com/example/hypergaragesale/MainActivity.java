package com.example.hypergaragesale;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

// This is called NewPostActivity in the canonical project
public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ContentValues values;

    private EditText titleText;
    private EditText descText;
    private EditText priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return; // not sure if this is the best action, but it avoids the compiler warning
        }
        actionBar.setDisplayHomeAsUpEnabled(true);

        titleText = (EditText)findViewById(R.id.itemTitle);
        descText = (EditText)findViewById(R.id.itemDescription);
        priceText = (EditText)findViewById(R.id.itemPrice);

        // Gets the data repository in write mode
        PostsDbHelper mDbHelper = new PostsDbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }

    private void showSnackBar() {
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.new_post_snackbar,
                Snackbar.LENGTH_SHORT).show();
    }

    private void addPost() {
        // Create a new map of values, where column names are the keys
        values = new ContentValues();
        values.put(Posts.PostEntry.COLUMN_NAME_TITLE, titleText.getText().toString());
        values.put(Posts.PostEntry.COLUMN_NAME_DESCRIPTION, descText.getText().toString());
        values.put(Posts.PostEntry.COLUMN_NAME_PRICE, priceText.getText().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                Posts.PostEntry.TABLE_NAME,
                null,
                values);
        System.out.println("Pressed Post - NewItem button");

        // Done adding new entry into database, navigate user back to browsing screen
        startActivity(new Intent(this, BrowsePostsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_post) {
            showSnackBar();
            addPost();
        }
        return super.onOptionsItemSelected(item);
    }
}
