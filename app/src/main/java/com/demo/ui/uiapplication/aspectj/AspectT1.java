package com.demo.ui.uiapplication.aspectj;

import com.demo.ui.uiapplication.LogUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Vincent.Lei on 2018/10/12.
 * Title：
 * Note：
 */
@Aspect
public class AspectT1 {
    @Before("execution(* android.app.Activity.on**(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
        LogUtil.d(joinPoint.toString());
        LogUtil.d(joinPoint.getSignature().toString());
    }

    @After("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onClickHook(JoinPoint joinPoint) throws Throwable {
        LogUtil.d(joinPoint.toString());
        LogUtil.d(joinPoint.getSignature().toString());
        Object[] objects = joinPoint.getArgs();
        if (objects != null && objects.length > 0) {
            LogUtil.d(objects[0].getClass().getName());
        }
    }

    @Around("execution(* test**(..))")
    public void onAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogUtil.d(proceedingJoinPoint.getSignature().toString());
        proceedingJoinPoint.proceed();
        LogUtil.d("around finish");
    }

    @Pointcut("execution(@com.demo.ui.uiapplication.aspectj.DebugLog * **(..))")
    public void debugLogPointCut() throws Throwable {
    }

    @Around("debugLogPointCut()")
    public void dealLogPointCut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogUtil.d("dealLogPointCut = " + proceedingJoinPoint.getSignature().toString());
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        DebugLog debugLog = methodSignature.getMethod().getAnnotation(DebugLog.class);
        LogUtil.d("TAG = " + debugLog.tag());
        proceedingJoinPoint.proceed();
    }

    @Around("call(* printMsg(..))")
    public void testCall(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogUtil.d("testCall");
        LogUtil.d("dealLogPointCut = " + proceedingJoinPoint.getSignature().toString());
        proceedingJoinPoint.proceed();
    }

    @Pointcut("withincode(void com.demo.ui.uiapplication.aspectj.AspectJTest.method1(..))")
    public void methodHook1() {
    }

    @Pointcut("call(void com.demo.ui.uiapplication.aspectj.AspectJTest.method3(..))")
    public void methodHook2() {
    }

    @Pointcut("methodHook1() && methodHook2()")
    public void methodHook3() {
    }

    @Before("methodHook3()")
    public void methodHook4() {
        LogUtil.d("-----------method1 && method3---------");
    }
}
