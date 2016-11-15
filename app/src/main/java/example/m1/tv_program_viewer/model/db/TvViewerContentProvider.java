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
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_CATEGORY_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_IS_FAVORITE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_PICTURE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_URL;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_DEFAULT_PROJECTION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_FAVORITES_PATH_POSITION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_ID_PATH_POSITION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_TABLE_NAME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.DEFAULT_CHANNELS_SORT_ORDER;
import static example.m1.tv_program_viewer.model.db.ContractClass.DATABASE_NAME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.DEFAULT_PROGRAMS_SORT_ORDER;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_CHANNEL_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_DATE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_DESCRIPTION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_TIME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_TITLE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_CONTENT_ID_URI_BASE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_DEFAULT_PROJECTION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_ID_PATH_POSITION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_TABLE_NAME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_TIMESTAMP_PATH_POSITION;

/**
 * Created by M1 on 11.11.2016.
 */

public class TvViewerContentProvider extends ContentProvider {

    private static final int DATABASE_VERSION = 1;

    private static final int CHANNEL = 1;
    private static final int CHANNEL_ID = 2;
    private static final int CHANNEL_FAVORITE = 3;
    private static final int PROGRAM = 4;
    private static final int PROGRAM_ID = 5;

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "channel", CHANNEL);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "channel/*", CHANNEL_ID);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "channel_favorite/*", CHANNEL_FAVORITE);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "programs", PROGRAM);
        sUriMatcher.addURI(ContractClass.AUTHORITY, "programs/*/*", PROGRAM_ID);
    }

    private static HashMap channelsProjectionMap;
    private static HashMap programsProjectionMap;

    static {
        channelsProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < CHANNEL_DEFAULT_PROJECTION.length; i++) {
            channelsProjectionMap.put(
                    CHANNEL_DEFAULT_PROJECTION[i],
                    CHANNEL_DEFAULT_PROJECTION[i]);
        }

        programsProjectionMap = new HashMap<String, String>();
        for (int i = 0; i < PROGRAMS_DEFAULT_PROJECTION.length; i++) {
            programsProjectionMap.put(
                    PROGRAMS_DEFAULT_PROJECTION[i],
                    PROGRAMS_DEFAULT_PROJECTION[i]);
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
                qb.setTables(CHANNEL_TABLE_NAME);
                qb.setProjectionMap(channelsProjectionMap);
                orderBy = DEFAULT_CHANNELS_SORT_ORDER;
                break;
            case CHANNEL_ID:
                qb.setTables(CHANNEL_TABLE_NAME);
                qb.setProjectionMap(channelsProjectionMap);
                qb.appendWhere(ContractClass.ChannelContract._ID + "=" + uri.getPathSegments().get(CHANNEL_ID_PATH_POSITION));
                orderBy = DEFAULT_CHANNELS_SORT_ORDER;
                break;

            case CHANNEL_FAVORITE:
                qb.setTables(CHANNEL_TABLE_NAME);
                qb.setProjectionMap(channelsProjectionMap);
                qb.appendWhere(CHANNEL_COLUMN_NAME_IS_FAVORITE + "=" + uri.getPathSegments().get(CHANNEL_FAVORITES_PATH_POSITION));
                orderBy = DEFAULT_CHANNELS_SORT_ORDER;
                break;
            case PROGRAM:
                qb.setTables(ContractClass.ProgramsContract.PROGRAMS_TABLE_NAME);
                qb.setProjectionMap(programsProjectionMap);
                orderBy = DEFAULT_PROGRAMS_SORT_ORDER;
                break;

            case PROGRAM_ID:
                qb.setTables(ContractClass.ProgramsContract.PROGRAMS_TABLE_NAME);
                qb.setProjectionMap(programsProjectionMap);
                qb.appendWhere(PROGRAMS_COLUMN_NAME_CHANNEL_ID + "=" + uri.getPathSegments().get(PROGRAMS_ID_PATH_POSITION) + " OR ");
                qb.appendWhere(PROGRAMS_COLUMN_NAME_DATE + "=" + uri.getPathSegments().get(PROGRAMS_TIMESTAMP_PATH_POSITION));
                orderBy = DEFAULT_PROGRAMS_SORT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

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
                return ContractClass.ChannelContract.CHANNEL_CONTENT_TYPE;
            case CHANNEL_ID:
                return ContractClass.ChannelContract.CHANNEL_CONTENT_ITEM_TYPE;
            case CHANNEL_FAVORITE:
                return ContractClass.ChannelContract.CHANNEL_CONTENT_ITEM_TYPE;
            case PROGRAM:
                return ContractClass.ProgramsContract.PROGRAMS_CONTENT_TYPE;
            case PROGRAM_ID:
                return ContractClass.ProgramsContract.PROGRAMS_CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public synchronized Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != CHANNEL && sUriMatcher.match(uri) != PROGRAM) {
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
                rowId = db.insertWithOnConflict(CHANNEL_TABLE_NAME,
                        ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME,
                        values, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowId == -1) {
                    rowId = db.update(CHANNEL_TABLE_NAME,
                            initialValues,
                            "_id=?", new String[]{"" + values.get(_ID)});
                }
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(ContractClass.ChannelContract.CHANNEL_CONTENT_ID_URI_BASE, rowId);
                    getContext().getContentResolver().notifyChange(rowUri, null);
                }
                break;

            case PROGRAM:
                prepareProgramData(values);

                rowId = db.insertWithOnConflict(PROGRAMS_TABLE_NAME,
                        PROGRAMS_COLUMN_NAME_TITLE,
                        values, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowId > 0) {
                    rowUri = ContentUris.withAppendedId(PROGRAMS_CONTENT_ID_URI_BASE, rowId);
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
                count = db.delete(CHANNEL_TABLE_NAME, where, whereArgs);
                break;
            case CHANNEL_ID:
                finalWhere = ContractClass.ChannelContract._ID + " = " + uri.getPathSegments().get(CHANNEL_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(CHANNEL_TABLE_NAME, finalWhere, whereArgs);
                break;
            case PROGRAM:
                count = db.delete(PROGRAMS_TABLE_NAME, where, whereArgs);
                break;
            case PROGRAM_ID:
                finalWhere = _ID + " = " + uri.getPathSegments().get(PROGRAMS_ID_PATH_POSITION);
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.delete(PROGRAMS_TABLE_NAME, finalWhere, whereArgs);
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
                count = db.update(CHANNEL_TABLE_NAME, values, where, whereArgs);
                break;
            case CHANNEL_ID:
                id = uri.getPathSegments().get(CHANNEL_ID_PATH_POSITION);
                finalWhere = ContractClass.ChannelContract._ID + " = " + id;
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(CHANNEL_TABLE_NAME, values, finalWhere, whereArgs);
                break;
            case PROGRAM:
                count = db.update(PROGRAMS_TABLE_NAME, values, where, whereArgs);
                break;
            case PROGRAM_ID:
                id = uri.getPathSegments().get(PROGRAMS_ID_PATH_POSITION);
                finalWhere = _ID + " = " + id;
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(PROGRAMS_TABLE_NAME, values, finalWhere, whereArgs);
                break;
            case CHANNEL_FAVORITE:
                id = uri.getPathSegments().get(PROGRAMS_ID_PATH_POSITION);
                finalWhere = _ID + " = " + id;
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }
                count = db.update(CHANNEL_TABLE_NAME, values, finalWhere, whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private void prepareChannelData(ContentValues values) {
        if (!values.containsKey(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME)) {
            values.put(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME, "");
        }
        if (!values.containsKey(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_URL)) {
            values.put(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_URL, "");
        }
        if (!values.containsKey(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_PICTURE)) {
            values.put(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_PICTURE, "");
        }
        if (!values.containsKey(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_CATEGORY_ID)) {
            values.put(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_CATEGORY_ID, 0);
        }
        if (!values.containsKey(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_IS_FAVORITE)) {
            values.put(ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_IS_FAVORITE, 0);
        }
    }

    private void prepareProgramData(ContentValues values) {
        if (!values.containsKey(PROGRAMS_COLUMN_NAME_CHANNEL_ID)) {
            values.put(PROGRAMS_COLUMN_NAME_CHANNEL_ID, 0);
        }
        if (!values.containsKey(PROGRAMS_COLUMN_NAME_DATE)) {
            values.put(PROGRAMS_COLUMN_NAME_DATE, "");
        }
        if (!values.containsKey(PROGRAMS_COLUMN_NAME_TIME)) {
            values.put(PROGRAMS_COLUMN_NAME_TIME, "");
        }
        if (!values.containsKey(PROGRAMS_COLUMN_NAME_TITLE)) {
            values.put(PROGRAMS_COLUMN_NAME_TITLE, "");
        }
        if (!values.containsKey(PROGRAMS_COLUMN_NAME_DESCRIPTION)) {
            values.put(PROGRAMS_COLUMN_NAME_DESCRIPTION, "");
        }
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {


        private static final String DATABASE_CREATE_TABLE_CHANNELS =
                "create table " + CHANNEL_TABLE_NAME + " ("
                        + _ID + " integer primary key autoincrement, "
                        + CHANNEL_COLUMN_NAME_NAME + " string , "
                        + CHANNEL_COLUMN_NAME_URL + " string , "
                        + CHANNEL_COLUMN_NAME_PICTURE + " string , "
                        + CHANNEL_COLUMN_NAME_CATEGORY_ID + " integer , "
                        + CHANNEL_COLUMN_NAME_IS_FAVORITE + " integer );";

        private static final String DATABASE_CREATE_TABLE_PROGRAMS =
                "create table " + PROGRAMS_TABLE_NAME + " ("
                        + _ID + " integer primary key autoincrement, "
                        + PROGRAMS_COLUMN_NAME_CHANNEL_ID + " integer , "
                        + PROGRAMS_COLUMN_NAME_DATE + " string , "
                        + PROGRAMS_COLUMN_NAME_TIME + " string , "
                        + PROGRAMS_COLUMN_NAME_TITLE + " string , "
                        + PROGRAMS_COLUMN_NAME_DESCRIPTION + " string );";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_TABLE_CHANNELS);
            db.execSQL(DATABASE_CREATE_TABLE_PROGRAMS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CHANNEL_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PROGRAMS_TABLE_NAME);
            onCreate(db);
        }
    }
}
