package com.esgi.teamst.smartlight.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.esgi.teamst.smartlight.listeners.ScrollViewListener;

/**
 * Created by sylvainvincent on 06/07/16.
 */
public class CustomScrollView extends ScrollView {

    private ScrollViewListener mScrollViewListener;


    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        mScrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mScrollViewListener != null){
            mScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}
