package glengine.yan.glengine.tasks;

/**
 * Created by ybra on 12.12.2014.
 */
interface YANTask {

    /**
     * Called every frame of rendering loop
     *
     * @param deltaSeconds
     */
    void onUpdate(float deltaSeconds);

    /**
     * Starts the task from the beginning
     */
    void start();

    /**
     * Stops the task entirely
     */
    void stop();

    /**
     * Returns true if the task is still running.
     * Otherwise returns false
     */
    public boolean isRunning();

}
