package org.misty.rc.Endymion.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/28
 * Time: 9:27
 * To change this template use File | Settings | File Templates.
 */
public class MediaInfoComparator implements Comparator<MediaInfo>, Serializable {
    public static final int NAME_SORT = 1;
    public static final int DATE_SORT = 2;
    public static final int SIZE_SORT = 3;
    public static final int TAG_SORT = 4;

    private int _key;

    public MediaInfoComparator(int key) {
        _key = key;
    }

    @Override
    public int compare(MediaInfo lhs, MediaInfo rhs) {
        switch (_key) {
            case NAME_SORT:
                break;
            case DATE_SORT:
                break;
            case SIZE_SORT:
                break;
            case TAG_SORT:
                break;
        }
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
