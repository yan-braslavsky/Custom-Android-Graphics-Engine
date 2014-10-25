package com.example.yan_home.openglengineandroid.setup;

import com.example.yan_home.openglengineandroid.nodes.YANBaseNode;
import com.example.yan_home.openglengineandroid.tween.TweenNodeAccessor;

import aurelienribon.tweenengine.Tween;

/**
 * Created by Yan-Home on 10/25/2014.
 */
public class YANEngineSetup {

    public static void setupTweenEngine(){
        //register the node accessor on the tween engine
        Tween.registerAccessor(YANBaseNode.class, new TweenNodeAccessor());
    }
}
