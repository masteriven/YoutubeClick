package com.contactlist.tal.youtubeclick;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int WEEK_VIEW_MODE = 1;
    ArrayAdapter<VideoItem> arrayAdapter;
    DatabaseHandler databaseHandler;
    private Handler handler;
    String jsons;
    private ListView listView;
    private int mCurViewMode;
    ImageView playvideo;
    private ProgressDialog progressDialog;
    ImageView saveButton;
    public List<VideoItem> searchResults = new ArrayList();
    List<String> videoItems = new ArrayList();
    EditText youtubeSearch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myactionbar);
        setSupportActionBar(toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/youtubeclick.ttf"));
        this.databaseHandler = new DatabaseHandler(this, null, null, 1);
        this.youtubeSearch = (EditText) findViewById(R.id.youtubesearchid);
        this.listView = (ListView) findViewById(R.id.youtubelist);
        this.handler = new Handler();
        this.youtubeSearch.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 6) {
                    return true;
                }
                MainActivity.this.searchOnYoutube(v.getText().toString());
                MainActivity.this.progressDialog = new ProgressDialog(MainActivity.this,R.style.AppTheme);
                MainActivity.this.progressDialog.setProgressStyle(0);
                MainActivity.this.progressDialog.setProgress(0);
                MainActivity.this.progressDialog.setCancelable(false);
                MainActivity.this.progressDialog.show();
                return false;
            }
        });
    }

    private void searchOnYoutube(final String keywords) {
        new Thread() {
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(MainActivity.this);
                MainActivity.this.searchResults = yc.search(keywords);
                MainActivity.this.handler.post(new Runnable() {
                    public void run() {
                        MainActivity.this.updateVideosFound();
                    }
                });
            }
        }.start();
    }

    public void updateVideosFound() {
        this.arrayAdapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.search_result, this.searchResults) {
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = MainActivity.this.getLayoutInflater().inflate(R.layout.search_result, parent, false);
                    MainActivity.this.progressDialog.dismiss();
                }
                MainActivity.this.playvideo = (ImageView) convertView.findViewById(R.id.playbutton);
                MainActivity.this.saveButton = (ImageView) convertView.findViewById(R.id.saveclip);
                TextView titleclip = (TextView) convertView.findViewById(R.id.titleclip);
                MainActivity.this.playvideo.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        MainActivity.this.startActivity(YouTubeStandalonePlayer.createVideoIntent(MainActivity.this, YoutubeConnector.KEY, ((VideoItem) MainActivity.this.searchResults.get(position)).getId(), 0, true, true));
                    }
                });
                MainActivity.this.saveButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        MainActivity.this.databaseHandler.addVideo(new VideoItem(((VideoItem) MainActivity.this.searchResults.get(position)).getId(), ((VideoItem) MainActivity.this.searchResults.get(position)).getTitle()));
                        Toast.makeText(MainActivity.this.getApplicationContext(), "Video added to playlist", Toast.LENGTH_SHORT).show();
                    }
                });
                titleclip.setText(((VideoItem) MainActivity.this.searchResults.get(position)).getTitle());
                return convertView;
            }
        };
        this.listView.setAdapter(this.arrayAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MyPlaylist:
                startActivity(new Intent(this, PlaylistActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
