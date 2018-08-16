package com.contactlist.tal.youtubeclick;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Builder;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;

public class YoutubeConnector {
    public static final String KEY = "AIzaSyCqni3zFsRW9h3QVdKQAqocTyxwKh7lCKo";
    private YouTube.Search.List query;
    private YouTube youtube;

    public YoutubeConnector(Context context) {
        this.youtube = new Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();
        try {
            this.query = this.youtube.search().list("id,snippet");
            this.query.setKey(KEY);
            this.query.setMaxResults(Long.valueOf(50));
            this.query.setType("video");
        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e.getMessage());
        }
    }

    public java.util.List<VideoItem> search(String keywords) {
        this.query.setQ(keywords);
        try {
            java.util.List<SearchResult> results = ((SearchListResponse) this.query.execute()).getItems();
            java.util.List<VideoItem> arrayList = new ArrayList();
            for (SearchResult result : results) {
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setId(result.getId().getVideoId());
                arrayList.add(item);
            }
            return arrayList;
        } catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }
}
