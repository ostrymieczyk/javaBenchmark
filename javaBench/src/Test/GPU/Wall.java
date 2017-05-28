package Test.GPU;

/**
 *
 */
public class Wall {

    /**
     * @return
     */
    public float[] getRightTop() {
        return rightTop;
    }

    /**
     *
     */
    private float[] rightTop = new float[3];

    /**
     * @return
     */
    public float[] getLeftTop() {
        return leftTop;
    }

    /**
     *
     */
    private float[] leftTop = new float[3];

    /**
     * @return
     */
    public float[] getRightBottom() {
        return rightBottom;
    }

    /**
     *
     */
    private float[] rightBottom = new float[3];

    /**
     * @return
     */
    public float[] getLeftBottom() {
        return leftBottom;
    }

    /**
     *
     */
    private float[] leftBottom = new float[3];

    /**
     * @return
     */
    public float[] getColor() {
        return color;
    }

    /**
     *
     */
    private float[] color = new float[3];

    /**
     * @param rightTop
     * @param leftTop
     * @param rightBottom
     * @param leftBottom
     * @param color
     */
    Wall (float[] rightTop, float[] leftTop, float[] rightBottom, float[] leftBottom, float[] color){
        this.rightTop = rightTop;
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
        this.leftBottom = leftBottom;
        this.color = color;
    }


}
