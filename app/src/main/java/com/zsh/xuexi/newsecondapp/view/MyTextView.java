package com.zsh.xuexi.newsecondapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by zsh on 2016/7/4.
 * demo：1
 * 自定义TextView控件，
 * 实现重影效果
 */
public class MyTextView extends TextView {
    private Paint mpaint;
    int mViewWidth,mViewHeight;
    int mTranslate;
    LinearGradient mlg;
    Matrix mx;
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();//
        canvas.translate(10, 0);//绘制文字前平移10像素
        //在回调父类方法前，实现自己的逻辑，
        super.onDraw(canvas);//父类完成的方法，即绘制文本
        //在回调父类方法后实现自己的逻辑。
        canvas.restore();//
        if(mx!=null){
            mTranslate+=mViewWidth/5;
            if(mTranslate>2*mViewWidth){
                mTranslate=-mViewWidth;
            }
            mx.setTranslate(mTranslate,0);
            mlg.setLocalMatrix(mx);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth==0){
            mViewWidth=getMeasuredWidth();
            if(mViewWidth>0){
                mpaint=getPaint();
                mlg=new LinearGradient(0,0,mViewWidth,0,new int[]{Color.BLUE,0xffffffff, Color.BLUE},null, Shader.TileMode.CLAMP);
                mpaint.setShader(mlg);
                mx=new Matrix();
            }
        }
    }

    //如果控件里面的高度和宽度设置是wrap_content，那么必须重写onMeasure方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));//通过这个方法设置控件高度宽度
    }
    //确定控件的宽度
    private int measureWidth(int measureSpec){
        int result=0;
        int specMode= MeasureSpec.getMode(measureSpec);//拿到控件的测量模式，有三种：EXACTLY，AT_MOST，UNSPECIFIED
        int specSize= MeasureSpec.getSize(measureSpec);//拿到控件的大小
        if(specMode== MeasureSpec.EXACTLY){//精确值模式，当我们将控件的属性设置为具体的值或者match_parent时，系统使用的就是这个模式
            result=specSize;//把控件的宽度给result;
        }else{
            result=200;//如果为其他模式，则自己设置大小。
            // UNSPECIFIED模式: 这种模式不指定大小测量模式，View想多大就多大
            if(specMode== MeasureSpec.AT_MOST){//最大值模式，控件的尺度只要不超过父控件允许的最大尺寸即可。
                result= Math.min(result,specSize);//取最小值
            }
        }
        return result;//返回值
    }

    //确定控件的高度
    private int measureHeight(int measureSpec){
        int result=0;
        int specMode= MeasureSpec.getMode(measureSpec);
        int specSize= MeasureSpec.getSize(measureSpec);
        if(specMode== MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result=60;
            if(specMode== MeasureSpec.AT_MOST){
                result= Math.min(result,specSize);
            }
        }
        return result;
    }
}







