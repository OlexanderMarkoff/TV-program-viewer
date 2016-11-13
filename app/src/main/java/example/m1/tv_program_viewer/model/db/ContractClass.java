package example.m1.tv_program_viewer.model.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by M1 on 12.11.2016.
 */

public final class ContractClass {
    public static final String AUTHORITY = "example.m1.tv_program_viewer.model.db.ContractClass";

    private ContractClass() {
    }

    public static final class ChannelContract implements BaseColumns {
        private ChannelContract() {
        }

        public static final String TABLE_NAME = "channel";
        private static final String SCHEME = "content://";
        private static final String PATH_CHANNEL = "/channel";
        private static final String PATH_CHANNEL_ID = "/channel/";
        private static final String PATH_CHANNEL_IS_FAVORITE = "/channel_favorite/";
        public static final int CHANNEL_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_CHANNEL);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_CHANNEL_ID);
        public static final Uri CONTENT_FAVORITES_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_CHANNEL_IS_FAVORITE);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.channel";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.channel";
        public static final String DEFAULT_SORT_ORDER = "_id ASC";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_PICTURE = "picture";
        public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
        public static final String COLUMN_NAME_IS_FAVORITE = "is_favorite";
        public static final String[] DEFAULT_PROJECTION = new String[]{
                ContractClass.ChannelContract._ID,
                ContractClass.ChannelContract.COLUMN_NAME_NAME,
                ChannelContract.COLUMN_NAME_URL,
                ChannelContract.COLUMN_NAME_PICTURE,
                ChannelContract.COLUMN_NAME_CATEGORY_ID,
                ChannelContract.COLUMN_NAME_IS_FAVORITE
        };
    }

}
