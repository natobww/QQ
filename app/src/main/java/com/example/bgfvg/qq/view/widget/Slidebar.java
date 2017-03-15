package com.example.bgfvg.qq.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.utils.DensityUtil;


/**
 * Created by BGFVG on 2017/3/15.
 */

public class Slidebar extends View {
    private static final String[] SECTIONS = {"搜", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public int mAcgWidth;
    public int mAvgHeight;
    public Paint mPaint;

    public Slidebar(Context context) {
        this(context, null);
    }

    public Slidebar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public Slidebar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Slidebar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        mAcgWidth = measuredWidth / 2;
        mAvgHeight = measuredHeight / SECTIONS.length;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < SECTIONS.length; i++) {
            canvas.drawText(SECTIONS[i], mAcgWidth, mAvgHeight *(i+1),mPaint);
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 这个h是像素单位 不是SP值
         * 使用环信的工具类
         */
        mPaint.setTextSize(DensityUtil.sp2px(getContext(), 10));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getResources().getColor(R.color.inactive));
    }
}
