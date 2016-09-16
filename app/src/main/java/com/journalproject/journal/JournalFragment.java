package com.journalproject.journal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.journalproject.journal.database.Cwrapper;
import com.journalproject.journal.database.JournalDBHelper;
import com.journalproject.journal.database.JournalSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class JournalFragment extends Fragment{
    private RecyclerView recyclerView;
    private JournalAdapter Adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.journal_list_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_entry_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_new_entry){
            Entry entry = new Entry();
            EntryList.get(getActivity()).addEntry(entry);
            Intent i = EntryActivity.newIntent(getActivity(),entry.getId());
            startActivity(i);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        EntryList entryList = EntryList.get(getActivity());
        ArrayList<Entry> entries = entryList.getEntries();

        if(Adapter ==null){
            Adapter = new JournalAdapter(entryList);
            recyclerView.setAdapter(Adapter);
        }
        else{
            Adapter.setEntryList(entries);
            Adapter.notifyDataSetChanged();
        }
    }


    public static class EntryList {
        private static EntryList entryList;
        private ArrayList<Entry> entryArrayList;
        private Context context;
        private SQLiteDatabase Journal;

        private EntryList(Context context){
            this.context = context.getApplicationContext();
            Journal = new JournalDBHelper(context).getWritableDatabase();
            entryArrayList = new ArrayList<>(100);
        }
        public static EntryList get(Context context){
            if(entryList ==null){
                entryList = new EntryList(context);
            }
            return entryList;
        }
        public ArrayList<Entry> getEntries(){
            ArrayList<Entry> entries = new ArrayList<>();

            Cwrapper c = queryEntries(null,null);

            try{
                c.moveToFirst();
                while(!c.isAfterLast()){
                    entries.add(c.getEntry());
                    c.moveToNext();
                }
            } finally{
                c.close();
            }
            return entries;
        }

        public Entry getEntry(UUID id){
            Cwrapper c = queryEntries(
                    JournalSchema.Journal.Columns.UUID + " =?",
                    new String[] { id.toString()}
            );
            try{
                if(c.getCount() ==0){
                    return null;
                }
                c.moveToFirst();
                return c.getEntry();
            } finally{
                c.close();
            }
        }

        public Entry get(int position){
            return entryList.entryArrayList.get(position);
        }

        public int size(){
            return entryArrayList.size();
        }

        public void addEntry(Entry entry){
            ContentValues values = getContentValues(entry);
            Journal.insert(JournalSchema.Journal.NAME,null,values);
        }

        public void updateEntry(Entry entry){
            String uuid = entry.getId().toString();
            ContentValues values = getContentValues(entry);
            String[] uuidArray = new String[]{uuid};
            Journal.update(JournalSchema.Journal.NAME,values,
                    JournalSchema.Journal.Columns.UUID + "= ?",
                    uuidArray);
        }

        private static ContentValues getContentValues(Entry entry){
            ContentValues values = new ContentValues();
            values.put(JournalSchema.Journal.Columns.UUID,entry.getId().toString());
            values.put(JournalSchema.Journal.Columns.ENTRY, entry.getEntry());
            values.put(JournalSchema.Journal.Columns.DATE,entry.getDate());

            return values;
        }

        private Cwrapper queryEntries(String where,String[] whereArgs){
            Cursor c = Journal.query(
                    JournalSchema.Journal.NAME,
                    null,
                    where,
                    whereArgs,
                    null,
                    null,
                    null
            );
            return new Cwrapper(c);
        }
    }


    private class EntryHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView entry_date;
        private Entry entry;

        public EntryHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            entry_date = (TextView)itemView.findViewById(R.id.entry_date);
        }
        public void bindItem(Entry entry){
            this.entry = entry;
            entry_date.setText(this.entry.getDate());
        }

        @Override
        public void onClick(View view) {
            startActivity(EntryActivity.newIntent(getActivity(), entry.getId()));
        }
    }


    private class JournalAdapter extends RecyclerView.Adapter<EntryHolder>{
        private EntryList entryList;

        public JournalAdapter(EntryList entryList){
            this.entryList = entryList;
        }

        @Override
        public EntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_entry,parent,false);
            return new EntryHolder(view);
        }

        @Override
        public void onBindViewHolder(EntryHolder holder, int position) {
            Entry entry = entryList.entryArrayList.get(position);
            holder.bindItem(entry);
        }

        @Override
        public int getItemCount() {
            return entryList.entryArrayList.size();
        }
        public void setEntryList(ArrayList<Entry> entryList){
            this.entryList.entryArrayList = entryList;
        }
    }
}

