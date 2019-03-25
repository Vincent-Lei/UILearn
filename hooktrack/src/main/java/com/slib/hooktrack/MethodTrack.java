package com.slib.hooktrack;

import android.content.DialogInterface;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;

import com.slib.utils.string.MD5;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Vincent.Lei on 2019/2/21.
 * Title：
 * Note：
 */
public class MethodTrack {
    private static HashMap<Integer, Pair<Integer, String>> mAliveFragMap = new HashMap<>();
    private static HashMap<Integer, WeakReference<Object>> mAliveWeakFragCache = new HashMap<>();

    static HashMap<Integer, Pair<Integer, String>> getAliveFragMap() {
        return mAliveFragMap;
    }

    static void onClick(View view) {
        if (view == null)
            return;
        String path = PathUtil.getViewPath(view);
        TrackLog.d(path);
        String eventId = MD5.getStringMD5(path);
        TrackLog.d(eventId);
    }

    static void onClick(Object object, DialogInterface dialogInterface, int which) {
    }

    static void onItemClick(Object object, AdapterView parent, View view, int position, long id) {
        if (view == null)
            return;
        String path = PathUtil.getViewPath(view);
        TrackLog.d(path);
        String eventId = MD5.getStringMD5(path);
        TrackLog.d(eventId);
    }

    static void onItemSelected(Object object, AdapterView parent, View view, int position, long id) {
        onItemClick(object, parent, view, position, id);
    }

    static void onGroupClick(Object thisObject, ExpandableListView parent, View v, int groupPosition, long id) {
    }

    static void onChildClick(Object thisObject, ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

    }

    static void onStopTrackingTouch(Object thisObj, SeekBar seekBar) {

    }

    static void onRatingChanged(Object thisObj, RatingBar ratingBar, float rating, boolean fromUser) {

    }

    static void onCheckedChanged(Object object, RadioGroup radioGroup, int checkedId) {
    }

    static void onCheckedChanged(Object object, CompoundButton button, boolean isChecked) {
    }

    static void onFragmentResume(Object obj) {
        addAliveFragment(obj);
    }

    static void onFragmentPause(Object obj) {
        removeAliveFragment(obj);
    }

    static void onFragmentDestroy(Object obj) {
    }

    static boolean checkFragment(android.support.v4.app.Fragment paramFragment) {
        return true;
    }

    static boolean checkFragment(android.app.Fragment paramFragment) {
        return true;
    }

    static void setFragmentUserVisibleHint(Object obj, boolean isUserVisibleHint) {
        if (isUserVisibleHint)
            addAliveFragment(obj);
        else
            removeAliveFragment(obj);
    }

    static void onFragmentHiddenChanged(Object fragment, boolean hidden) {
        setFragmentUserVisibleHint(fragment, !hidden);
    }

    static void addAliveFragment(Object obj) {
        View view = null;
        if (obj instanceof android.app.Fragment) {
            view = ((android.app.Fragment) obj).getView();
        } else if (obj instanceof android.support.v4.app.Fragment) {
            view = ((android.support.v4.app.Fragment) obj).getView();
        }
        if (null != view) {
            int viewCode = view.hashCode();
            mAliveFragMap.put(obj.hashCode(), new Pair<>(viewCode, obj.getClass().getName()));
            mAliveWeakFragCache.put(viewCode, new WeakReference<Object>(obj));
        }
    }

    static void removeAliveFragment(Object obj) {
        if (null != obj) {
            mAliveFragMap.remove(obj.hashCode());
            View view = null;
            if (obj instanceof android.app.Fragment) {
                view = ((android.app.Fragment) obj).getView();
            } else if (obj instanceof android.support.v4.app.Fragment) {
                view = ((android.support.v4.app.Fragment) obj).getView();
            }
            if (null != view)
                mAliveWeakFragCache.remove(view.hashCode());
        }
    }

    static String getFragmentName(View targetView) {
        if (targetView == null)
            return null;
        Iterator<Map.Entry<Integer, Pair<Integer, String>>> iterator = mAliveFragMap.entrySet().iterator();
        Pair<Integer, String> pair;
        int viewCode;
        ViewParent parent;
        while (iterator.hasNext()) {
            pair = iterator.next().getValue();
            viewCode = pair.first;
            if (viewCode == targetView.hashCode())
                return pair.second;
            parent = targetView.getParent();
            while (parent != null && parent instanceof ViewGroup) {
                if (viewCode == parent.hashCode())
                    return pair.second;
                parent = parent.getParent();
            }
        }
        return null;
    }

    static Object getFragmentCache(View targetView) {
        if (targetView == null)
            return null;
        int viewCode = targetView.hashCode();
        if (mAliveWeakFragCache.containsKey(viewCode))
            return mAliveWeakFragCache.get(viewCode).get();
        WeakReference<Object> ref;
        ViewParent parent = targetView.getParent();
        while (parent != null && parent instanceof ViewGroup) {
            ref = mAliveWeakFragCache.get(parent.hashCode());
            if (ref != null)
                return ref.get();
            parent = parent.getParent();
        }
        return null;
    }

}
