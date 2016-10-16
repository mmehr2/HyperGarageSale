package com.example.hypergaragesale;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

// This is called NewPostActivity in the canonical project
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return; // not sure if this is the best action, but it avoids the compiler warning
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void showSnackBar() {
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.new_post_snackbar,
                Snackbar.LENGTH_SHORT).show();
    }

    public void newItemAdded(View v) {
        showSnackBar();
        System.out.println("Pressed Post - NewItem button");
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
        }
        return super.onOptionsItemSelected(item);
    }
}
