package com.example.music.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Create By morningsun  on 2020-07-21
 */
public class NestedScrollableHost extends FrameLayout {
    private int touchSlop;
    private float initialX;
    private float initialY;
    private ViewPager2 getParentViewPager() {
        ViewParent viewParent = getParent();
        if (!(getParent() instanceof View)) {
            viewParent = null;
        }

        View v;
        for(v = (View)viewParent; v != null && !(v instanceof ViewPager2); v = (View)viewParent) {
            viewParent = v.getParent();
            if (!(viewParent instanceof View)) {
                viewParent = null;
            }
        }

        View var2 = v;
        if (!(v instanceof ViewPager2)) {
            var2 = null;
        }

        return (ViewPager2)var2;
    }

    private View getChild() {
        return this.getChildCount() > 0 ? this.getChildAt(0) : null;
    }

    private boolean canChildScroll(int orientation, float delta) {
        int direction = -((int)Math.signum(delta));
        boolean var6=false;
        switch(orientation) {
            case 0:
                if(getChild()!=null){
                    var6= getChild().canScrollHorizontally(direction);
                }
                break;
            case 1:
                if(getChild()!=null){
                    var6= getChild().canScrollVertically(direction);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }

        return var6;
    }

    public boolean onInterceptTouchEvent(@NotNull MotionEvent e) {
        handleInterceptTouchEvent(e);
        return super.onInterceptTouchEvent(e);
    }

    private void handleInterceptTouchEvent(MotionEvent e) {
        ViewPager2 viewPager2 = this.getParentViewPager();
        if (viewPager2 != null) {
            int orientation = viewPager2.getOrientation();
            if (canChildScroll(orientation, -1.0F) || this.canChildScroll(orientation, 1.0F)) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    this.initialX = e.getX();
                    this.initialY = e.getY();
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
                    float dx = e.getX() - this.initialX;
                    float dy = e.getY() - this.initialY;
                    boolean isVpHorizontal = orientation == 0;
                    float scaledDx = Math.abs(dx) * (isVpHorizontal ? 0.5F : 1.0F);
                    float scaledDy = Math.abs(dy) * (isVpHorizontal ? 1.0F : 0.5F);
                    if (scaledDx > (float)touchSlop || scaledDy > (float)touchSlop) {
                        if (isVpHorizontal == scaledDy > scaledDx) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else if (this.canChildScroll(orientation, isVpHorizontal ? dx : dy)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }

            }
        }
    }

    public NestedScrollableHost(@NotNull Context context) {
        super(context);
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public NestedScrollableHost(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }
}
