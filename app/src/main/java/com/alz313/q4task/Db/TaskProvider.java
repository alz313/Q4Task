package com.alz313.q4task.Db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import static com.alz313.q4task.Db.DbSchema.TaskTable;


public class TaskProvider extends ContentProvider {
    public static final String LOG_TAG = "LOG_ALZ313";

    private SQLiteDatabase mDb;

    static final String AUTHORITY = "com.alz313.q4task";
    static final String TASK_PATH = TaskTable.NAME;

    public static final Uri TASK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TASK_PATH);     // Общий Uri

    static final String CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TASK_PATH;
    static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TASK_PATH;

    static final int URI_TASKS = 1;
    static final int URI_TASKS_ID = 2;
    static final int URI_URGENT_TASKS = 3;
    static final int URI_IMPORTANT_TASKS = 4;
    static final int URI_URGENT_IMPORTANT_TASKS = 5;
    static final int URI_NO_URGENT_IMPORTANT_TASKS = 6;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TASK_PATH, URI_TASKS);
        uriMatcher.addURI(AUTHORITY, TASK_PATH + "/#", URI_TASKS_ID);
        uriMatcher.addURI(AUTHORITY, TASK_PATH + "/urgent", URI_URGENT_TASKS);
        uriMatcher.addURI(AUTHORITY, TASK_PATH + "/important", URI_IMPORTANT_TASKS);
        uriMatcher.addURI(AUTHORITY, TASK_PATH + "/important/urgent", URI_URGENT_IMPORTANT_TASKS);
        uriMatcher.addURI(AUTHORITY, TASK_PATH + "/urgent/important", URI_URGENT_IMPORTANT_TASKS);
        uriMatcher.addURI(AUTHORITY, TASK_PATH + "/no/important/urgent", URI_NO_URGENT_IMPORTANT_TASKS);
        uriMatcher.addURI(AUTHORITY, TASK_PATH + "/no/urgent/important", URI_NO_URGENT_IMPORTANT_TASKS);
    }

    public TaskProvider() {
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_TASKS:
            case URI_URGENT_TASKS:
            case URI_IMPORTANT_TASKS:
            case URI_URGENT_IMPORTANT_TASKS:
            case URI_NO_URGENT_IMPORTANT_TASKS:
                return CONTACT_CONTENT_TYPE + AUTHORITY + "." + TASK_PATH;
            case URI_TASKS_ID:
                return CONTACT_CONTENT_ITEM_TYPE + AUTHORITY + "." + TASK_PATH;
            default:
                return null;

        }
    }

    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, TaskProvider.class.getName() + " onCreate");

        Context context = getContext();
        DbHelper dbHelper = new DbHelper(context);

        mDb = dbHelper.getWritableDatabase();
        return mDb != null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, TaskProvider.class.getName() + " delete");

        switch (uriMatcher.match(uri)) {
            case URI_TASKS:
                break;
            case URI_TASKS_ID:
                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {
                    selection = TaskTable.Cols.ID + " = " + id;
                } else {
                    selection = selection + " AND " + TaskTable.Cols.ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        int cnt = mDb.delete(TaskTable.NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Log.d(LOG_TAG, TaskProvider.class.getName() + " insert");

        if (uriMatcher.match(uri) != URI_TASKS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        long rowID = mDb.insert(TaskTable.NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(TASK_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, TaskProvider.class.getName() + " update");

        switch (uriMatcher.match(uri)) {
            case URI_TASKS:
                break;
            case URI_TASKS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = TaskTable.Cols.ID + " = " + id;
                } else {
                    selection = selection + " AND " + TaskTable.Cols.ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        int cnt = mDb.update(TaskTable.NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, TaskProvider.class.getName() + " query");

        switch (uriMatcher.match(uri)) {
            case URI_TASKS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = " " + TaskTable.Cols.ID + " ASC";
                }
                break;
            case URI_TASKS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = TaskTable.Cols.ID + " = " + id;
                } else {
                    selection = selection + " AND " + TaskTable.Cols.ID + " = " + id;
                }
                break;
            case URI_URGENT_TASKS:
                if (TextUtils.isEmpty(selection)) {
                    selection = (TaskTable.Cols.IMPORTANCE + " < 50 AND " + TaskTable.Cols.URGENCY + " >= 50 ");
                } else {
                    selection = selection + " and " + (TaskTable.Cols.IMPORTANCE + " < 50 AND " + TaskTable.Cols.URGENCY + " >= 50 ");
                }
                break;
            case URI_IMPORTANT_TASKS:
                if (TextUtils.isEmpty(selection)) {
                    selection = (TaskTable.Cols.IMPORTANCE + " >= 50 AND " + TaskTable.Cols.URGENCY + " < 50 ");
                } else {
                    selection = selection + " and " + (TaskTable.Cols.IMPORTANCE + " >= 50 AND " + TaskTable.Cols.URGENCY + " < 50 ");
                }
                break;
            case URI_URGENT_IMPORTANT_TASKS:
                if (TextUtils.isEmpty(selection)) {
                    selection = (TaskTable.Cols.IMPORTANCE + " >= 50 AND " + TaskTable.Cols.URGENCY + " >= 50 ");
                } else {
                    selection = selection + " and " + (TaskTable.Cols.IMPORTANCE + " >= 50 AND " + TaskTable.Cols.URGENCY + " >= 50 ");
                }
                break;
            case URI_NO_URGENT_IMPORTANT_TASKS:
                if (TextUtils.isEmpty(selection)) {
                    selection = (TaskTable.Cols.IMPORTANCE + " < 50 AND " + TaskTable.Cols.URGENCY + " < 50 ");
                } else {
                    selection = selection + " and " + (TaskTable.Cols.IMPORTANCE + " < 50 AND " + TaskTable.Cols.URGENCY + " < 50 ");
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        sortOrder = (TextUtils.isEmpty(sortOrder) ? "(" + TaskTable.Cols.IMPORTANCE + " + " + TaskTable.Cols.URGENCY + ") DESC" : sortOrder);
        Cursor cursor = mDb.query(TaskTable.NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), TASK_CONTENT_URI);
        return cursor;
    }

}
