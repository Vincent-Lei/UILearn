package com.demo.ui.uiapplication.xlistviewnestscroll.view;



public interface IScrollableView {
    boolean isTop();

    void smoothScrollBy(int velocityY, int distance, int duration);

    void setViewTop();
}
