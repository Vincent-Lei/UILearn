package com.demo.ui.uiapplication.xlistviewnestscroll.view;



public interface IScrollRefreshListener {
    void onRefresh();
    void onScroll(int currentY, int maxY, int moveY);
    void onScrollableStateChange(int state);
}
