package Test.GPU;

/**
 * Created by Robert Ostaszewski on 08.01.2017.
 */
public class Wall {
    public float[] getRightTop() {
        return rightTop;
    }

    float[] rightTop = new float[3];

    public float[] getLeftTop() {
        return leftTop;
    }

    float[] leftTop = new float[3];

    public float[] getRightBottom() {
        return rightBottom;
    }

    float[] rightBottom = new float[3];

    public float[] getLeftBottom() {
        return leftBottom;
    }

    float[] leftBottom = new float[3];

    public float[] getColor() {
        return color;
    }

    float[] color = new float[3];

    Wall (float[] rightTop, float[] leftTop, float[] rightBottom, float[] leftBottom, float[] color){
        this.rightTop = rightTop;
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
        this.leftBottom = leftBottom;
        this.color = color;
    }


}
