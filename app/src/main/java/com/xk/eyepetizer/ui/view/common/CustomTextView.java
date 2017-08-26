package com.xk.eyepetizer.ui.view.common;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.DimenRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.xk.eyepetizer.util.DisplayManager;

import java.util.ArrayList;

/**
 * Created by xuekai on 2017/8/25.
 */

public class CustomTextView extends AppCompatTextView{

        private Paint mPaint = null;
        private Paint.FontMetrics fm;
        private float offset;
        private String content = "测试数据";
        private float lineSpace = 0;

        public CustomTextView(Context context) {
            super(context);
//            init();
        }

        public CustomTextView(Context context, AttributeSet set) {
            super(context, set, 0);
//            init();
        }

        public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
//            init();
        }

//        private void init() {
//            mPaint = new Paint();
//            mPaint.setAntiAlias(true);
//            mPaint.setColor(Color.WHITE);
//            mPaint.setTextSize(dip2px(16));
//            initParams();
//        }

        private void initParams() {
            fm = mPaint.getFontMetrics();
            if (lineSpace > 0) {
                fm.leading = lineSpace;
            }else{
                fm.leading = dip2px(1);
            }
            offset = fm.descent - fm.ascent + fm.leading;
        }

        public static int dip2px(float dpValue) {
            return DisplayManager.INSTANCE.dip2px(dpValue);
        }

//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//            int width;
//            int height;
//
//            if (widthMode == MeasureSpec.EXACTLY) {
//                width = widthSize;
//            } else if (widthMode == MeasureSpec.AT_MOST) {
//                int textWidth = (int) mPaint.measureText(content);
//                width = textWidth + getPaddingLeft() + getPaddingRight() > widthSize ? widthSize : textWidth + getPaddingLeft() + getPaddingRight();
//            } else {
//                int textWidth = (int) mPaint.measureText(content);
//                width = textWidth + getPaddingLeft() + getPaddingRight();
//            }
//            if (heightMode == MeasureSpec.EXACTLY) {
//                height = heightSize;
//            } else if (heightMode == MeasureSpec.AT_MOST) {
//                int lines = calculateLines(content, width - getPaddingLeft() - getPaddingRight()).size();
//                int indeedHeight = getPaddingTop() + getPaddingBottom() + (int) offset * lines + (int)fm.bottom;
//                height = indeedHeight > heightSize ? heightSize : indeedHeight;
//            } else {
//                int lines = calculateLines(content, width - getPaddingLeft() - getPaddingRight()).size();
//                height = getPaddingTop() + getPaddingBottom() + (int) offset * lines + (int)fm.bottom;
//            }
//            setMeasuredDimension(width, height);
//        }

//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//            float x = getPaddingLeft();
//            float y = -fm.top + getPaddingTop();
//            ArrayList<String> list = calculateLines(content, getWidth() - getPaddingLeft() - getPaddingRight());
//            for (String text : list) {
//                canvas.drawText(text, x, y, mPaint);
//                y += offset;
//            }
//        }

        private ArrayList<String> list = new ArrayList<>(0);

        private ArrayList<String> calculateLines(String content, int width) {
            list.clear();
            int length = content.length();
            float textWidth = mPaint.measureText(content);
            if (textWidth <= width) {
                list.add(content);
                return list;
            }

            int start = 0, end = 1;
            while (start < length) {
                if (mPaint.measureText(content, start, end) > width) {
                    list.add(content.substring(start, end - 1));
                    start = end - 1;
                } else if (end < length) {
                    end++;
                }
                if (end == length) {
                    list.add(content.subSequence(start, end).toString());
                    break;
                }
            }
            return list;
        }

//        public void setText(String text) {
//            if (null == text || text.trim().length() == 0) {
//                content = "";
//            } else {
//                content = text;
//            }
//            invalidate();
//        }

//        /**
//         * @param textColor R.color.xx
//         */
//        public void setTextColor(@ColorRes int textColor) {
//            mPaint.setColor(getResources().getColor(textColor));
//            invalidate();
//        }
//
//        /**
//         * @param textSize R.dimen.xx
//         */
//        public void setTextSize(@DimenRes int textSize) {
//            mPaint.setTextSize(getResources().getDimension(textSize));
//            initParams();
//            invalidate();
//        }

        /**
         * @param spacing R.dimen.xx
         */
        public void setLineSpacingExtra(@DimenRes int spacing) {
            this.lineSpace = getResources().getDimension(spacing);
            initParams();
            invalidate();
        }
}
