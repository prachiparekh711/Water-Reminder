package com.example.waterreminder;

/**
 * Created by Gunjan on 19-05-18.
 */

public class Song {
    String songName;

    String id, uri;


    public Song(String songName, String id, String uri) {
        this.songName = songName;

        this.id = id;
        this.uri = uri;

    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
