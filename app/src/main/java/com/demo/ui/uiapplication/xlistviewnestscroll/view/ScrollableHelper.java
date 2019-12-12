/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2015 cpoopc
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package com.demo.ui.uiapplication.xlistviewnestscroll.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ScrollView;


public class ScrollableHelper {

    private static final String TAG = "ScrollableHelper";

    private ScrollableContainer mCurrentScrollableCainer;

    /**
     * a viewgroup whitch contains ScrollView/ListView/RecycelerView..
     */
    public interface ScrollableContainer {
        /**
         * @return ScrollView/ListView/RecycelerView..'s instance
         */
        View getScrollableView();

        IScrollableView getIScrollableView();
    }

    public void setCurrentScrollableContainer(ScrollableContainer scrollableContainer) {
        boolean isOldTop = isTop();
        this.mCurrentScrollableCainer = scrollableContainer;
        if (isOldTop) {
            setViewTop();
        }
    }

    private View getScrollableView() {
        if (mCurrentScrollableCainer == null) {
            return null;
        }
        return mCurrentScrollableCainer.getScrollableView();
    }


    private IScrollableView getIScrollableView() {
        if (mCurrentScrollableCainer == null) {
            return null;
        }
        return mCurrentScrollableCainer.getIScrollableView();
    }

    /**
     * 判断是否滑动到顶部方法,ScrollAbleLayout根据此方法来做一些逻辑判断
     * 目前只实现了AdapterView,ScrollView,RecyclerView
     * 需要支持其他view可以自行补充实现
     *
     * @return
     */
    public boolean isTop() {
        View scrollableView = getScrollableView();
        if (scrollableView == null) {
            return isInterfaceViewTop();
        }
        if (scrollableView instanceof AdapterView) {
            return isAdapterViewTop((AdapterView) scrollableView);
        }
        if (scrollableView instanceof ScrollView) {
            return isScrollViewTop((ScrollView) scrollableView);
        }
        if (scrollableView instanceof RecyclerView) {
//            if (scrollableView instanceof PARSRecyclerView) {
//                return isPARSRecyclerViewTop((PARSRecyclerView) scrollableView);
//            } else {
                return isRecyclerViewTop((RecyclerView) scrollableView);
//            }
        }
        if (scrollableView instanceof WebView) {
            return isWebViewTop((WebView) scrollableView);
        }
        throw new IllegalStateException("scrollableView must be a instance of AdapterView|ScrollView|RecyclerView");
    }

    private boolean isInterfaceViewTop() {
        IScrollableView interfaceView = getIScrollableView();
        if (interfaceView != null) {
            return interfaceView.isTop();
        }
        return true;
    }

    private static boolean isRecyclerViewTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                View childAt = recyclerView.getChildAt(0);
                if (childAt == null || (firstVisibleItemPosition == 0 &&
                        layoutManager.getDecoratedTop(childAt) == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

//    private static boolean isPARSRecyclerViewTop(PARSRecyclerView recyclerView) {
//        if (recyclerView == null) {
//            return false;
//        }
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof LinearLayoutManager) {
//            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//            int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
//            View childAt = recyclerView.getChildAt(0);
//            //当前屏幕所看到的子项个数
//            int visibleItemCount = layoutManager.getChildCount();
//            if (childAt == null || (visibleItemCount > 0 && firstCompletelyVisibleItemPosition == 0)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else if (manager instanceof StaggeredGridLayoutManager) {
//            boolean hasHeaderAndFooter = recyclerView.isHasHeaderAndFooter();
//            int firstChild = 0;
//            if (hasHeaderAndFooter) {
//                //如果有头部
//                firstChild = 2;
//            }
//            StaggeredGridLayoutManager layoutManagerStag = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
//            int[] firstCompletelyVisibleItemPositions = layoutManagerStag.findFirstCompletelyVisibleItemPositions(new int[6]);
//            View childAt = recyclerView.getChildAt(0);
//            int visibleItemCount = layoutManagerStag.getChildCount();
//            //因为这里有下拉刷新的顶部的两个item，所以第一个可见的需要判断是否是2
//            if (childAt == null || (visibleItemCount > 0 &&
//                    (firstCompletelyVisibleItemPositions[0] == firstChild || (firstCompletelyVisibleItemPositions[0] == firstChild + 1 && firstCompletelyVisibleItemPositions[1] == firstChild)))) {
//                return true;
//            } else {
//                if ((firstCompletelyVisibleItemPositions[0] == -1 && firstCompletelyVisibleItemPositions[1] == -1)) {
//                    if (recyclerView.getAdapter() != null) {
//                        int childCount = recyclerView.getAdapter().getItemCount();
//                        if (hasHeaderAndFooter && childCount == 4) {//页面数量为空时
//                            return true;
//                        } else if (!hasHeaderAndFooter && childCount == 0) {//页面数量为空时
//                            return true;
//                        }
//                    }
//                }
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }

    private static boolean isAdapterViewTop(AdapterView adapterView) {
        if (adapterView != null) {
            int firstVisiblePosition = adapterView.getFirstVisiblePosition();
            View childAt = adapterView.getChildAt(0);
            if (childAt == null || (firstVisiblePosition == 0 && childAt.getTop() == 0)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isScrollViewTop(ScrollView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    private static boolean isWebViewTop(WebView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    @SuppressLint("NewApi")
    public void smoothScrollBy(int velocityY, int distance, int duration) {
        View scrollableView = getScrollableView();
        if (scrollableView == null) {
            IScrollableView interfaceView = getIScrollableView();
            if (interfaceView != null) {
                interfaceView.smoothScrollBy(velocityY, distance, duration);
            }
            return;
        }
        if (scrollableView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) scrollableView;
            if (Build.VERSION.SDK_INT >= 21) {
                absListView.fling(velocityY);
            } else {
                absListView.smoothScrollBy(distance, duration);
            }
        } else if (scrollableView instanceof ScrollView) {
            ((ScrollView) scrollableView).fling(velocityY);
        } else if (scrollableView instanceof RecyclerView) {
            ((RecyclerView) scrollableView).fling(0, velocityY);
        } else if (scrollableView instanceof WebView) {
            ((WebView) scrollableView).flingScroll(0, velocityY);
        }
    }

    public void setViewTop() {
        try {
            View scrollableView = getScrollableView();
            if (scrollableView == null) {
                IScrollableView interfaceView = getIScrollableView();
                if (interfaceView != null) {
                    interfaceView.setViewTop();
                }
                return;
            }
            scrollableView.scrollTo(0, 0);
        } catch (Exception e) {
            Log.i("ScrollableHelper", "setViewTop:" + e.getMessage());
        }
    }
}
