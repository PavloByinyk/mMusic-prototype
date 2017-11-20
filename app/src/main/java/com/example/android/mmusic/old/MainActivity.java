package com.example.android.mmusic.old;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.mmusic.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    Button btnStart;
    MediaPlayer mediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaplayer = new MediaPlayer();

        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
    }


    public void play(){

//        MediaPlayer mp = new MediaPlayer();
//        try {
//            mp.setDataSource("http://vprbbc.streamguys.net/vprbbc24.mp3");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.start();
//            }
//
//
//        });
//        mp.prepareAsync();


        Uri myUri = Uri.parse("https://www.ssaurel.com/tmp/mymusic.mp3");

        try {
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaplayer.setDataSource(getApplicationContext(), myUri);

// or just mediaPlayer.setDataSource(mFileName);

            mediaplayer.prepare(); // must call prepare first

        mediaplayer.start(); // then start
        } catch (Exception e) {
            e.printStackTrace();
        }



//        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaplayer.prepareAsync();
//        mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mediaplayer.start();
//            }
//        });

//        try {
//            mediaplayer = new MediaPlayer();
//            mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaplayer.setDataSource("http://vprbbc.streamguys.net/vprbbc24.mp3");
//            mediaplayer.prepare();
//            mediaplayer.start();
//        } catch (Exception e) {
//            // TODO: handle exception
//        }

    }
}
