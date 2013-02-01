package org.misty.rc.Endymion;

import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/02/01
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class MediaTable {

    public static class FOLDER {
        public static final String TABLE = "folder";
        public static final String ID = "_id";
        public static final String PATH = "path";
        public static final String DISP_NAME = "disp_name";
        public static final String MIME_TYPE = "mime_type";
    }

    public static class SMART {
        public static final String TABLE = "smart";
        public static final String ID = "_id";
        public static final String SMART_ID = "smart_id";
        public static final String PATH = "path";
    }
}
