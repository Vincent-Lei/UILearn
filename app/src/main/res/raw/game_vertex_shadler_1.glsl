attribute vec4 a_Position;
attribute vec4 a_Color;
varying vec4 v_Color;
uniform mat4 uMatrix;

void main() {
    v_Color = a_Color;
    gl_Position = uMatrix*a_Position;
    gl_PointSize = 10.0;
}
