package com.yoursway.sadr.ruby.core.staticchecks;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class RubyProblemReporter implements IRubyProblemReporter {
    
    private final IResource resource;
    
    public RubyProblemReporter(IResource resource) {
        this.resource = resource;
    }
    
    public void error(String message, int startOffset, int endOffset) {
        IMarker marker;
        try {
            marker = resource.createMarker(MARKER_TYPE);
            //            marker.setAttribute(IMarker.LINE_NUMBER, 1);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
            marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
            marker.setAttribute(IMarker.CHAR_START, startOffset);
            marker.setAttribute(IMarker.CHAR_END, endOffset);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
    
    public void warning(String message, int startOffset, int endOffset) {
        IMarker marker;
        try {
            marker = resource.createMarker(MARKER_TYPE);
            //            marker.setAttribute(IMarker.LINE_NUMBER, 1);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
            marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
            marker.setAttribute(IMarker.CHAR_START, startOffset);
            marker.setAttribute(IMarker.CHAR_END, endOffset);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
    
    public void info(String message, int startOffset, int endOffset) {
        IMarker marker;
        try {
            marker = resource.createMarker(MARKER_TYPE);
            //            marker.setAttribute(IMarker.LINE_NUMBER, 1);
            marker.setAttribute(IMarker.MESSAGE, message);
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
            marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
            marker.setAttribute(IMarker.CHAR_START, startOffset);
            marker.setAttribute(IMarker.CHAR_END, endOffset);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
    
    public void reset() {
        try {
            resource.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        
    }
    
}
