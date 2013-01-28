package org.misty.rc.Endymion.model;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/28
 * Time: 9:15
 * To change this template use File | Settings | File Templates.
 */
public class MediaInfo implements Comparable<MediaInfo> {

    private File _file;

    public MediaInfo(File file) {
        _file = file;
    }

    @Override
    public int compareTo(MediaInfo another) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
