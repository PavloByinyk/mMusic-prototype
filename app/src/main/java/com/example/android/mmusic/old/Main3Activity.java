package com.example.android.mmusic.old;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.mmusic.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main3Activity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {


    ImageButton btnPlayPause;
    TextView timer;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;
    int mediaFileLenght;
    int realTimeLenght;
    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        seekBar = findViewById(R.id.seek_bar);
        seekBar.setMax(99);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mediaPlayer.isPlaying()){

                    SeekBar seekBar = (SeekBar) view;
                    int position = (mediaFileLenght/1000) * seekBar.getProgress();
                    mediaPlayer.seekTo(position);
                }
                return false;
            }
        });




        timer = findViewById(R.id.textTimer);

        btnPlayPause = findViewById(R.id.btn_play_pause);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(Main3Activity.this);


                AsyncTask<String, String, String> mp3Play = new AsyncTask<String, String, String>() {


                    @Override
                    protected void onPreExecute() {
                        progressDialog.setMessage("Preparing");
                        progressDialog.show();
                    }

                    @Override
                    protected String doInBackground(String... strings) {

                        try {
                            mediaPlayer.setDataSource(strings[0]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        return "";

                    }


                    @Override
                    protected void onPostExecute(String s) {

                        mediaFileLenght = mediaPlayer.getDuration();
                        realTimeLenght = mediaFileLenght;

                        if(!mediaPlayer.isPlaying()){
                            mediaPlayer.start();
                            btnPlayPause.setImageResource(R.drawable.ic_pause_black_24dp);
                        }else {
                            mediaPlayer.pause();
                            btnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        }

                        updateSeekBar();
                        progressDialog.dismiss();

                    }
                };

                mp3Play.execute("http://mic.duytan.edu.vn:86/ncs.mp3");
            }
        });


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }


    public void updateSeekBar(){
        seekBar.setProgress((int) (((float)mediaPlayer.getCurrentPosition()/mediaFileLenght) * 100));

         if(mediaPlayer.isPlaying()){

             Runnable updater = new Runnable() {
                 @Override
                 public void run() {

                    updateSeekBar();
                    realTimeLenght -= 1000;
                    timer.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(realTimeLenght),
                            TimeUnit.MILLISECONDS.toSeconds(realTimeLenght) - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(realTimeLenght))
                             ));

                 }
             };

             handler.postDelayed(updater, 1000);

         }

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        btnPlayPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
         seekBar.setSecondaryProgress(i);
    }
}
