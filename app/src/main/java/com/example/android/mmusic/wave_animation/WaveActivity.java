package com.example.android.mmusic.wave_animation;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.mmusic.R;


public class WaveActivity extends AppCompatActivity {


    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);



//        WaveDrawable mWaveDrawable = new WaveDrawable(getResources().getDrawable(R.drawable.shape_bg));
//
//        imageView = findViewById(R.id.iv_wave);
//// Use as common drawable
//        imageView.setImageDrawable(mWaveDrawable);
//
//        mWaveDrawable.setWaveAmplitude(20);
//        mWaveDrawable.setWaveLength(30);
//        mWaveDrawable.setWaveSpeed(40);



//        waveProgressbar = findViewById(R.id.waveProgressbar);
//        waveProgressbar.setCurrent(100,"text"); // 77, "788M/1024M"
//        waveProgressbar.setMaxProgress(100);
//        //waveProgressbar.setText(String mTextColor,int mTextSize);//"#FFFF00", 41
//        waveProgressbar.setWaveColor("#5b9ef4"); //"#5b9ef4"
//
//        waveProgressbar.setWave(10,100);
//        waveProgressbar.setmWaveSpeed(20);//The larger the value, the slower the vibration
    }
}
