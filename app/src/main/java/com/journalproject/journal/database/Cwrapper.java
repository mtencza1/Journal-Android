package com.journalproject.journal.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.journalproject.journal.Entry;

import java.util.UUID;


public class Cwrapper extends CursorWrapper {
    public Cwrapper(Cursor cursor) {
        super(cursor);
    }
    public Entry getEntry(){
        String uuid = getString(getColumnIndex(JournalSchema.Journal.Columns.UUID));
        String date = getString(getColumnIndex(JournalSchema.Journal.Columns.DATE));
        String entry = getString(getColumnIndex(JournalSchema.Journal.Columns.ENTRY));

        Entry e = new Entry(UUID.fromString(uuid));
        e.setDate(date);
        e.setEntry(entry);

        return e;
    }
}
