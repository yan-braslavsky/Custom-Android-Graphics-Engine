package com.yan.glengine.tasks;

/**
 * Created by ybra on 12.12.2014.
 */
public class YANDelayedTask implements YANTask {

    private float mDurationSeconds;

    public YANDelayedTask(float durationSeconds) {
        mDurationSeconds = durationSeconds;
    }

    public interface YANDelayedTaskListener {
        void onComplete();
    }

    @Override
    public void onUpdate(float deltaSeconds) {
        //TODO : calculate time and remove , when task suppose to finish
    }

    @Override
    public void start() {
        //TODO : reset the values of end task calculation and add to the manager
    }

    @Override
    public void stop() {
        //TODO : reset the values of end task calculation and remove from the manager
    }

}
