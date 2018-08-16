package com.contactlist.tal.youtubeclick;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements OnInitializedListener {
    private YouTubePlayerView playerView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.player_view);
        this.playerView = (YouTubePlayerView) findViewById(R.id.player_view);
        this.playerView.initialize(YoutubeConnector.KEY, this);
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        if (!restored) {
            youTubePlayer.loadVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Field", 1).show();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
