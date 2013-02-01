package org.misty.rc.Endymion;

import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.UserDictionary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/31
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public final class MediaProvider extends ContentProvider {
    private static final String AUTHORITY = "org.misty.rc.provider";
    private static final String SQLITE_DBNAME = "media.sqlite";

    public enum Contract {
        FOLDER(BaseColumns._ID, "path", "display_name", "mime_type"),
        SMART(BaseColumns._ID, "path", "display_name", "mime_type");

        Contract(final String...columns) {
            this.columns = Collections.unmodifiableList(Arrays.asList(columns));
        }

        private final String tableName = name().toLowerCase();
        private final int allCode = ordinal() * 10;
        private final int byIdCode = ordinal() * 10 + 1;
        private final Uri contentUri = Uri.parse("content://" + AUTHORITY + "/" + tableName);
        private final String mimeTypeForOne = "vnd.android.cursor.item/vnd.misty." + tableName;
        private final String mimeTypeForMany = "vnd.android.cursor.dir/vnd.misty." + tableName;
        public final List<String> columns;
    }

    private static final UriMatcher _UriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        _UriMatcher.addURI(AUTHORITY, Contract.FOLDER.tableName, Contract.FOLDER.allCode);
        _UriMatcher.addURI(AUTHORITY, Contract.FOLDER.tableName + "/#", Contract.FOLDER.byIdCode);
        _UriMatcher.addURI(AUTHORITY, Contract.SMART.tableName, Contract.SMART.allCode);
        _UriMatcher.addURI(AUTHORITY, Contract.SMART.tableName + "/#", Contract.SMART.byIdCode);
    }

    private SQLite _OpenHelper;

    private static class SQLite extends SQLiteOpenHelper {

        public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.beginTransaction();
            try {
                // create table DDL

            } finally {
                db.endTransaction();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    @Override
    public boolean onCreate() {
        final int _version;
        try {
            _version = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        _OpenHelper = new SQLite(getContext(), SQLITE_DBNAME, null, _version);
        return true;
    }

    private void checkUri(Uri uri) {
        final int code = _UriMatcher.match(uri);
        for (final Contract contract : Contract.values()) {
            if(code == contract.allCode) {
                return;
            } else if (code == contract.byIdCode) {
                return;
            }
        }
        throw new IllegalArgumentException("unknown uri: " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        checkUri(uri);
        SQLiteDatabase db = _OpenHelper.getReadableDatabase();

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
