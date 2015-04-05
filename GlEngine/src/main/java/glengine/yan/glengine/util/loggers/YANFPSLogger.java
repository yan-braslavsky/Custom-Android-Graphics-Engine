package glengine.yan.glengine.util.loggers;

/**
 * Created by Yan-Home on 1/17/2015.
 */
public class YANFPSLogger {

    public interface FPSLoggerListener {
        void onValueChange(int newFpsValue);
    }

    //used for FPS logging
    float mSecondsElapsed;
    int mFramesCounter;
    private FPSLoggerListener mFPSLoggerListener;

    public void update(float deltaTimeSeconds) {
        mSecondsElapsed += deltaTimeSeconds;
        mFramesCounter++;
        if (mSecondsElapsed > 1.0) {
            if (mFPSLoggerListener != null) {
                mFPSLoggerListener.onValueChange(mFramesCounter);
            }
            mSecondsElapsed = 0;
            mFramesCounter = 0;
        }
    }

    public void setFPSLoggerListener(FPSLoggerListener FPSLoggerListener) {
        mFPSLoggerListener = FPSLoggerListener;
    }
}
