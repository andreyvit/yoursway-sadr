package com.esko.dtl.core.tests.internal;import java.io.File;public class FileUtil {    public static boolean recursiveDelete(File path) {        if (path.isDirectory()) {            File[] files = path.listFiles();            for (int i = 0; i < files.length; i++) {                if (files[i].isDirectory()) {                    recursiveDelete(files[i]);                } else {                    files[i].delete();                }            }        }        return path.delete();    }    }