package com.demo.ui.uiapplication.opengles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.demo.ui.uiapplication.LogUtil;
import com.demo.ui.uiapplication.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Vincent.Lei on 2018/12/21.
 * Title：
 * Note：
 */
public class TextureRender implements GLSurfaceView.Renderer {

    /**
     * V1            V3                                       V1                  V3
     * 0.0f,0.0f    1.0f,0.0f                                 -1.0f,1.0f        1.0f,1.0f
     * <p>
     * V2             V4                                       V2                V4
     * 0.0f,1.0f     1.0f,1.0f                                -1.0f,-1.0f       1.0f,-1.0f
     */


    private final float[] vertexData = {
            //V2 V4 V1 V3
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f

    };
    private final float[] textureData = {
            //原图对齐
//            0f, 1f,
//            1f, 1f,
//            0f, 0f,
//            1f, 0f

            0f, 2f,
            2f, 2f,
            0f, 0f,
            2f, 0f

            //镜像对齐
//            1.0f,1.0f,
//            0.0f,1.0f,
//            1.0f, 0.0f,
//            0.0f, 0.0f
            //垂直翻转
//            0.0f,0.0f,
//            1.0f,0.0f,
//            0.0f,1.0f,
//            1.0f,1.0f


    };
    private Context context;
    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private int program;
    private int avPosition;
    private int afPosition;
    private int textureId;
    private Timer timer;
    private int[] mTextureIds = {R.mipmap.ic_launcher, R.mipmap.qqchat1, R.mipmap.qqchat2, R.mipmap.wechat1, R.mipmap.weixin, R.mipmap.image_open};
    private int mPosition;
    private Bitmap mBitmap;

    public TextureRender(Context context) {
        this.context = context;

        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

        textureBuffer = ByteBuffer.allocateDirect(textureData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(textureData);
        textureBuffer.position(0);
    }

    void stopTimerBitmap() {
        if (timer != null)
            timer.cancel();
    }

    void timerBitmap(final IFrameAvailableListener frameAvailableListener) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mPosition >= mTextureIds.length)
                    mPosition = 0;
                setBitmapRes(mTextureIds[mPosition]);
                mPosition++;
                if (frameAvailableListener != null)
                    frameAvailableListener.onFrameAvailable();
            }
        }, 100, 3000);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexSource = GLProgramCreater.readRawTxt(context, R.raw.vertex_shader);
        String fragmentSource = GLProgramCreater.readRawTxt(context, R.raw.fragment_shader);
        program = GLProgramCreater.createProgram(vertexSource, fragmentSource);
        if (program > 0) {
            avPosition = GLES20.glGetAttribLocation(program, "av_Position");
            afPosition = GLES20.glGetAttribLocation(program, "af_Position");
            //创建和绑定纹理
            int[] textureIds = new int[1];
            GLES20.glGenTextures(1, textureIds, 0);
            if (textureIds[0] == 0) {
                return;
            }
            textureId = textureIds[0];
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
            //设置环绕和过滤方式
            //环绕（超出纹理坐标范围）：（s==x t==y GL_REPEAT 重复）
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
            //过滤（纹理像素映射到坐标点）：（缩小、放大：GL_LINEAR线性）
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        }
    }

    private void setBitmapRes(int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        if (bitmap == null) {
            return;
        }
        Bitmap temp = mBitmap;
        mBitmap = bitmap;
        if (temp != null)
            temp.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//        setBitmapRes(mTextureIds[mPosition]);

        GLES20.glUseProgram(program);
        GLES20.glEnableVertexAttribArray(avPosition);
        GLES20.glVertexAttribPointer(avPosition, 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);

        GLES20.glEnableVertexAttribArray(afPosition);
        GLES20.glVertexAttribPointer(afPosition, 2, GLES20.GL_FLOAT, false, 8, textureBuffer);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        if (mBitmap != null && !mBitmap.isRecycled())
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
