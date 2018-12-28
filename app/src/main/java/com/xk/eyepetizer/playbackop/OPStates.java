package com.xk.eyepetizer.playbackop;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 以队列的形式存储MotionEvent，以Activity为单位
 *
 * @author xuekai1
 * @date 2018/12/27
 */
public class OPStates {
    /**
     * 存储元素为一个队列，存放一个activity中的操作状态。
     */
    public static Queue<Queue<State>> states = new LinkedList<>();

    /**
     * 是否在录制
     */
    public static boolean isRecord = false;

    public static boolean play = false;
}
