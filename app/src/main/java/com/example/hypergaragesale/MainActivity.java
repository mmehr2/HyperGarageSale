package com.example.hypergaragesale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newItemAdded(View v) {
        // TODO - add code for new items here
        System.out.println("Pressed Post - NewItem button");
    }
}
