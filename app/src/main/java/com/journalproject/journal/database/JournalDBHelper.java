package com.journalproject.journal.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JournalDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "Journal.db";

    public JournalDBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + JournalSchema.Journal.NAME
        + "(" + " _id integer primary key autoincrement, " +
                        JournalSchema.Journal.Columns.UUID+ ", " +
                        JournalSchema.Journal.Columns.ENTRY+", " +
                        JournalSchema.Journal.Columns.DATE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
