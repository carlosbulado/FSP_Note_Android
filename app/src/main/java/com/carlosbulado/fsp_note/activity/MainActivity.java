package com.carlosbulado.fsp_note.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.adapter.AllNotesAdapter;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Category;
import com.carlosbulado.fsp_note.domain.Note;
import com.carlosbulado.fsp_note.repository.NoteRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private AllNotesAdapter adapter;
    private ArrayList<Note> allNotes;
    private Spinner spinner;
    private EditText search;
    private int positionOfSort = 0;
    private String textOfSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("All Notes");
        this.recyclerView = findViewById(R.id.allNotesGrid);
        this.search = findViewById(R.id.all_notes_search);

        spinner = findViewById(R.id.all_notes_sort);
        String[] items = new String[] { "Title A -> Z", "Title Z -> A" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        this.loadAllNotes();

        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                textOfSearch = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                loadAllNotes();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                positionOfSort = position;
                loadAllNotes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void loadAllNotes()
    {
        this.allNotes = ((NoteRepository)APP.Services.noteService.getRepository()).getAll(textOfSearch);
        switch (this.positionOfSort)
        {
            case 0:
                Collections.sort(allNotes, new Comparator<Note>(){
                    public int compare(Note p1, Note p2){
                        return p1.getTitle().compareTo(p2.getTitle());
                    }
                });
                break;
            case 1:
                Collections.sort(allNotes, new Comparator<Note>(){
                    public int compare(Note p1, Note p2){
                        return p2.getTitle().compareTo(p1.getTitle());
                    }
                });
                break;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayout.VERTICAL));
        this.adapter = new AllNotesAdapter(this, this.allNotes);
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume()
    {
        this.allNotes = APP.Services.noteService.getRepository().getAll();
        this.recyclerView.refreshDrawableState();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_note_list_activity, menu);
        menu.getItem(1).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_note_list_new:
                APP.goTo(this, NotePageActivity.class);
                break;
            case R.id.menu_note_list_categories:
                APP.goTo(this, CategoryListPageActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
