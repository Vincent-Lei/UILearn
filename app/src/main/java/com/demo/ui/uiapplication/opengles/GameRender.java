package com.demo.ui.uiapplication.opengles;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.demo.ui.uiapplication.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Vincent.Lei on 2019/1/2.
 * Title：
 * Note：
 */
public class GameRender implements GLSurfaceView.Renderer {
//    private float[] triangleOfTable =
//            {0f, 0f,
//                    9f, 14f,
//                    0f, 14f,
//                    0f, 0f,
//                    9f, 0f,
//                    9f, 14f,
//                    0f,7f,
//                    9f,7f,
//                    4.5f,2f,
//                    4.5f,12f
//            };

//    private float[] triangleOfTable =
//            {-0.5f, -0.5f,
//                    0.5f, 0.5f,
//                    -0.5f, 0.5f,
//                    -0.5f, -0.5f,
//                    0.5f, -0.5f,
//                    0.5f, 0.5f,
//                    -0.5f, 0f,
//                    0.5f, 0f,
//                    0f, -0.25f,
//                    0f, 0.25f
//            };

    private float[] triangleOfTable =
            {
                    0.0f, 0.0f, 0.0f, 1.5f, 1f, 1f, 1f,
                    -0.5f, -0.8f, 0.0f, 1f, 0.5f, 0.5f, 0.5f,
                    0.5f, -0.8f, 0.0f, 1f, 0.5f, 0.5f, 0.5f,
                    0.5f, 0.8f, 0.0f, 2f, 0.5f, 0.5f, 0.5f,
                    -0.5f, 0.8f, 0.0f, 2f, 0.5f, 0.5f, 0.5f,
                    -0.5f, -0.8f, 0.0f, 1f, 0.5f, 0.5f, 0.5f,

                    -0.5f, 0f, 0f, 1.5f, 1.0f, 0f, 0f,
                    0.5f, 0f, 0f, 1.5f, 1.0f, 0f, 0f,
                    0f, -0.25f, 0f, 1.25f, 0f, 0f, 1f,
                    0f, 0.25f, 0f, 1.75f, 1f, 0f, 0f
            };

    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;
    private int program;
    private Context mContext;
    //    private int uColorLocation;
    private int aPositionLocation;
    private int aColorLocation;
    private int uMatrixLocation;
    private float aspect;
    private float[] uMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private IFrameAvailableListener frameAvailableListener;
    private static final int POSITION_COMPONENT_COUNT = 4;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public GameRender(Context context) {
        this.mContext = context;
        vertexData = ByteBuffer.allocateDirect(triangleOfTable.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(triangleOfTable);
    }

    public void setFrameAvailableListener(IFrameAvailableListener frameAvailableListener) {
        this.frameAvailableListener = frameAvailableListener;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        program = GLProgramCreater.createProgram(GLProgramCreater.readRawTxt(mContext, R.raw.game_vertex_shadler_1), GLProgramCreater.readRawTxt(mContext, R.raw.game_fragment_shadler_1));
//        uColorLocation = GLES20.glGetUniformLocation(program, "u_Color");
        aPositionLocation = GLES20.glGetAttribLocation(program, "a_Position");
        aColorLocation = GLES20.glGetAttribLocation(program, "a_Color");
        uMatrixLocation = GLES20.glGetUniformLocation(program, "uMatrix");
        GLES20.glUseProgram(program);

        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        vertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);

        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;
        aspect = width * 1.0f / height;
        float ratio;
        if (width > height) {
            ratio = width * 1.0f / height;
            Matrix.orthoM(uMatrix, 0, -ratio, ratio, -1f, 1f, -1f, 1f);
        } else {
            ratio = height * 1.0f / width;
            Matrix.orthoM(uMatrix, 0, -1f, 1f, -ratio, ratio, -1f, 1f);
        }
        perspectiveM(uMatrix, 45, width * 1.0f / height, 1f, 10f);

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2f);
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, uMatrix, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);


//        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

//        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

//        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

//        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }

    public static void perspectiveM(float[] m, float fovy, float aspect, float zNear, float zFar) {
        float angle = (float) (fovy * Math.PI / 180.0f);
        float a = (float) (1.0 / Math.tan(angle / 2.0));
        m[0] = a / aspect;
        m[1] = 0.0f;
        m[2] = 0.0f;
        m[3] = 0.0f;

        m[4] = 0.0f;
        m[5] = a;
        m[6] = 0.0f;
        m[7] = 0.0f;

        m[8] = 0.0f;
        m[9] = 0.0f;
        m[10] = -((zFar + zNear) / (zFar - zNear));
        m[11] = -1.0f;

        m[12] = 0.0f;
        m[13] = 0.0f;
        m[14] = -((2 * zFar * zNear) / (zFar - zNear));
        m[15] = 0.0f;
    }
}
