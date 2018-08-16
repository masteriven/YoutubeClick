package com.contactlist.tal.youtubeclick;

import java.io.Serializable;

public class VideoItem implements Serializable {
    private String _id;
    private String _title;
    private int idsql;

    public VideoItem() {

    }

    public VideoItem(VideoItem videoItem) {

    }

    public VideoItem(String id, String title) {
        this._title = title;
        this._id = id;
    }

    public VideoItem(int idsql, String title, String id) {
        this.idsql = idsql;
        this._title = title;
        this._id = id;
    }

    public String getTitle() {
        return this._title;
    }

    public String getId() {
        return this._id;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public void setId(String id) {
        this._id = id;
    }

    public int getIdsql() {
        return this.idsql;
    }

    public void setIdsql(int idsql) {
        this.idsql = idsql;
    }
}
