package com.contactlist.tal.youtubeclick;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<VideoItem> {
    Context context;
    TextView titleclip;
    List<VideoItem> videoItems = new ArrayList();

    public PlaylistAdapter(Context context, List videoItems) {
        super(context, R.layout.search_result, videoItems);
        this.context = context;
        this.videoItems = videoItems;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.search_result, parent, false);
            ((PlaylistActivity) this.context).playlistView.deferNotifyDataSetChanged();
        }
        this.titleclip = (TextView) convertView.findViewById(R.id.titleclip);
        ImageView playbutton = (ImageView) convertView.findViewById(R.id.playbutton);
        ((ImageView) convertView.findViewById(R.id.saveclip)).setVisibility(4);
        playbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PlaylistAdapter.this.context.startActivity(YouTubeStandalonePlayer.createVideoIntent((Activity) PlaylistAdapter.this.context, YoutubeConnector.KEY, ((VideoItem) PlaylistAdapter.this.videoItems.get(position)).getId(), 0, true, true));
            }
        });
        this.titleclip.setText(((VideoItem) this.videoItems.get(position)).getTitle());
        return convertView;
    }
}
