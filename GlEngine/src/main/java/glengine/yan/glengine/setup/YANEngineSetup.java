package glengine.yan.glengine.setup;

import glengine.yan.glengine.nodes.YANBaseNode;
import glengine.yan.glengine.tween.YANTweenNodeAccessor;

import aurelienribon.tweenengine.Tween;

/**
 * Created by Yan-Home on 10/25/2014.
 */
public class YANEngineSetup {

    public static void setupTweenEngine(){
        //register the node accessor on the tween engine
        Tween.registerAccessor(YANBaseNode.class, new YANTweenNodeAccessor());
    }

}
