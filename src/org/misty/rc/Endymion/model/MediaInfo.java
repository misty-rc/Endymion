package org.misty.rc.Endymion.model;

import java.io.File;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/16
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class MediaInfo implements Comparable<MediaInfo> {
    private String _name; //表示名
    private File _file;

    public MediaInfo(String name, File file) {
        _name = name;
        _file = file;
    }

    public String getName() {
        return _name;
    }

    public File getFile() {
        return _file;
    }

    @Override
    public int compareTo(MediaInfo another) {
        return _file.getName().toLowerCase().compareTo(another.getFile().getName().toLowerCase());
    }
}
