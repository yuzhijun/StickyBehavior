package com.lenovohit.stickybehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * 中间停靠的栏目
 * Created by yuzhijun on 2017/9/21.
 */
public class StickyBehavior extends CoordinatorLayout.Behavior<View>{
    //初始的视图的偏移也就是最大的偏移量
    int maxOffset = 0;
    //最小的偏移
    int minOffset = 0;

    public StickyBehavior() {
        super();
    }

    public StickyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        View headerView = getHeaderView(parent);
        child.offsetTopAndBottom(headerView.getBottom());
        maxOffset = headerView.getHeight();
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //判断监听的方向
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 ;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        int top = child.getTop();
        if (top >= minOffset){
            consumed[1] = scroll(child, dy, minOffset, maxOffset);
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        int top = child.getTop();
        if (top >= minOffset){
            scroll(child, dyUnconsumed, minOffset, maxOffset);
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        //如果需要回弹则需要在这里写代码
    }

    private View getHeaderView(CoordinatorLayout coordinatorLayout){
        View HeaderView = coordinatorLayout.getChildAt(0);
        return HeaderView;
    }

    private int scroll(View child, int dy, int minOffset, int maxOffset) {
        int top = child.getTop();
        int offset = clamp(top - dy, minOffset, maxOffset) - top;
        child.offsetTopAndBottom(offset);
        return -offset;
    }

    /**
     * 取上下限之间的值
     * */
    private int clamp(int i, int minOffset, int maxOffset) {
        if (i < minOffset) {
            return minOffset;
        } else if (i > maxOffset) {
            return maxOffset;
        } else {
            return i;
        }
    }
}
