package example.m1.tv_program_viewer.model.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by M1 on 12.11.2016.
 */

public final class ContractClass {
    public static final String DATABASE_NAME = "TvViewerDB";
    public static final String AUTHORITY = "example.m1.tv_program_viewer.model.db.ContractClass";

    private ContractClass() {
    }

    public static final class ChannelContract implements BaseColumns {
        private ChannelContract() {
        }

        public static final String CHANNEL_TABLE_NAME = "channel";
        public static final String CHANNEL_SCHEME = "content://";
        public static final String PATH_CHANNEL = "/channel";
        public static final String PATH_CHANNEL_ID = "/channel/";
        public static final String PATH_CHANNEL_IS_FAVORITE = "/channel_favorite/";
        public static final int CHANNEL_ID_PATH_POSITION = 1;
        public static final int CHANNEL_FAVORITES_PATH_POSITION = 1;
        public static final Uri CHANNEL_CONTENT_URI = Uri.parse(CHANNEL_SCHEME + AUTHORITY + PATH_CHANNEL);
        public static final Uri CHANNEL_CONTENT_ID_URI_BASE = Uri.parse(CHANNEL_SCHEME + AUTHORITY + PATH_CHANNEL_ID);
        public static final Uri CHANNEL_FAVORITE = Uri.parse(CHANNEL_SCHEME + AUTHORITY + PATH_CHANNEL_IS_FAVORITE);
        public static final String CHANNEL_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.channel";
        public static final String CHANNEL_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.channel";
        public static final String DEFAULT_CHANNELS_SORT_ORDER = "_id ASC";
        public static final String CHANNEL_COLUMN_NAME_NAME = "name";
        public static final String CHANNEL_COLUMN_NAME_URL = "url";
        public static final String CHANNEL_COLUMN_NAME_PICTURE = "picture";
        public static final String CHANNEL_COLUMN_NAME_CATEGORY_ID = "category_id";
        public static final String CHANNEL_COLUMN_NAME_IS_FAVORITE = "is_favorite";
        public static final String[] CHANNEL_DEFAULT_PROJECTION = new String[]{
                _ID,
                CHANNEL_COLUMN_NAME_NAME,
                CHANNEL_COLUMN_NAME_URL,
                CHANNEL_COLUMN_NAME_PICTURE,
                CHANNEL_COLUMN_NAME_CATEGORY_ID,
                CHANNEL_COLUMN_NAME_IS_FAVORITE
        };
    }

    public static final class ProgramsContract implements BaseColumns {
        private ProgramsContract() {
        }

        public static final String PROGRAMS_TABLE_NAME = "programs";
        public static final String PROGRAMS_SCHEME = "content://";
        public static final String PATH_PROGRAMS = "/programs";
        public static final String PATH_PROGRAMS_ID = "/programs/";
        public static final int PROGRAMS_ID_PATH_POSITION = 1;
        public static final int PROGRAMS_TIMESTAMP_PATH_POSITION = 2;
        public static final Uri PROGRAMS_CONTENT_URI = Uri.parse(PROGRAMS_SCHEME + AUTHORITY + PATH_PROGRAMS);
        public static final Uri PROGRAMS_CONTENT_ID_URI_BASE = Uri.parse(PROGRAMS_SCHEME + AUTHORITY + PATH_PROGRAMS_ID);
        public static final String PROGRAMS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.programs";
        public static final String PROGRAMS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.programs";
        public static final String DEFAULT_PROGRAMS_SORT_ORDER = "time ASC";
        public static final String PROGRAMS_COLUMN_NAME_CHANNEL_ID = "channel_id";
        public static final String PROGRAMS_COLUMN_NAME_DATE = "program_date";
        public static final String PROGRAMS_COLUMN_NAME_TIME = "time";
        public static final String PROGRAMS_COLUMN_NAME_TITLE = "title";
        public static final String PROGRAMS_COLUMN_NAME_DESCRIPTION = "description";
        public static final String[] PROGRAMS_DEFAULT_PROJECTION = new String[]{
                _ID,
                PROGRAMS_COLUMN_NAME_CHANNEL_ID,
                PROGRAMS_COLUMN_NAME_DATE,
                PROGRAMS_COLUMN_NAME_TIME,
                PROGRAMS_COLUMN_NAME_TITLE,
                PROGRAMS_COLUMN_NAME_DESCRIPTION
        };
    }

}
