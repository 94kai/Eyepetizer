//package com.xk.eyepetizer.playbackop;
//
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MotionEvent;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
///**
// * @author xuekai1
// * @date 2018/12/27
// */
//public class BaseActivity extends AppCompatActivity {
//
//    Handler handler = new Handler();
//    /**
//     * 存放当前activity中的事件
//     */
//    private Queue<State> activityEvents;
//    /**
//     * 当前activity可见之后的时间点（每次onresume之后都创建一个新的队列，同时也赋值新的statetime）
//     */
//    private long startTime = 0;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        record();
//        play();
//    }
//
//    public void record() {
//        if (OPStates.isRecord) {
//            OPStates.play = false;
//            activityEvents = new LinkedList<>();
//            startTime = System.currentTimeMillis();
//            OPStates.states.add(activityEvents);
//        }
//    }
//
//    public void play() {
//        if (OPStates.play) {
//            OPStates.isRecord = false;
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!OPStates.states.isEmpty()) {
//                                Queue<State> pop = OPStates.states.remove();
//                                while (!pop.isEmpty()) {
//                                    final State state = pop.remove();
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (state.event == null) {
//                                                //简单粗暴的认为点击返回键就是finish
//                                                finish();
//                                            } else {
//                                                dispatchTouchEvent(state.event);
//                                            }
//                                        }
//                                    }, state.time);
//                                }
//                            }
//                        }
//                    }).start();
//                }
//            }, 1000);
//
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        //简单粗暴的把这里假设成点了返回键
//        State state = new State();
//        state.event = null;
//        state.time = System.currentTimeMillis() - startTime;
//        activityEvents.add(state);
//        super.onBackPressed();
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (OPStates.isRecord && activityEvents != null) {
//            MotionEvent obtain = MotionEvent.obtain(ev);
//            State state = new State();
//            state.event = obtain;
//            state.time = System.currentTimeMillis() - startTime;
//            activityEvents.add(state);
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//}
