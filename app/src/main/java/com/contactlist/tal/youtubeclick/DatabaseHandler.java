package com.contactlist.tal.youtubeclick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "video.db";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "id";
    private static final String KEY_ID = "_sqlid";
    private static final String KEY_NAME = "name";
    private static String TABLE_VIDEO = "videos";
    private Context cont;
    private SQLiteDatabase database;
    private DatabaseHandler db;

    public DatabaseHandler(Context context, String string, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_VIDEO + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + ID + " TEXT" + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    public void addVideo(VideoItem videoItem) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, videoItem.getTitle());
        values.put(ID, videoItem.getId());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_VIDEO, null, values);
        sqLiteDatabase.close();
    }

    public VideoItem getVideo(int id) {
        Cursor cursor = getReadableDatabase().query(DATABASE_NAME, new String[]{KEY_ID, KEY_NAME}, "_sqlid=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new VideoItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
    }

    public int getVideoCount() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + DATABASE_NAME, null);
        cursor.close();
        return cursor.getCount();
    }

    public void close() {
        this.db.close();
    }

    public int updateVideo(VideoItem videoItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, videoItem.getTitle());
        values.put(ID, videoItem.getId());
        return db.update(TABLE_VIDEO, values, "_sqlid = ?", new String[]{String.valueOf(videoItem.getIdsql())});
    }

    public void deleteVideo(String videoItem) {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_VIDEO + " WHERE " + KEY_NAME + "=\"" + videoItem + "\"");
    }

    public List<VideoItem> getAllVideos() {
        List<VideoItem> contactList = new ArrayList();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT  * FROM " + TABLE_VIDEO, null);
        if (cursor.moveToFirst()) {
            do {
                VideoItem contact = new VideoItem();
                contact.setIdsql(Integer.parseInt(cursor.getString(0)));
                contact.setTitle(cursor.getString(1));
                contact.setId(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
}
