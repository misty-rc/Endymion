package org.misty.rc.Endymion.FileSelector;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/01/17
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class FileInfo implements Comparable<FileInfo> {
    private String _filename;
    private File _fileObj;

    public FileInfo(String label, File file) {
        _filename = label;
        _fileObj = file;
    }

    public String getName() {
        return _filename;
    }

    public File getFile() {
        return _fileObj;
    }

    public boolean isDirectory() {
        return _fileObj.isDirectory();
    }

    @Override
    public int compareTo(FileInfo another) {
        if(true == _fileObj.isDirectory() && false == another.getFile().isDirectory()) {
            return 1;
        }
        if(false == _fileObj.isDirectory() && true == another.getFile().isDirectory()) {
            return -1;
        }
        return 0;
    }
}
