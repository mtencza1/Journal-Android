package com.journalproject.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JournalTitleActivity extends AppCompatActivity {


        private TextView my_journal;
        private Button enter_button;
        private ImageView journal_title_image;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_journal_title);

            my_journal = (TextView)findViewById(R.id.my_journal);
            my_journal.setText(R.string.journal);

            enter_button = (Button)findViewById(R.id.enter_btn);
            enter_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = JournalActivity.newIntent(JournalTitleActivity.this);
                    startActivity(i);
                }
            });
            journal_title_image = (ImageView)findViewById(R.id.journal_title_image);
        }
}
