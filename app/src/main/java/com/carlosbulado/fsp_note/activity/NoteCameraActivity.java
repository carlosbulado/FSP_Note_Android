package com.carlosbulado.fsp_note.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.app.CameraSurfaceView;

import java.io.FileOutputStream;

public class NoteCameraActivity extends AppCompatActivity
{
    CameraSurfaceView cameraView;
    FrameLayout frame;
    Button capture;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_camera);

        if(NotePageActivity.note != null)
        {
            cameraView = new CameraSurfaceView(getApplicationContext(), NotePageActivity.note.getId() + ".jpg");
            frame = findViewById(R.id.note_camera_frame);
            frame.addView(cameraView);
            capture = findViewById(R.id.note_camera_capture);

            cameraView.capture(new Camera.PictureCallback()
            {
                public void onPictureTaken(byte[] data, Camera camera)
                {
                    Log.v("Still", "Image data received from camera");
                    FileOutputStream fos;
                    try
                    {
                        String pathForAppFiles = getFilesDir()
                                .getAbsolutePath();
                        pathForAppFiles = pathForAppFiles + "/"
                                + cameraView.getImageFile();
                        Log.d("Still image filename:", pathForAppFiles);
                        fos = openFileOutput(cameraView.getImageFile(),
                                MODE_PRIVATE);
                        fos.write(data);
                        fos.close();
                    } catch (Exception e)
                    {
                        Log.e("Still", "Error writing file", e);
                    }
                }
            });

            capture.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    NotePageActivity.notePageActivity.text.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.common_full_open_on_phone), null);
                    back();
                }
            });
        }
    }

    public void back()
    {
        Intent nextPage = new Intent(this, NotePageActivity.class);
        nextPage.putExtra("noteId", NotePageActivity.note.getId());
        this.startActivity(nextPage);
    }
}
