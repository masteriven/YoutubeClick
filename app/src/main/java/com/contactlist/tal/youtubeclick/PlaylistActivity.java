package com.contactlist.tal.youtubeclick;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class PlaylistActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    private int mCurViewMode;
    PlaylistAdapter playlistAdapter;
    ListView playlistView;

    protected class ExampleAsyncTask extends AsyncTask<Void, Void, Boolean> {
        protected ExampleAsyncTask() {
        }

        protected Boolean doInBackground(Void... params) {
            return Boolean.valueOf(true);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onCancelled() {
            super.onCancelled();
        }

        protected void onPostExecute(Boolean aBoolean) {
            PlaylistActivity.this.playlistAdapter.notifyDataSetChanged();
            super.onPostExecute(aBoolean);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myactionbarPlaylist);
        setSupportActionBar(toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_plylisttitle)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/youtubeclick.ttf"));
        this.databaseHandler = new DatabaseHandler(this, null, null, 1);
        final MainActivity mainActivity = new MainActivity();
        mainActivity.searchResults.addAll(this.databaseHandler.getAllVideos());
        this.playlistView = (ListView) findViewById(R.id.playlist);
        this.playlistAdapter = new PlaylistAdapter(this, mainActivity.searchResults);
        this.playlistView.setAdapter(this.playlistAdapter);
        new ExampleAsyncTask().onPostExecute(Boolean.valueOf(true));
        runOnUiThread(new Runnable() {
            public void run() {
                PlaylistActivity.this.playlistAdapter.notifyDataSetChanged();
            }
        });
        this.playlistView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                Builder builder = new Builder(new ContextThemeWrapper(PlaylistActivity.this, (int) R.style.AppTheme));
                builder.setMessage((CharSequence) "Are you sure you want to delete this video?");
                builder.setCancelable(false);
                builder.setPositiveButton((CharSequence) "Ok", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PlaylistActivity.this.databaseHandler.deleteVideo(((VideoItem) mainActivity.searchResults.get(position)).getTitle());
                        PlaylistActivity.this.playlistAdapter.remove(mainActivity.searchResults.get(position));
                        PlaylistActivity.this.playlistAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton((CharSequence) "Cancel", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(-1).setTextSize(15.0f);
                dialog.getButton(-2).setTextSize(15.0f);
                return true;
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
