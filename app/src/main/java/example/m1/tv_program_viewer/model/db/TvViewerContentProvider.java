package example.m1.tv_program_viewer.model.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

import static android.provider.BaseColumns._ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_CATEGORY_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_IS_FAVORITE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_NAME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_PICTURE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.COLUMN_NAME_URL;

/**
 * Created by M1 on 11.11.2016.
 */

public class TvViewerContentProvider extends ContentProvider {

    private static final int DATABASE_VERSION = 1;

    private static final int CHANNEL = 1;
    private static final int CHANNEL_ID = 2;
    private static final int IS_CHANNEL_FAVORITE = 3;

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "channel", CHANNEL);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "channel/#", CHANNEL_ID);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "channel_favorite", IS_CHANNEL_FAVORITE);
    }

    private static HashMap channelProjectionMap;

    static {
        channelProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < ContractClass.ChannelContract.DEFAULT_PROJECTION.length; i++) {
            channelProjectionMap.put(
                    ContractClass.ChannelContract.DEFAULT_PROJECTION[i],
                    ContractClass.ChannelContract.DEFAULT_PROJECTION[i]);
        }
    }

    DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public synchronized Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = null;
        switch (sUriMatcher.match(uri)) {
            case CHANNEL:
                qb.setTables(ContractClass.ChannelContract.TABLE_NAME);
                qb.setProjectionMap(channelProjectionMap);
                break;
            case CHANNEL_ID:
                qb.setTables(ContractClass.ChannelContract.TABLE_NAME);
                qb.setProjectionMap(channelProjectionMap);
                qb.appendWhere(ContractClass.ChannelContract._ID + "=" + uri.getPathSegments().get(ContractClass.ChannelContract.CHANNEL_ID_PATH_POSITION));
                break;
            case IS_CHANNEL_FAVORITE:
                qb.setTables(ContractClass.ChannelContract.TABLE_NAME);
                qb.setProjectionMap(channelProjectionMap);
                qb.appendWhere(ContractClass.ChannelContract.COLUMN_NAME_IS_FAVORITE + "= 1");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        orderBy = ContractClass.ChannelContract.DEFAULT_SORT_ORDER;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public synchronized String getType(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case CHANNEL:
                return ContractClass.ChannelContract.CONTENT_TYPE;
            case CHANNEL_ID:
                return ContractClass.ChannelContract.CONTENT_ITEM_TYPE;
            case IS_CHANNEL_FAVORITE:
                return ContractClass.ChannelContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public synchronized Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != CHANNEL) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        long rowId = -1;
        Uri rowUri = Uri.EMPTY;
        switch (sUriMatcher.match(uri)) {
            case CHANNEL:
                prepareChannelData(values);
                rowId = db.insertWithOnConflict(ContractClass.ChannelContract.TABLE_NAME,
                        ContractClass.ChannelContract.COLUMN_NAME_NAME,
                        values, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowId == -1) {
                    rowId = db.update(ContractClass.ChannelContract.TABLE_NAME,
                            initialValues,
                            "_id=?", new String[]{"" + values.get(_ID)});
                }
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(ContractClass.ChannelContract.CONTENT_ID_URI_BASE, rowId);
                    getContext().getContentResolver().notifyChange(rowUri, null);
                }
                break;
        }
        return rowUri;
    }

    @Override
    public synchronized int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String finalWhere;
        int count;
        switch (sUriMatcher.match(uri)) {
            case CHANNEL:
                count = db.delete(ContractClass.ChannelContract.TABLE_NAME, where, whereArgs);
                break;
            case CHANNEL_ID:
                finalWhere = ContractClass.ChannelContract._ID + " = " + uri.getPathSegments().get(ContractClass.ChannelContract.CHANNEL_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(ContractClass.ChannelContract.TABLE_NAME, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public synchronized int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        String finalWhere;
        String id;
        switch (sUriMatcher.match(uri)) {
            case CHANNEL:
                count = db.update(ContractClass.ChannelContract.TABLE_NAME, values, where, whereArgs);
                break;
            case CHANNEL_ID:
                id = uri.getPathSegments().get(ContractClass.ChannelContract.CHANNEL_ID_PATH_POSITION);
                finalWhere = ContractClass.ChannelContract._ID + " = " + id;
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(ContractClass.ChannelContract.TABLE_NAME, values, finalWhere, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private void prepareChannelData(ContentValues values) {
        if (!values.containsKey(ContractClass.ChannelContract.COLUMN_NAME_NAME)) {
            values.put(ContractClass.ChannelContract.COLUMN_NAME_NAME, "");
        }
        if (!values.containsKey(ContractClass.ChannelContract.COLUMN_NAME_URL)) {
            values.put(ContractClass.ChannelContract.COLUMN_NAME_URL, "");
        }
        if (!values.containsKey(ContractClass.ChannelContract.COLUMN_NAME_PICTURE)) {
            values.put(ContractClass.ChannelContract.COLUMN_NAME_PICTURE, "");
        }
        if (!values.containsKey(ContractClass.ChannelContract.COLUMN_NAME_CATEGORY_ID)) {
            values.put(ContractClass.ChannelContract.COLUMN_NAME_CATEGORY_ID, 0);
        }
        if (!values.containsKey(ContractClass.ChannelContract.COLUMN_NAME_IS_FAVORITE)) {
            values.put(ContractClass.ChannelContract.COLUMN_NAME_IS_FAVORITE, 0);
        }
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "TvViewerDB";
        public static final String DATABASE_TABLE_CHANNEL = ContractClass.ChannelContract.TABLE_NAME;

        private static final String DATABASE_CREATE_TABLE_STUDENTS =
                "create table " + DATABASE_TABLE_CHANNEL + " ("
                        + _ID + " integer primary key autoincrement, "
                        + COLUMN_NAME_NAME + " string , "
                        + COLUMN_NAME_URL + " string , "
                        + COLUMN_NAME_PICTURE + " string , "
                        + COLUMN_NAME_CATEGORY_ID + " integer , "
                        + COLUMN_NAME_IS_FAVORITE + " integer );";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_TABLE_STUDENTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CHANNEL);
            onCreate(db);
        }
    }
}
