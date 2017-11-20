package com.example.android.mmusic.pojo;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by android on 11/17/17.
 */

public class Track {


    private String title;
    private Long duration;
    @PropertyName("path")
    private String path;

    private String pathStorage;

    private boolean isChecked;


    public Track() {
    }

    public Track(String title, Long duration, String path) {
        this.title = title;
        this.duration = duration;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @PropertyName("path")
    public String getPathDataBase() {
        return path;
    }

    @PropertyName("path")
    public void setPathDataBase(String pathDataBase) {
        this.path = pathDataBase;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPathStorage() {
        return pathStorage;
    }

    public void setPathStorage(String pathStorage) {
        this.pathStorage = pathStorage;
    }
}
