package com.yoursway.sadr.ruby.core.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.yoursway.sadr.ruby.core.tests.internal.StringInputStream;


public class TestUtils {

    public static String joinPath(String c1, String c2) {
        IPath path = new Path(c1).append(c2);
        return path.toPortableString();
    }

    public static String joinPath(String c1, String c2, String c3) {
        return joinPath(joinPath(c1, c2), c3);
    }

    public static String readFile(final String fileName) throws IOException {
        InputStream in = RubyTestsPlugin.openResource(fileName);
        return TestUtils.readAndClose(in);
    }

    public static String readAndClose(InputStream in) throws IOException {
        try {
            StringBuffer result = new StringBuffer();
            InputStreamReader reader = new InputStreamReader(in);
            char[] buf = new char[1024];
            while (true) {
                int read = reader.read(buf);
                if (read <= 0)
                    break;
                result.append(buf, 0, read);
            }
            return result.toString();
        } finally {
            in.close();
        }
    }

    public static String removeExtension(final String fileName) {
        return new Path(fileName).removeFileExtension().lastSegment();
    }

    public static void replaceContents(IFile oldFile, IFile newFile) throws IOException, CoreException {
        String oldName = removeExtension(oldFile.getName());
        String newName = removeExtension(newFile.getName());
        String original = readAndClose(newFile.getContents());
        String fixed = original.replaceAll("class " + newName, "class " + oldName);
        oldFile.setContents(new StringInputStream(fixed), true, false, null);
    }
    
}
