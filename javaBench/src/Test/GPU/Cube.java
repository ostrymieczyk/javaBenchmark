package Test.GPU;

/**
 * Created by Robert Ostaszewski on 08.01.2017.
 */
public class Cube {
    private Wall[] walls = new Wall[6];

    public Cube (float[][][] points, float[][] color){
        for (int i = 0; i<6; i++){
            float[] rightTop = points[i][0];
            float[] leftTop = points[i][1];
            float[] rightBottom = points[i][2];
            float[] leftBottom = points[i][3];
            walls[i] = new Wall(rightTop, leftTop, rightBottom, leftBottom, color[i]);
        }
    }

    public Wall[] getWalls(){
        return walls;
    }
}
