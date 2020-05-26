package com.example.music.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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


import com.example.music.model.LrcBeen;
import com.example.music.R;
import com.example.music.utils.PlayController;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

@SuppressLint("AppCompatCustomView")
public class LrcText extends TextView implements View.OnTouchListener{
    private List<LrcBeen.LrclistBean> dataBean;
    // 标记当前行
    private int currentLine=0;
    private Paint currentPaint;
    private Paint otherPaint;
    private Paint timePaint;
    private int currentColor = Color.YELLOW;

    private int otherColor = Color.WHITE;

    private int timeColor = Color.BLACK;

    // 行间距
    private int lineSpace = getResources().getDimensionPixelOffset(R.dimen.dp_30);
    //当前歌词字体
    private Typeface currentTypeface = Typeface.DEFAULT_BOLD;
    //其他歌词字体
    private Typeface otherTypeface = Typeface.DEFAULT;
    private boolean IsDrawLine=false;
    private boolean IsSrcoll=false;

    private  float offset;
    private int progree;
    private int oldProgress;
    private GestureDetector detector;
    private PlayController playController;
    private Context context;
    private MyBroadcastReceiver myBroadcastReceiver;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    //播放下一列歌词
                    if(currentLine>=dataBean.size()-1){
                        Toast.makeText(getContext(),"播放完成",Toast.LENGTH_SHORT).show();
                    }else{
                        currentLine++;
                        invalidate(); // 刷新,会再次调用onDraw方法
                    }
                    break;
                case 11:
                    //播放当前歌词
                    invalidate();
                    break;
                case 12:
                    //滑动事件
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IsDrawLine=false;
                        }
                    },3000);
                    break;
            }
        }

    };

    public LrcText(Context context, AttributeSet attrs)  {
        super(context, attrs);
        playController=new PlayController(context);

        myBroadcastReceiver=new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.music.pro");
        context.registerReceiver(myBroadcastReceiver, filter);

        this.context =context;
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
        otherPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_16));
        otherPaint.setTextAlign(Paint.Align.CENTER);
        otherPaint.setTypeface(otherTypeface);

        timePaint.setColor(timeColor);
        timePaint.setTextAlign(Paint.Align.CENTER);
        IsDrawLine=false;
    }

    public LrcText(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
            if(IsDrawLine){
                Paint p = new Paint();
                p.setColor(getResources().getColor(R.color.colorAccent));
                Path path = new Path();
                path.moveTo(5, getHeight()/2-20);// 此点为多边形的起点
                path.lineTo(5, getHeight()/2+20);
                path.lineTo(24, getHeight()/2);
                path.close(); // 使这些点构成封闭的多边形
                canvas.drawPath(path, p);
                canvas.drawLine(30,getHeight()/2,getWidth(),getHeight()/2,currentPaint);
                for(int i=0;i<dataBean.size();i++){
                    LrcBeen.LrclistBean lyric;
                    if(i<currentLine){
                        //绘制播放过的歌词
                        lyric = dataBean.get(i);
                        canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                                getHeight() / 2+offset + lineSpace * (i - currentLine), otherPaint);

                    } else if(i==currentLine){
                        // 绘制正在播放的歌词
                        lyric = dataBean.get(currentLine);
                        canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                                getHeight() / 2+offset, currentPaint);
                    }else{
                        lyric = dataBean.get(i);
                        canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                                getHeight() / 2+offset + lineSpace * (i -currentLine), otherPaint);
                    }
                }
            }

            if(IsSrcoll){
                canvas.translate(0,offset);
                IsSrcoll=false;
            }

            if(dataBean!=null&&!IsDrawLine){
                for(int i=0;i<dataBean.size();i++){
                LrcBeen.LrclistBean lyric;
                if(i<currentLine){
                    //绘制播放过的歌词
                    lyric = dataBean.get(i);
                    canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                            getHeight() / 2 + lineSpace * (i - currentLine), otherPaint);

                } else if(i==currentLine){
                    // 绘制正在播放的歌词
                    lyric = dataBean.get(currentLine);
                    canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                            getHeight() / 2, currentPaint);
                }else{
                    lyric = dataBean.get(i);
                    canvas.drawText(lyric.getLineLyric(), getWidth() / 2,
                            getHeight() / 2 + lineSpace * (i -currentLine), otherPaint);
                }
            }
        }

        super.onDraw(canvas);
    }

    public void send(List<LrcBeen.LrclistBean> lrcBeens) {
        dataBean =lrcBeens;
        handler.removeCallbacksAndMessages(null);
        IsDrawLine=false;
        IsSrcoll=false;

        if(playController.get_state()==1){
            if(currentLine==0){
                currentLine=findShowLine(playController.get_pro());
                handler.sendEmptyMessage(11);
            }else{
                if(playController.get_pro()>=Double.valueOf(dataBean.get(currentLine+1).getTime())){
                    handler.sendEmptyMessage(10);
                }else{
                    handler.sendEmptyMessage(11);
                }
            }
        }
    }


    private int findShowLine(double time) {
        if(dataBean!=null){
            int left = 0;
            int right = dataBean.size();
            while (left <= right) {
                int middle = (left + right) / 2;
                double middleTime = Double.valueOf(dataBean.get(middle).getTime());

                if (time < middleTime) {
                    right = middle - 1;
                } else {
                    if (middle + 1 >= dataBean.size() || time < Double.valueOf(dataBean.get(middle + 1).getTime())) {
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
            float x = e.getX();
            float y = e.getY();
            if(x>0&&x<30&&y>getHeight()/2-20&&y<getHeight()/2+20){
                handler.removeCallbacksAndMessages(null);
                IsDrawLine=false;
                if(offset>0){
                    currentLine= (int) (currentLine-(Math.ceil(Math.abs(offset))/getResources().getDimensionPixelSize(R.dimen.dp_30)));
                }else if(offset<0){
                    currentLine= (int) (currentLine+(Math.floor(Math.abs(offset))/getResources().getDimensionPixelSize(R.dimen.dp_30)));
                }
                playController.set_pro((Double.valueOf(dataBean.get(currentLine).getTime()).intValue()));
                invalidate();
                return true;
            }else{
                return false;
            }

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                offset += -distanceY;
                if(dataBean!=null){
                    IsDrawLine=true;
                    if(offset<=getResources().getDimensionPixelSize(R.dimen.dp_30)*(dataBean.size()-2)){
                        IsSrcoll=true;
                    }
                }
                invalidate();
                return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if(offset>0){
//                currentLine= (int) (currentLine-(Math.ceil(Math.abs(offset))/getResources().getDimensionPixelSize(R.dimen.dp_30)));
//            }else if(offset<0){
//                currentLine= (int) (currentLine+(Math.floor(Math.abs(offset))/getResources().getDimensionPixelSize(R.dimen.dp_30)));
//            }
//            if(currentLine<dataBean.size()){
//                invalidate();
//                return true;
//            }else{
                return  false;
//            }
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }
    };

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这里获取的是播放的进度
            progree=intent.getIntExtra("pro",0);
            if(IsDrawLine=true){
                if(progree-oldProgress>15){
                    IsDrawLine=false;
                }
            }else{
                oldProgress=progree;
            }
            if(currentLine==0){
                currentLine=findShowLine(progree);
                handler.sendEmptyMessage(11);
            }else{
                if(currentLine==dataBean.size()-1){
                    handler.sendEmptyMessage(10);
                }else{
                    if(progree>=Double.valueOf(dataBean.get(currentLine+1).getTime())){
                        handler.sendEmptyMessage(10);
                    }else{
                        handler.sendEmptyMessage(11);
                    }
                }

            }

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        playController.onDestory();
        progree=0;
        currentLine=0;
        if(myBroadcastReceiver!=null){
            context.unregisterReceiver(myBroadcastReceiver);
        }
    }
}
