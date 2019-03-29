package com.ccbfm.journal;

import android.util.Log;

import com.ccbfm.journal.annotation.Journal;

public class Test {

    @Journal
    public Test() {
        Log.d("Test", "Test");
    }

    @Journal
    public void test() {
        Log.d("Test", "Test");
    }
}
