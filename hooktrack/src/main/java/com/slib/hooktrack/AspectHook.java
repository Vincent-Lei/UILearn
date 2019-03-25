package com.slib.hooktrack;

import android.view.View;
import android.widget.AdapterView;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by Vincent.Lei on 2019/2/20.
 * Title：
 * Note：
 */
@Aspect
public class AspectHook {

    @After("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onClickHook(JoinPoint joinPoint) throws Throwable {
        MethodTrack.onClick((View) joinPoint.getArgs()[0]);
    }

    @After("execution(void android.support.v4.app.Fragment.onResume()) && @annotation(com.slib.hooktrack.IgnoreSuperMethod)")
    public void onFragmentResumeHook(JoinPoint joinPoint) throws Throwable {
        TrackLog.d("onFragmenResumeHook");
        MethodTrack.onFragmentResume(joinPoint.getThis());
    }

    @After("execution(void android.support.v4.app.Fragment.onPause())&& @annotation(com.slib.hooktrack.IgnoreSuperMethod)")
    public void onFragmentPauseHook(JoinPoint joinPoint) throws Throwable {
        TrackLog.d("onFragmentPauseHook");
        MethodTrack.onFragmentPause(joinPoint.getThis());
    }

    @After("execution(void android.support.v4.app.Fragment.onDestroy())&& @annotation(com.slib.hooktrack.IgnoreSuperMethod)")
    public void onFragmentDestroyHook(JoinPoint joinPoint) throws Throwable {
        TrackLog.d("onFragmentDestroyHook");
        MethodTrack.onFragmentDestroy(joinPoint.getThis());
    }

    @After("execution(void android.support.v4.app.Fragment.onHiddenChanged(boolean))&& @annotation(com.slib.hooktrack.IgnoreSuperMethod)")
    public void onFragmentHideChangeHook(JoinPoint joinPoint) throws Throwable {
        TrackLog.d("onFragmentHideChangeHook :" + ((Boolean) joinPoint.getArgs()[0]));
        MethodTrack.onFragmentHiddenChanged(joinPoint.getThis(), (Boolean) joinPoint.getArgs()[0]);
    }

    @After("execution(void android.widget.AdapterView.OnItemClickListener.onItemClick(..))")
    public void onAdapterViewItemClickHook(JoinPoint joinPoint) throws Throwable {
        TrackLog.d("onAdapterViewItemClickHook");
        Object[] args = joinPoint.getArgs();
        MethodTrack.onItemClick(joinPoint.getThis(), (AdapterView) args[0], (View) args[1], (int) args[2],(long) args[3]);
    }
}
