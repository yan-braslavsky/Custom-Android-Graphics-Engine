package com.yan.glengine.setup;

import com.yan.glengine.R;
import com.yan.glengine.nodes.YANBaseNode;
import com.yan.glengine.tween.YANTweenNodeAccessor;

import java.util.HashMap;

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
