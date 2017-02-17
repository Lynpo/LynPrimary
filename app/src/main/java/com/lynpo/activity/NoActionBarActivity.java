package com.lynpo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lynpo.R;

public class NoActionBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_action_bar);
    }
}
