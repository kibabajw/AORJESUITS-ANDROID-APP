package org.easternafricajesuits.monthlythought;

import android.provider.BaseColumns;

public class MonthlyThoughtContract {

    public MonthlyThoughtContract() {

    };

    public static final class ThoughtEntry implements BaseColumns {
        public final static String TABLE_NAME = "monthlythought";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_THOUGHT_HEADING = "heading";
        public final static String COLUMN_THOUGHT_TITLE = "title";
        public final static String COLUMN_THOUGHT_SUB_TITLE = "subtitle";
        public final static String COLUMN_THOUGHT_IMAGE = "image";
        public final static String COLUMN_THOUGHT_UPDATED_AT = "pageupdatedat";

    }
}
