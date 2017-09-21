package com.lenovohit.stickybehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 顶部栏behavior
 * Created by yuzhijun on 2017/9/21.
 */
public class HeaderBehavior extends CoordinatorLayout.Behavior<View> {
    //初始化偏移
    private int initOffset = 0;
    private int childTop;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        if (childTop < 0){
            child.offsetTopAndBottom(childTop);
        }else{
            child.offsetTopAndBottom(initOffset);
        }
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof TabLayout;
    }

    private int dependentViewTop = -1;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (dependentViewTop != -1){
            int top = child.getTop();
            int dy = dependency.getTop() - dependentViewTop;

            if (dy > -top){
                dy = -top;
            }

            child.offsetTopAndBottom(dy);
        }

        dependentViewTop = dependency.getTop();
        childTop = child.getTop();
        return true;
    }
}
