package org.misty.rc.Endymion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/17
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class EndymionUtil {

    public static List<String> toStringList(Set<String> collection) {
        List<String> _list = new ArrayList<String>();
        _list.addAll(collection);

        return _list;
    }


    public static List<?> toListFromSet(Set<?> collection) {
        List<?> _list = new ArrayList<Object>();

        return _list;
    }
}
