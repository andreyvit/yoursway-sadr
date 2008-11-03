package com.yoursway.sadr.python.core.tests.typeinferencing;

import java.io.File;

import com.yoursway.ide.application.model.projects.types.ProjectType;

public class PythonProjectType extends ProjectType {
    
    @Override
    public String generateNewProjectName(File location) {
        return null;
    }
    
    @Override
    public String getDescriptiveName() {
        return "Python";
    }
    
    @Override
    public boolean recognize(File location) {
        return true;
    }
    
}
