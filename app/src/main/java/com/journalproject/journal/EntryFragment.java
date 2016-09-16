package com.journalproject.journal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;


public class EntryFragment extends Fragment {

    public static final String ARG_ENTRY_ID = "entry_id";
    private Entry entry;
    private TextView date;
    private EditText editText;
    private Button submit_button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID entry_id = (UUID)getArguments().getSerializable(ARG_ENTRY_ID);
        entry = JournalFragment.EntryList.get(getActivity()).getEntry(entry_id);
    }
    @Override
    public void onPause(){
        super.onPause();
        JournalFragment.EntryList.get(getActivity()).updateEntry(entry);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry,container,false);

        date = (TextView)view.findViewById(R.id.date);
        date.setText(entry.getDate());

        editText = (EditText)view.findViewById(R.id.editText);
        submit_button = (Button)view.findViewById(R.id.submit_button);

        if(entry.getEntry()!=null && !entry.getEntry().isEmpty()){
            editText.setEnabled(false);
            editText.setText(entry.getEntry());
            submit_button.setEnabled(false);
        }
        else{
            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String description = (String)editText.getText().toString();
                    entry.setEntry(description);
                    editText.setEnabled(false);
                    submit_button.setEnabled(false);
                }
            });
        }
        return view;
    }
    public static EntryFragment newInstance(UUID id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ENTRY_ID,id);

        EntryFragment entryFragment = new EntryFragment();
        entryFragment.setArguments(args);

        return entryFragment;
    }
    public EntryFragment() {
    }
}
