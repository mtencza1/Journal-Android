package com.journalproject.journal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.UUID;

public class EntryActivity extends AppCompatActivity {

    private static final String EXTRA_ENTRY_ID = "com.journalproject.journal.entry_id";
    private ViewPager viewPager;
    private JournalFragment.EntryList entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        entryList = JournalFragment.EntryList.get(this);

        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Entry entry = entryList.get(position);
                return EntryFragment.newInstance(entry.getId());
            }

            @Override
            public int getCount() {
                return entryList.size();
            }
        });
        UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_ENTRY_ID);

        for(int i=0;i<entryList.size();i++){
            if(entryList.get(i).getId().equals(id)){
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context context, UUID entryId){
        Intent i = new Intent(context,EntryActivity.class);
        i.putExtra(EXTRA_ENTRY_ID,entryId);
        return i;
    }
}
