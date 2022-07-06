package org.easternafricajesuits.adusum.databases;

import android.net.Uri;
import android.provider.BaseColumns;

public final class AdusumDatabaseContract {
    
    private AdusumDatabaseContract() {
        
    }

    public static final String PATH_BROTHER = "brother";
    
    public static final class BrotherEntry implements BaseColumns {
        //    Table functionality
        public static final String ADUSUMACCOUNT_TABLE = "ADUSUMACCOUNT";

        public static final String COLUMN_BROTHER_USER_NAME = "username";
        public static final String COLUMN_BROTHER_FULL_NAME = "fullname";
        public static final String COLUMN_BROTHER_DATE_OF_BIRTH = "dateofbirth";
        public static final String COLUMN_BROTHER_DATE_OF_ENTRY = "dateofentry";
        public static final String COLUMN_BROTHER_NAME_OF_PROVINCIAL = "nameofprovincial";
        public static final String COLUMN_BROTHER_CURRENT_COMMUNITY = "currentcommunity";
        public static final String COLUMN_BROTHER_ACCOUNT_PICTURE = "accountpicture";
        public static final String COLUMN_BROTHER_PASSWORD = "password";
        public static final String COLUMN_BROTHER_AUTH_TOKEN = "authtoken";
        public static final String COLUMN_BROTHER_ACCOUNT_STATUS = "accountstatus";
        public static final String COLUMN_BROTHER_COOKIE_TOKEN = "cookietoken";
        public static final String COLUMN_BROTHER_ONLINE_ID = "onlineid";

        public static final String CONTENT_AUTHORITY = "org.easternafricajesuits";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BROTHER);
    }
    
}





















