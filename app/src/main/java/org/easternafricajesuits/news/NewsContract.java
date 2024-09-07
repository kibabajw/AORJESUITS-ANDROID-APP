package org.easternafricajesuits.news;

import android.provider.BaseColumns;

public class NewsContract {

    public NewsContract() {

    };

    public static final class NewsEntry implements BaseColumns {
        public final static String TABLE_NAME = "news";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_CREATED_AT = "createdat";
        public final static String COLUMN_IMAGE = "image";
        public final static String COLUMN_BODY = "body";
    }

}
