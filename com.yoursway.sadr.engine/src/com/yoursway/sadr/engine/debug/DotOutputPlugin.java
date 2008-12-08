package com.yoursway.sadr.engine.debug;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.yoursway.utils.YsFileUtils.writeString;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;

import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.engine.incremental.EngineListener;
import com.yoursway.sadr.engine.incremental.IncrementalAnalysisEngine;
import com.yoursway.utils.YsDebugging;

public class DotOutputPlugin implements EngineListener {
    
    private static final String INDENT = "  ";
    
    static class SessionInfo {
        
        final String name;
        
        public SessionInfo(int goalId, int sessionOrd) {
            this.name = "g" + goalId + "_" + sessionOrd;
        }
        
        Collection<SessionInfo> children = newArrayList();
        
        Collection<SessionInfo> recursiveDeps = newArrayList();
        
        boolean finished;
        
        boolean recursive;
        
    }
    
    static class GoalInfo {
        
        final Goal<?> goal;
        
        final String label;
        
        final Collection<SessionInfo> sessions = newArrayList();
        
        SessionInfo lastSession;
        
        final int id;
        
        public GoalInfo(Goal<?> goal, int id) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.goal = goal;
            String tempName = YsDebugging.escape(goal.toString());
            this.label = tempName.substring(0, 1) + id + " " + tempName.substring(1);
            this.id = id;
            addSession(new SessionInfo(id, 0));
        }
        
        @Override
        public String toString() {
            return label;
        }
        
        boolean needsNewSession() {
            return lastSession == null || lastSession.finished;
        }
        
        public void addSession(SessionInfo sessionInfo) {
            sessions.add(sessionInfo);
            lastSession = sessionInfo;
        }
        
    }
    
    int nextId = 1;
    
    Map<Goal<?>, GoalInfo> goals = newHashMap();
    
    Collection<GoalInfo> goalInfos = newArrayList();
    
    Goal<?> activeGoal;
    
    private final File outputFile;
    
    private final boolean dumpOnEveryChange;
    
    public DotOutputPlugin(File outputFile, IncrementalAnalysisEngine engine, boolean dumpOnEveryChange,
            final int tcpPort) {
        if (outputFile == null)
            throw new NullPointerException("outputFile is null");
        if (engine == null)
            throw new NullPointerException("engine is null");
        this.outputFile = outputFile;
        engine.addListener(this);
        this.dumpOnEveryChange = dumpOnEveryChange;
        if (tcpPort > 0) {
            Thread thread = new Thread("Engine TCP debug plugin") {
                @Override
                public void run() {
                    try {
                        ServerSocket serverSocket = new ServerSocket(tcpPort);
                        serverSocket.setReuseAddress(true);
                        while (true) {
                            Socket socket = serverSocket.accept();
                            socket.getOutputStream().write(DotOutputPlugin.this.toString().getBytes("utf-8"));
                            socket.close();
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace(System.err);
                    }
                }
            };
            thread.setDaemon(true);
            thread.start();
        }
    }
    
    public synchronized void goalExecutionStarting(Goal<?> goal) {
        activeGoal = goal;
        lookup(goal);
        if (dumpOnEveryChange)
            updateOutputFile();
    }
    
    public synchronized void goalFinished(Goal<?> goal, Boolean isRecursive, Result result) {
        SessionInfo session = lookup(goal).lastSession;
        session.finished = true;
        session.recursive = isRecursive;
    }
    
    public synchronized void goalParentAdded(Goal<?> goal, Goal<?> parent) {
        GoalInfo goalInfo = lookup(goal);
        GoalInfo parentInfo = lookup(parent);
        parentInfo.lastSession.children.add(goalInfo.lastSession);
    }
    
    public synchronized void goalScheduled(Goal<?> goal) {
        GoalInfo info = lookup(goal);
        if (info.needsNewSession())
            info.addSession(new SessionInfo(info.id, info.sessions.size() + 1));
        info.lastSession.finished = false;
    }
    
    private void updateOutputFile() {
        try {
            writeString(outputFile, toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    GoalInfo lookup(Goal<?> goal) {
        GoalInfo result = goals.get(goal);
        if (result == null) {
            result = new GoalInfo(goal, nextId++);
            goals.put(goal, result);
            goalInfos.add(result);
        }
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        toString(builder);
        return builder.toString();
    }
    
    public synchronized void toString(StringBuilder result) {
        result.append("digraph Goals {\n");
        for (GoalInfo info : goalInfos) {
            result.append("\n");
            toStringNode(info, INDENT, result);
            toStringEdges(info, INDENT, result);
        }
        result.append("\n");
        result.append("}\n");
    }
    
    private void toStringEdges(GoalInfo info, String indent, StringBuilder result) {
        for (SessionInfo sinfo : info.sessions) {
            for (SessionInfo child : sinfo.children)
                result.append(indent).append(sinfo.name).append(" -> ").append(child.name).append(";\n");
            for (SessionInfo dep : sinfo.recursiveDeps)
                result.append(indent).append(sinfo.name).append(" -> ").append(dep.name).append(
                        " [style=dotted,weight=0];\n");
        }
    }
    
    private void toStringNode(GoalInfo info, String indent, StringBuilder result) {
        Collection<SessionInfo> sessions = info.sessions;
        if (sessions.size() < 2)
            toStringSessionNode(info, info.lastSession, indent, info.label, result);
        else {
            result.append(indent).append("subgraph cluster_").append(info.id).append(" {\n");
            result.append(indent).append(INDENT).append("color = blue;\n");
            result.append(indent).append(INDENT).append("label = ").append(introduceNewLine(info.label))
                    .append(";\n");
            int index = 1;
            for (SessionInfo sinfo : sessions) {
                toStringSessionNode(info, sinfo, indent + INDENT, YsDebugging.escape("Iter. " + index++),
                        result);
            }
            result.append(indent).append("}\n");
        }
    }
    
    private void toStringSessionNode(GoalInfo ginfo, SessionInfo sinfo, String indent, String label,
            StringBuilder result) {
        String color, style;
        if (sinfo.finished && sinfo.recursive) {
            color = "red";
            style = "filled";
        } else if (sinfo.finished) {
            color = "black";
            style = "solid";
        } else if (ginfo.goal == activeGoal) {
            color = "green";
            style = "filled";
        } else {
            color = "grey";
            style = "solid";
        }
        label = introduceNewLine(label);
        result.append(indent).append(sinfo.name).append(" [label=").append(label).append(",color=").append(
                color).append(",style=").append(style).append("];\n");
    }
    
    private String introduceNewLine(String label) {
        int pos = label.indexOf("<");
        if (pos >= 0)
            label = label.substring(0, pos) + "\\n" + label.substring(pos);
        return label;
    }
    
    public synchronized void rootGoalFinished(Goal<?> goal) {
        updateOutputFile();
    }
    
    public synchronized void goalRecursiveDependencyAdded(Goal<?> goal, Goal<?> dependsOn) {
        lookup(goal).lastSession.recursiveDeps.add(lookup(dependsOn).lastSession);
    }
    
}
