package com.yoursway.sadr.engine.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {
    
    private static Activator instance;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        instance = this;
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        instance = null;
        super.stop(context);
    }
    
    public static Activator instance() {
        return instance;
    }
    
}
