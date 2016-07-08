package com.esgi.teamst.smartlight.listeners;

import com.esgi.teamst.smartlight.views.CustomScrollView;

/**
 * Created by sylvainvincent on 06/07/16.
 */
public interface ScrollViewListener {

    void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy);

}
