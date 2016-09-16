package com.journalproject.journal;


import android.graphics.drawable.Drawable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Entry {
    private UUID id;

    private String date;
    private String entryText;

    public Entry(String date){
        id = UUID.randomUUID();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy mm:ss:HH");
        Date d = new Date();
        this.date = dateFormat.format(d);
    }
    public Entry(UUID uuid){
        this.id = uuid;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy mm:ss:HH");
        Date d = new Date();
        this.date = dateFormat.format(d);
    }
    public Entry(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy mm:ss:HH");
        Date d = new Date();
        this.date = dateFormat.format(d);
        id = UUID.randomUUID();
    }
    public UUID getId(){
        return id;
    }
    public String getEntry(){
        return entryText;
    }
    public void setEntry(String entryText){
        this.entryText = entryText;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }


}
