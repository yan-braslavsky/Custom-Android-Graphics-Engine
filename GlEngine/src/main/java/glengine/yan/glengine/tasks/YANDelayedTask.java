package glengine.yan.glengine.tasks;

import glengine.yan.glengine.util.object_pool.YANIPoolableObject;

/**
 * Created by ybra on 12.12.2014.
 */
public class YANDelayedTask implements YANTask, YANIPoolableObject {

    private volatile float mDurationSeconds;
    private YANDelayedTaskListener mDelayedTaskListener;
    private volatile boolean running;

    @Override
    public void resetState() {
        stop();
        mDelayedTaskListener = null;
    }

    public interface YANDelayedTaskListener {
        void onComplete();
    }

    public YANDelayedTask() {
        //Default constructor is required by an object pool
    }

    @Override
    public void onUpdate(float deltaSeconds) {
        mDurationSeconds -= deltaSeconds;
        if (running && (mDurationSeconds <= 0)) {
            finishTask();
        }
    }

    private void finishTask() {
        stop();
        if (mDelayedTaskListener != null) {
            mDelayedTaskListener.onComplete();
        }
    }

    @Override
    public void start() {
        running = true;
        YANTaskManager.getInstance().addTask(this);
    }

    @Override
    public void stop() {
        running = false;
    }

    /**
     * This amount is used to count down the task execution.
     * Cannot be modified during the execution of a task
     */
    public void setDurationSeconds(float durationSeconds) {
        mDurationSeconds = durationSeconds;
    }


    @Override
    public boolean isRunning() {
        return running;
    }

    public void setDelayedTaskListener(YANDelayedTaskListener delayedTaskListener) {
        mDelayedTaskListener = delayedTaskListener;
    }
}
