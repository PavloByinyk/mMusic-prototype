//package com.example.android.mmusic;
//
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.example.android.mmusic.pojo.Track;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class DemoActivity extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MusicAdapter.OnMusicAdapterListener {
//
//
//    Button btnStart;
//    SeekBar seekBar;
//    private boolean playPause;
//    private MediaPlayer mediaPlayer;
//    private ProgressDialog progressDialog;
//    private boolean initialStage = true;
//    TextView tvTimer;
//
//
//    int mediaFileLenght;
//    int realTimeLenght;
//    int bufferingLenght;
//    Handler handler = new Handler();
//
//    private List<Track> trackList = Arrays.asList(
//            new Track("First Track", "03:47", "http://desolate-tor-17567.herokuapp.com/audio/T1.mp3"),
//            new Track("Second Track", "02:47", "https://www.ssaurel.com/tmp/mymusic.mp3"),
//            new Track("Third Track", "01:47", "http://mic.duytan.edu.vn:86/ncs.mp3"));
//
//    private MusicAdapter musicAdapter;
//    private RecyclerView recyclerView;
//
//
//
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_demo);
//
//        tvTimer = (TextView) findViewById(R.id.tv_timer);
//        btnStart = (Button) findViewById(R.id.btnPlay);
//        seekBar = (SeekBar) findViewById(R.id.seek_bar);
//
//        seekBar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(mediaPlayer.isPlaying()){
//
//                    SeekBar seekBar = (SeekBar) view;
//
//                    int currentPos = seekBar.getProgress();
//
//                    if(currentPos < bufferingLenght){
//                        mediaPlayer.seekTo((currentPos * mediaFileLenght)/ seekBar.getMax());
//
//                    }
//                }
//                return false;
//            }
//        });
//
//
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.setOnBufferingUpdateListener(this);
//        mediaPlayer.setOnCompletionListener(this);
//
//        progressDialog = new ProgressDialog(this);
//
//
//        musicAdapter = new MusicAdapter(trackList, this, this);
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(musicAdapter);
//
//
//
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!playPause) {
//                    btnStart.setText("Pause Streaming");
//
//                    if (initialStage) {
//                        new Player().execute("http://desolate-tor-17567.herokuapp.com/audio/T1.mp3");
//                    } else {
//                        if (!mediaPlayer.isPlaying())
//                            mediaPlayer.start();
//                    }
//
//                    playPause = true;
//
//                } else {
//                    btnStart.setText("Launch Streaming");
//
//                    if (mediaPlayer.isPlaying()) {
//                        mediaPlayer.pause();
//                    }
//
//                    playPause = false;
//                }
//            }
//        });
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (mediaPlayer != null) {
//            mediaPlayer.reset();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
//
//    @Override
//    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
//            bufferingLenght = i;
//            seekBar.setSecondaryProgress(bufferingLenght);
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mediaPlayer) {
//
//    }
//
//    @Override
//    public void onMusicClick(Track track) {
//
//        if(mediaPlayer.isPlaying()) {
//            mediaPlayer.stop();
//            playPause = true;
//        }
//
//        if (!playPause) {
//            //btnStart.setText("Pause Streaming");
//
//            if (initialStage) {
//                new Player().execute(track.getPathDataBase());
//            } else {
//                if (!mediaPlayer.isPlaying())
//                    mediaPlayer.start();
//            }
//
//            playPause = true;
//
//        } else {
//            //btnStart.setText("Launch Streaming");
//
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.pause();
//            }
//
//            playPause = false;
//        }
//
//
//    }
//
//    @Override
//    public void onPauseClick() {
//
//    }
//
//
//
//    class Player extends AsyncTask<String, Void, Boolean> {
//        @Override
//        protected Boolean doInBackground(String... strings) {
//            Boolean prepared = false;
//
//            try {
//                mediaPlayer.setDataSource(strings[0]);
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        initialStage = true;
//                        playPause = false;
//                        mediaPlayer.stop();
//                        mediaPlayer.reset();
//                    }
//                });
//
//                mediaPlayer.prepare();
//                prepared = true;
//
//            } catch (Exception e) {
//                Log.e("MyAudioStreamingApp", e.getMessage());
//                prepared = false;
//            }
//
//            return prepared;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//
//            mediaFileLenght = mediaPlayer.getDuration();
//            realTimeLenght = mediaFileLenght;
//
//            if (progressDialog.isShowing()) {
//                progressDialog.cancel();
//            }
//
//            mediaPlayer.start();
//            initialStage = false;
//            updateSeekBar();
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog.setMessage("Buffering...");
//            progressDialog.show();
//        }
//    }
//
//    public void updateSeekBar(){
//        seekBar.setProgress((int) (((float)mediaPlayer.getCurrentPosition()/mediaFileLenght) * 100));
//
//        if(mediaPlayer.isPlaying()){
//
//            Runnable updater = new Runnable() {
//                @Override
//                public void run() {
//
//                    updateSeekBar();
//                    realTimeLenght -= 1000;
//                    tvTimer.setText(getTime());
//
//                }
//            };
//
//            handler.postDelayed(updater, 1000);
//
//        }
//    }
//
//    @SuppressLint("DefaultLocale")
//    public String getTime(){
//        return String.format("%02d : %02d",
//                TimeUnit.MILLISECONDS.toMinutes(realTimeLenght),
//                TimeUnit.MILLISECONDS.toSeconds(realTimeLenght) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(realTimeLenght)));
//    }
//}
