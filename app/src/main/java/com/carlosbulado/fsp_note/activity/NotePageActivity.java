package com.carlosbulado.fsp_note.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.app.RecordAudio;
import com.carlosbulado.fsp_note.domain.Category;
import com.carlosbulado.fsp_note.domain.Note;

import java.util.ArrayList;

public class NotePageActivity extends AppCompatActivity
{
    private RecordAudio recordAudio;

    EditText title;
    EditText text;
    String noteId;
    Spinner allCat;
    static Note note;
    boolean isNew;
    static NotePageActivity notePageActivity;
    MenuItem startRecording;
    MenuItem stopRecording;
    ArrayList<Category> allCatList;

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

        allCat = findViewById(R.id.note_page_spinner);
        allCatList = APP.Services.categoryService.getRepository().getAll();
        String[] items = new String[allCatList.size() + 1];
        int count = 0;
        items[count++] = "Select a Category";
        for (Category cat : allCatList)
        {
            items[count++] = cat.getText();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        allCat.setAdapter(adapter);

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

        this.recordAudio = new RecordAudio(this.getPackageManager(), this, this.note.getId());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, 0);
        }

        Log.i("media file", recordAudio.audioFilePath());
    }

    private void loadNoteInfo()
    {
        this.title.setText(this.note.getTitle());
        this.text.setText(this.note.getText());

        if(this.note.getCategory() != null && !this.note.getCategory().isEmpty())
        {
            int index = 0;
            for(int i = 0 ; i < allCatList.size() ; i++)
                if(allCatList.get(i).getId().equals(this.note.getCategory()))
                    index = i + 1;
            this.allCat.setSelection(index);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_note_page_activity, menu);
        if(this.isNew) menu.getItem(3).setVisible(false);
        menu.getItem(5).setVisible(false);
        startRecording = menu.getItem(4);
        stopRecording = menu.getItem(5);
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
                    if(this.allCat.getSelectedItemPosition() > 0)
                        this.note.setCategory(allCatList.get(this.allCat.getSelectedItemPosition() - 1).getId());
                    else
                        this.note.setCategory("");

                    APP.Services.noteService.save(this.note);

                    APP.goTo(this, MainActivity.class);
                }
                break;
            case R.id.menu_note_page_cancel:
                APP.goTo(this, MainActivity.class);
                break;
            case R.id.menu_note_page_location:
                APP.goTo(this, MapsActivity.class);
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
            case R.id.menu_note_page_record:
                if (!recordAudio.hasMicrophone())
                {
                    Toast.makeText(notePageActivity, "Cannot record audio without microphone.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(recordAudio.isRecording())
                    {
                        recordAudio.stopAudio();
                        startRecording.setVisible(true);
                        stopRecording.setVisible(false);
                    }
                    else
                    {
                        try
                        {
                            recordAudio.recordAudio();
                            startRecording.setVisible(false);
                            stopRecording.setVisible(true);
                        }
                        catch (Exception e) { }
                    }
                }
                break;
            case R.id.menu_note_page_stop_record:
                if (!recordAudio.hasMicrophone())
                {
                    Toast.makeText(notePageActivity, "Cannot record audio without microphone.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(recordAudio.isRecording())
                    {
                        recordAudio.stopAudio();
                        startRecording.setVisible(true);
                        stopRecording.setVisible(false);
                    }
                    else
                    {
                        try
                        {
                            recordAudio.recordAudio();
                            startRecording.setVisible(false);
                            stopRecording.setVisible(true);
                        }
                        catch (Exception e) { }
                    }
                }
                break;
            case R.id.menu_note_page_play_record:
                try
                {
                    recordAudio.playAudio();
                }
                catch(Exception e)
                {
                    Toast.makeText(notePageActivity, "Unable to play recorded audio.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_note_page_camera:
                Intent nextPage = new Intent(this, NoteCameraActivity.class);
                this.startActivity(nextPage);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
