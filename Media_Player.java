package com.arashad96.androiddeveloperintermidatekit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class Media_Player extends AppCompatActivity {
    Button github;
    Button info;
    Button play;
    Button pause;
    SeekBar volumecontroller;
    SeekBar audiocontroller;
    MediaPlayer mplayer;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_media__player);

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        volumecontroller = findViewById(R.id.volumecontroller);
        audiocontroller = findViewById(R.id.audiocontroller);

        //location of the music
        mplayer = MediaPlayer.create(getApplicationContext(), R.raw.bensound_summer);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentvol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        int duration = mplayer.getDuration();

        volumecontroller.setMax(maxvol);
        volumecontroller.setProgress(currentvol);

        volumecontroller.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        audiocontroller.setMax(duration);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audiocontroller.setProgress(mplayer.getCurrentPosition());
            }
        }, 0, 1000);

        audiocontroller.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mplayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mplayer.start();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mplayer.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mplayer.pause();
            }
        });


        github = findViewById(R.id.github);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ARashad96/Media_Player"));
                startActivity(intent);
            }
        });
        info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(Media_Player.this)
                        .setIcon(R.drawable.profile)
                        .setTitle("App info")
                        .setMessage("This app is a small media player with basic controls using textview, seekbar, buttons, mediaplayer, audiomanager and linearlayout.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }
}
