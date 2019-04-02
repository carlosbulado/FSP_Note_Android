package com.carlosbulado.fsp_note.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.adapter.AllNotesAdapter;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private AllNotesAdapter adapter;
    private ArrayList<Note> allNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("All Notes");
        this.recyclerView = findViewById(R.id.allNotesGrid);

        // Fetch all Subjects
        this.allNotes = APP.Services.noteService.getRepository().getAll();

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
