package glengine.yan.glengine.tasks;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ybra on 12.12.2014.
 */
public class YANTaskManager {

    private static YANTaskManager INSTANCE = new YANTaskManager();
    private volatile ArrayList<YANTask> mTaskList = new ArrayList<>();

    private YANTaskManager() {
    }

    public static YANTaskManager getInstance() {
        return INSTANCE;
    }

    public void update(float deltaSeconds) {

        if(mTaskList.isEmpty())
            return;

        Iterator<YANTask> it = mTaskList.iterator();
        while (it.hasNext()) {
            YANTask task = it.next();
            task.onUpdate(deltaSeconds);

            //remove finished task
            //this is the only place where task can be removed
            if (!task.isRunning())
                it.remove();
        }
    }

    protected void addTask(YANTask task) {
        mTaskList.add(task);
    }
}
