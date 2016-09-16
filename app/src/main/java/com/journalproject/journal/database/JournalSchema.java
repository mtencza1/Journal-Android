package com.journalproject.journal.database;


public class JournalSchema {

    public static final class Journal{
        public static final String NAME = "Journal";
        public static final class Columns{
            public static final String UUID = "uuid";
            public static final String ENTRY = "entry";
            public static final String DATE = "date";
        }
    }
}
