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
import android.widget.EditText;

public class NewPostActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ContentValues values;

    private EditText titleText;
    private EditText descText;
    private EditText priceText;

    private String errorCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
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
        errorCondition = "";

        // Gets the data repository in write mode
        PostsDbHelper mDbHelper = new PostsDbHelper(this);
        db = mDbHelper.getWritableDatabase();
    }

    private void showSnackBar() {
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), formatErrorCondition(),
                Snackbar.LENGTH_SHORT).show();
    }

    private void clearErrors() {
        errorCondition = "";
    }

    private void addErrorCondition(int type, String field) {
        if (type != 0)
            return;
        if (!errorCondition.isEmpty())
            errorCondition += ", ";
        errorCondition += field;
        //System.out.println("DEBUG - EC:=" + errorCondition);
    }

    private String formatErrorCondition() {
        String result = getResources().getString(R.string.new_post_snackbar);
        if (!errorCondition.isEmpty()) {
            // TODO: learn how to insert variables in output strings with proper localization
            // For now, just output a fixed error string, assuming a blank field was caught.
            result = getResources().getString(R.string.error_post_snackbar);
        }
        return result;
    }

    private void addPost() {
        // hide the soft keyboard in any case
        HideKeyboard.with(this);

        // Input the String values for validation
        String strTitle = titleText.getText().toString().trim();
        String strDescription = descText.getText().toString().trim();
        String strPrice = priceText.getText().toString().trim();
//        System.out.print("DEBUG! ");
//        System.out.print("Title=<"+strTitle);
//        System.out.print(">, Price=<"+strPrice);
//        System.out.print(">, Desc=<"+strDescription);
//        System.out.println(">\n");

        // Simple validation - no empty fields, if any detected, set error status for snack bar and return
        clearErrors();
        if (strTitle.isEmpty()) {
            addErrorCondition(0, "Title");
            titleText.setText(""); // make sure any white space is cleared too
        }
        if (strDescription.isEmpty()) {
            addErrorCondition(0, "Description");
            descText.setText(""); // make sure any white space is cleared too
        }
        if (strPrice.isEmpty()) {
            addErrorCondition(0, "Price");
            priceText.setText(""); // make sure any white space is cleared too
        }
        if (!errorCondition.isEmpty()) {
            return;
        }

        // Create a new map of values, where column names are the keys
        values = new ContentValues();
        values.put(Posts.PostEntry.COLUMN_NAME_TITLE, strTitle);
        values.put(Posts.PostEntry.COLUMN_NAME_DESCRIPTION, strDescription);
        values.put(Posts.PostEntry.COLUMN_NAME_PRICE, strPrice);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                Posts.PostEntry.TABLE_NAME,
                null,
                values);
        System.out.print("Post New Item returned ID=");
        System.out.print(newRowId);
        System.out.println("\n");

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
            addPost();
            showSnackBar();
        }
        return super.onOptionsItemSelected(item);
    }
}
