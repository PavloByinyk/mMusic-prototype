package com.example.android.mmusic;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.mmusic.pojo.Track;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by android on 11/20/17.
 */

public class MusicActivity extends AppCompatActivity
        implements
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener,
        MusicAdapter.OnMusicAdapterListener {

    private DatabaseReference mDatabase;
    private DatabaseReference audioCloudEndPoint;
    private FirebaseStorage storage;
    private StorageReference storageRef;



    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private TextView tvTimer;


    int mediaFileLenght;
    int realTimeLenght;
    int bufferingLenght;
    private Handler handler = new Handler();

    private MusicAdapter musicAdapter;
    private RecyclerView recyclerView;

    private final List<Track> trackList = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        audioCloudEndPoint = mDatabase.child("audio");

        storage = FirebaseStorage.getInstance();


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        progressDialog = new ProgressDialog(this);

        musicAdapter = new MusicAdapter(this, this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(musicAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        loadAudioFirebase();
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        bufferingLenght = i;
        seekBar.setSecondaryProgress(bufferingLenght);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onMusicClick(Track track) {

        mediaPlayer.stop();
        mediaPlayer.reset();

        progressDialog.setMessage("Buffering...");
        progressDialog.show();

        Uri myUri = Uri.parse(track.getPathStorage());

        try {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mediaFileLenght = mp.getDuration();
                realTimeLenght = mediaFileLenght;

                if(progressDialog.isShowing()){
                    progressDialog.hide();
                }

                mediaPlayer.start();
                updateSeekBar();
            }
        });
    }

    @Override
    public void onPauseClick() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else {
            mediaPlayer.start();
            updateSeekBar();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void passSeecBarAndTimerView(SeekBar seekBar, TextView textView) {
        this.seekBar = seekBar;
        this.tvTimer = textView;

        this.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mediaPlayer.isPlaying()){

                    SeekBar seekBar = (SeekBar) view;

                    int currentPos = seekBar.getProgress();

                    if(currentPos < bufferingLenght){
                        mediaPlayer.seekTo((currentPos * mediaFileLenght)/ seekBar.getMax());
                        realTimeLenght = mediaFileLenght - mediaPlayer.getCurrentPosition();
                        tvTimer.setText(getTime(realTimeLenght));
                    }
                }
                return false;
            }
        });


    }


    public void updateSeekBar(){
        seekBar.setProgress((int) (((float)mediaPlayer.getCurrentPosition()/mediaFileLenght) * 100));

        if(mediaPlayer.isPlaying()){

            Runnable updater = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                    realTimeLenght -= 1000;
                    tvTimer.setText(getTime(realTimeLenght));
                }
            };

            handler.postDelayed(updater, 1000);

        }
    }

    @SuppressLint("DefaultLocale")
    public String getTime(int time){
        return String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }


    public void loadAudioFirebase(){

        //final List<Track> mJournalEntries = new ArrayList<>();
        audioCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    final Track note = noteSnapshot.getValue(Track.class);

                    trackList.add(note);

                }
                loadFromStorage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FireBase", databaseError.getMessage());
            }
        });
    }


    public void loadFromStorage() {

        for (final Track track : trackList) {

            storageRef = storage.getReference();

            storageRef.child(track.getPathDataBase()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.e("Tuts+", "uri: " + uri.toString());
                    track.setPathStorage(uri.toString());
                    musicAdapter.addTrackToList(track);

                    if(progressDialog.isShowing()){
                        progressDialog.hide();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("Tuts+", "uri: " + exception.getMessage());
                }
            });


        }
    }

}
