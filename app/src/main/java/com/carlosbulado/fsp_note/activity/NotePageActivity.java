package com.carlosbulado.fsp_note.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Note;

public class NotePageActivity extends AppCompatActivity
{
    EditText title;
    EditText text;
    String noteId;
    Note note;
    boolean isNew;
    static NotePageActivity notePageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);
        setTitle("New Note");

        this.isNew = true;
        this.title = findViewById(R.id.note_page_title);
        this.text = findViewById(R.id.note_page_text);
        this.note = new Note();
        NotePageActivity.notePageActivity = this;

        Intent previousPage = getIntent();
        Bundle mBundle = previousPage.getExtras();
        this.noteId = mBundle != null ? mBundle.getString("noteId") : "";
        if(this.noteId != null && !this.noteId.isEmpty())
        {
            this.note = APP.Services.noteService.getRepository().getById(this.noteId);
            setTitle("Edit Note");
            this.isNew = false;
        }

        this.loadNoteInfo();
    }

    private void loadNoteInfo()
    {
        this.title.setText(this.note.getTitle());
        this.text.setText(this.note.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_note_page_activity, menu);
        if(this.isNew) menu.getItem(3).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_note_page_save:
                boolean valid = true;

                if(this.text.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Put some text in your note!", Toast.LENGTH_SHORT).show();
                    valid = false;
                }
                if(this.title.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Put a title in your note!", Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                if(valid)
                {
                    this.note.setText(this.text.getText().toString());
                    this.note.setTitle(this.title.getText().toString());

                    APP.Services.noteService.save(this.note);

                    APP.goTo(this, MainActivity.class);
                }
                break;
            case R.id.menu_note_page_cancel:
                APP.goTo(this, MainActivity.class);
                break;
            case R.id.menu_note_page_location:
                Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_note_page_delete:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Do you want to remove the note?");
                alertDialog.setMessage("This note will not be available once it's removed!");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                APP.Services.noteService.delete(notePageActivity.note);
                                APP.goTo(notePageActivity, MainActivity.class);
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
