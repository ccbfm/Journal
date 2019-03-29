package com.ccbfm.journal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ccbfm.journal.annotation.Journal;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start("qwer", 123);
        new Test();
        new Thread(){
            @Journal
            @Override
            public void run() {
                super.run();
                Log.d("MainActivity","run");
            }
        }.start();
    }

    @Journal
    public String start(String str, int ii){
        Log.d("MainActivity","start");
        return str + ii;
    }

}
