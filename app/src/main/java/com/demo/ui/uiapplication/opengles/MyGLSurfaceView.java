package com.demo.ui.uiapplication.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Vincent.Lei on 2018/12/6.
 * Title：
 * Note：
 */
public class MyGLSurfaceView extends GLSurfaceView implements IFrameAvailableListener {
    GLSurfaceView.Renderer renderer;


    public MyGLSurfaceView(Context context) {
        this(context, null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);//设置opengl es版本为2.0
        // 为glsurfaceview设置render
//        renderer = new NormalRender();
//        renderer = new TextureRender(context);
        renderer = new GameRender(context);
        setRenderer(renderer);//为glsurfaceview设置render

        if(renderer instanceof GameRender ){
            ((GameRender) renderer).setFrameAvailableListener(this);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        }


        if (renderer instanceof TextureRender) {
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            ((TextureRender) renderer).timerBitmap(this);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (renderer != null && renderer instanceof TextureRender)
            ((TextureRender) renderer).stopTimerBitmap();
    }

    @Override
    public void onFrameAvailable() {
        requestRender();
    }
}
