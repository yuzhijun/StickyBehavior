package com.lenovohit.stickybehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yuzhijun on 2017/9/21.
 */

public class BottomBehavior extends CoordinatorLayout.Behavior {
    private boolean isInit = true;
    private int childTop;

    public BottomBehavior() {
    }

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        parent.onLayoutChild( child, layoutDirection);
        View stickyLayout = getStickyLayout(parent);
        View headerView = getHeaderView(parent);
        child.offsetTopAndBottom(headerView.getBottom() + stickyLayout.getHeight());
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
            int dy = dependency.getTop() - dependentViewTop;

            if (!isInit){
                child.offsetTopAndBottom(dy);
            }
           isInit = false;
        }

        dependentViewTop = dependency.getTop();
        childTop = child.getTop();
        return true;
    }

    private View getStickyLayout(CoordinatorLayout coordinatorLayout){
        View stickyLayout = coordinatorLayout.getChildAt(1);
        return stickyLayout;
    }

    private View getHeaderView(CoordinatorLayout coordinatorLayout){
        View HeaderView = coordinatorLayout.getChildAt(0);
        return HeaderView;
    }
}
