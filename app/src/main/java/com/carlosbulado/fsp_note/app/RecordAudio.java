package com.carlosbulado.fsp_note.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;

public class RecordAudio
{
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    private static String audioFilePath;
    private boolean isRecording = false;
    private PackageManager pmanager;
    private Activity context;

    public RecordAudio(Activity context)
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(context, new String[] { Manifest.permission.RECORD_AUDIO }, 0);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(context, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }

    public RecordAudio(PackageManager pmanager, Activity context, String noteId)
    {
        this(context);
        this.pmanager = pmanager;
        this.context = context;
        this.audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + noteId + ".3gp";
    }

    public boolean hasMicrophone()
    {
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

    public void recordAudio () throws IOException
    {
        isRecording = true;

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mediaRecorder.start();
        Toast.makeText(context, "Initiate recording...", Toast.LENGTH_SHORT).show();
    }

    public void stopAudio ()
    {
        if(mediaRecorder != null)
        {
            if (isRecording)
            {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                isRecording = false;
            }
            else
            {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }

    public void playAudio () throws IOException
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public boolean isRecording()
    {
        return this.isRecording;
    }

    public String audioFilePath()
    {
        return this.audioFilePath;
    }
}
