package org.misty.rc.Endymion.model;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/29
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class FolderInfo {
    public static final int FLAG_STATIC_FOLDER = 0x1;
    public static final int FLAG_SMART_FOLDER = 0x2;

    private File _file;
    private int _flag;

    public FolderInfo(File file, int attr) {
        _file = file;
        _flag = attr;
    }

    public String getName() {
        return _file.getName();
    }

    public int getFileCount() {
        return _file.listFiles().length;
    }

    public int getCount() {
        return _file.list().length;
    }

    public String getAbsPath() {
        return _file.getAbsolutePath();
    }

    public int getFlag() {
        return _flag;
    }

}
