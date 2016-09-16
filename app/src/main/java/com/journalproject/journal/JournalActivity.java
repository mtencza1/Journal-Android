package com.journalproject.journal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;



public class JournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.journal_container);

        if(fragment==null){
            fragment = new JournalFragment();
            fm.beginTransaction().add(R.id.journal_container,fragment).commit();
        }
    }
    public static Intent newIntent(Context context){
        Intent i = new Intent(context,JournalActivity.class);
        return i;
    }
}
