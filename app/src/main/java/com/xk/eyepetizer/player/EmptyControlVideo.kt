package com.xk.eyepetizer.player

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.xk.eyepetizer.R

/**
 * 无ui
 * Created by xuekai on 2017/8/22.
 */
class EmptyControlVideo(context: Context?, attrs: AttributeSet?) : StandardGSYVideoPlayer(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun getLayoutId(): Int {
        return R.layout.empty_control_video
    }


    open class EmptyControlVideoCallBack : VideoAllCallBack {
        override fun onEnterFullscreen(url: String?, vararg objects: Any?) {

        }

        override fun onClickResume(url: String?, vararg objects: Any?) {
        }

        override fun onClickSeekbarFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onClickBlankFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onPrepared(url: String?, vararg objects: Any?) {
        }

        override fun onClickStartIcon(url: String?, vararg objects: Any?) {
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
        }

        override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {
        }

        override fun onTouchScreenSeekVolume(url: String?, vararg objects: Any?) {
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
        }

        override fun onTouchScreenSeekLight(url: String?, vararg objects: Any?) {
        }

        override fun onClickSeekbar(url: String?, vararg objects: Any?) {
        }

        override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onPlayError(url: String?, vararg objects: Any?) {
        }

        override fun onClickStartThumb(url: String?, vararg objects: Any?) {
        }

        override fun onEnterSmallWidget(url: String?, vararg objects: Any?) {
        }

        override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {
        }

        override fun onClickStartError(url: String?, vararg objects: Any?) {
        }

        override fun onTouchScreenSeekPosition(url: String?, vararg objects: Any?) {
        }

        override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {
        }

    }

}
