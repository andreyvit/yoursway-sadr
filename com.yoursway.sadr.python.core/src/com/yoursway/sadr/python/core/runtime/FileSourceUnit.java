package com.yoursway.sadr.python.core.runtime;

import static com.yoursway.utils.YsFileUtils.readAsString;

import java.io.File;
import java.io.IOException;

public class FileSourceUnit {
    
    private final File file;
    private final String path;
    
    public FileSourceUnit(File file, String path) {
        this.file = file;
        this.path = path;
    }
    
    public char[] getSourceAsCharArray() throws IOException {
        return getSource().toCharArray();
    }
    
    public String getSource() throws IOException {
        return readAsString(file);
    }
    
    public File getFile() {
        return file;
    }
    
    public String getPathName() {
        //        String path = module.getParent().getElementName();
        //        if (path.length() != 0)
        //            path = path + "/";
        //        String moduleName = path + module.getElementName();
        return path;
    }
    
    @Override
    public String toString() {
        return getPathName();
    }
}
