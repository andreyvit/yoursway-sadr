package com.yoursway.sadr.python.idioms.core;

import java.io.BufferedInputStream;
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
	public static final String PLUGIN_ID = "com.yoursway.sadr.python.idiomsfinder.core";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
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


}
