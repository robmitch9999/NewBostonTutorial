package com.thelastrobmitch.newbostontutorial;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Robert on 17/10/2017.
 */

public class GLRendererEx implements GLSurfaceView.Renderer {

    private GLCube cube;
    private GLTriangle tri;
    Boolean isTriangle = false;

    public GLRendererEx() {
        cube = new GLCube();
        tri = new GLTriangle();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST); // boosts performance
        //performance boosts come at a cost of quality, apparently!
        gl.glClearColor(.8f, 0f, .2f, 1); // red, green, blue, transparency
        gl.glClearDepthf(1f);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float ratio = (float) width/height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 30); // includes limits to visibility
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glDisable(GL10.GL_DITHER); // boosts performance
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, -3, 0, 0, 0, 0, 2, 0); // this is controlling where the "camera" is
        // and where it is looking
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = .090f * ((int) time);

        if (isTriangle) {
            gl.glRotatef(angle, 1, 1, 0);
            tri.draw(gl);

        } else {
            gl.glRotatef(angle, 0, 1, 0);
            cube.draw(gl);
        }



    }
}
