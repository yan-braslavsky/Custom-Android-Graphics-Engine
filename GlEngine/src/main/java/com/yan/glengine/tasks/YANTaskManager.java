package com.yan.glengine.tasks;

import java.util.ArrayList;

/**
 * Created by ybra on 12.12.2014.
 */
public class YANTaskManager {

    private static YANTaskManager INSTANCE = new YANTaskManager();
    private ArrayList<YANTask> mTaskList = new ArrayList<>();

    private YANTaskManager() {
    }

    public static YANTaskManager getInstance() {
        return INSTANCE;
    }

    public void update(float deltaSeconds) {
        for (YANTask task : mTaskList) {
            task.onUpdate(deltaSeconds);
        }
    }

    public void addTask(YANTask task) {
        mTaskList.add(task);
    }

    public void removeTask(YANTask task) {
        mTaskList.remove(task);
    }

}
