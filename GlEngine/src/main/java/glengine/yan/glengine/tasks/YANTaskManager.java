package glengine.yan.glengine.tasks;

import java.util.Vector;

/**
 * Created by ybra on 12.12.2014.
 */
public class YANTaskManager {

    private static YANTaskManager INSTANCE = new YANTaskManager();
    private Vector<YANTask> mTaskList = new Vector<>();

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
