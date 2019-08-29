package com.example.music.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.music.Beens.LrcBeen;
import com.example.music.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by Administrator on 2018/5/19.
 */

@SuppressLint("AppCompatCustomView")
public class lrcText extends TextView implements View.OnTouchListener{
    private LrcBeen.DataBean dataBean;
    // 标记当前行
    private int currentLine=0;
    private Paint currentPaint;
    private Paint otherPaint;
    private Paint timePaint;
    private int currentColor = Color.YELLOW;

    private int otherColor = Color.WHITE;

    private int timeColor = Color.BLACK;

    // 行间距
    private int lineSpace = 50;
    //当前歌词字体
    private Typeface currentTypeface = Typeface.DEFAULT_BOLD;
    //其他歌词字体
    private Typeface otherTypeface = Typeface.DEFAULT;
    private boolean hanschange;
    private boolean IsDrawLine;
    private boolean IsSrcoll;

    private  float offset;
    private int progree;
    private GestureDetector detector;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if(currentLine>=dataBean.getLrclist().size()){
                Toast.makeText(getContext(),"播放完成",Toast.LENGTH_SHORT).show();
            }else{
                if(hanschange){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentLine++;
                            invalidate(); // 刷新,会再次调用onDraw方法
                            hanschange=false;
                        }
                    }, (long) (Double.valueOf(dataBean.getLrclist().get(currentLine+1).getTime())-progree)*1000);

                }else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentLine++;
                            invalidate(); // 刷新,会再次调用onDraw方法
                            hanschange=false;
                        }
                    }, (long) (Double.valueOf(dataBean.getLrclist().get(currentLine+1).getTime())*1000
                                                - Double.valueOf(dataBean.getLrclist().get(currentLine).getTime())*1000));
                    hanschange=false;
                }
            }
        }


    };

    public lrcText(Context context, AttributeSet attrs)  {
        super(context, attrs);
        super.setOnTouchListener(this);
        super.setFocusable(true);
        super.setClickable(true);
        super.setLongClickable(true);
        detector = new GestureDetector(getContext(), mSimpleOnGestureListener);
        detector.setIsLongpressEnabled(false);

        currentPaint = new Paint();
        otherPaint = new Paint();
        timePaint = new Paint();
        currentPaint.setColor(currentColor);
        currentPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_18));
        currentPaint.setTextAlign(Paint.Align.CENTER); // 画在中间
        currentPaint.setTypeface(currentTypeface);

        otherPaint.setColor(otherColor);
        otherPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_14));
        otherPaint.setTextAlign(Paint.Align.CENTER);
        otherPaint.setTypeface(otherTypeface);

        timePaint.setColor(timeColor);
        timePaint.setTextAlign(Paint.Align.CENTER);
    }

    public lrcText(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
            if(IsDrawLine){
                canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,currentPaint);
                IsDrawLine=false;
            }

            if(IsSrcoll){
                canvas.translate(0,offset);
                IsSrcoll=false;
            }
            if(dataBean!=null){
                for(int i=0;i<dataBean.getLrclist().size();i++){
                LrcBeen.DataBean.LrclistBean lyric;
                if(i<currentLine){
                    //绘制播放过的歌词
                    lyric = dataBean.getLrclist().get(i);
                    canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                            getHeight() / 2 + lineSpace * (i - currentLine), otherPaint);

                } else if(i==currentLine){
                    // 绘制正在播放的歌词
                    lyric = dataBean.getLrclist().get(currentLine);
                    canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                            getHeight() / 2, currentPaint);
                }else{
                    lyric = dataBean.getLrclist().get(i);
                    canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                            getHeight() / 2 + lineSpace * (i -currentLine), otherPaint);
                }
            }
                handler.sendEmptyMessage(10);

        }else{

        }

        super.onDraw(canvas);
    }

    public void send(LrcBeen.DataBean lrcBeens) {
        dataBean =lrcBeens;
        invalidate();
    }



    public  void sendp(int progress) {
        this.progree=progress;
        currentLine=findShowLine(progress);
        hanschange=true;
        handler.removeCallbacksAndMessages(null);
        invalidate();
        IsDrawLine=true;
        IsSrcoll=true;
    }


    private int findShowLine(double time) {
        if(dataBean!=null){
            int left = 0;
            int right = dataBean.getLrclist().size();
            while (left <= right) {
                int middle = (left + right) / 2;
                double middleTime = dataBean.getLrclist().get(middle).getTime();

                if (time < middleTime) {
                    right = middle - 1;
                } else {
                    if (middle + 1 >= dataBean.getLrclist().size() || time < dataBean.getLrclist().get(middle + 1).getTime()) {
                        return middle;
                    }
                    left = middle + 1;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
    }
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            offset += -distanceY;
            if(dataBean.getLrclist()!=null){
                IsDrawLine=true;
                IsSrcoll=true;
            }
            Log.e("onScroll",offset+"");
            handler.removeCallbacksAndMessages(null);
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(offset>0){
                currentLine= (int) (currentLine-(Math.ceil(Math.abs(offset))/50));
            }else if(offset<0){
                currentLine= (int) (currentLine+(Math.floor(Math.abs(offset))/50));
            }
            if(currentLine<=dataBean.getLrclist().size()){
                EventBus.getDefault().postSticky(dataBean.getLrclist().get(currentLine).getTime());
                handler.removeCallbacksAndMessages(null);
                invalidate();
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            return true;
        }
    };
}
