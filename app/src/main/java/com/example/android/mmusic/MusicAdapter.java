package com.example.android.mmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.mmusic.pojo.Track;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {


    private List<Track> tracksList;
    private OnMusicAdapterListener listener;
    private Context context;

    private Track lastTrack  = new Track();

    public interface OnMusicAdapterListener{
        void onMusicClick(Track track);
        void onPauseClick();
        void passSeecBarAndTimerView(SeekBar seekBar, TextView textView);
    }

    public MusicAdapter( OnMusicAdapterListener listener, Context context) {
        //this.tracksList = tracksList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_music, parent, false);

        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicHolder holder, int position) {
        holder.bind(tracksList.get(position));

    }

    @Override
    public int getItemCount() {
        if(tracksList != null) {
            return tracksList.size();
        }else {
            return 0;
        }
    }

    public void setList(List<Track> list){
        this.tracksList = list;
        notifyDataSetChanged();
    }

    public class MusicHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView duration;
        private TextView count;
        private ImageButton playPause;
        private SeekBar seekBar;
        private boolean setImage;


        public MusicHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_music_title);
            duration = itemView.findViewById(R.id.tv_music_duration);
            count = itemView.findViewById(R.id.tv_count);
            playPause = itemView.findViewById(R.id.btn_play_stop);
            seekBar = itemView.findViewById(R.id.seekBar2);
        }

        public void bind(final Track track){
            title.setText(track.getTitle());
            duration.setText(String.valueOf(track.getDuration()));
            count.setText(String.valueOf(getAdapterPosition()+1));

            seekBar.setVisibility(View.GONE);
            playPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);

            playPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(track.equals(lastTrack)){
                        listener.onPauseClick();
                        playPause.setImageResource(setImage(setImage));
                        setImage = !setImage;
                        return;
                    }

                    listener.passSeecBarAndTimerView(seekBar, duration);

                    track.setChecked(true);

                    if(track.isChecked()) {
                        seekBar.setVisibility(View.VISIBLE);
                        track.setChecked(true);
                        lastTrack.setChecked(false);
                        notifyItemChanged(tracksList.indexOf(lastTrack));
                        playPause.setImageResource(R.drawable.ic_pause_black_24dp);
                        listener.onMusicClick(track);
                        lastTrack = track;
                    }
                }
            });
        }
    }


    public int setImage(boolean set){
        if(set){
            return  R.drawable.ic_pause_black_24dp;
        }else {
            return R.drawable.ic_play_arrow_black_24dp;
        }
    }
}
