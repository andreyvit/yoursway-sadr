package com.yoursway.sadr.python.core.tests;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.yoursway.sadr.ruby.tests";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static String loadContent(String path) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream input = null;
        try {
            input = openResource(path);
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            char[] data = new char[100 * 1024]; // tests shouldnt be more that 100 kb
            int size = br.read(data);
            if (size > 0)
                buffer.append(data, 0, size);
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return buffer.toString();
    }

    /**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	

    public static InputStream openResource(String path) throws IOException {
        URL url = Activator.getDefault().getBundle().getEntry(path);
        return new BufferedInputStream(url.openStream());
    }


}
