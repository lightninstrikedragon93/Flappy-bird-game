package flappy.level;

import flappy.graphics.Texture;
import flappy.graphics.VertexArray;
import flappy.math.Matrix4f;
import flappy.math.Vector3f;

public class Pipe {
    private Vector3f position = new Vector3f();
    private static float width = 1.5f;
    private static float height = 8.0f;
    private static Texture pipeTexture;
    private Matrix4f model_matrix;
    private static VertexArray mesh;

    public static void create(){
        float [] vertices = new float[]{
                0.0f, 0.0f, 0.1f,
                0.0f, height, 0.1f,
                width, height, 0.1f,
                width, 0.0f, 0.1f
        };

        byte [] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        float[] texture_points =new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        mesh = new VertexArray(vertices, indices, texture_points);
        pipeTexture = new Texture("res\\tub down.png");
    }
    public Pipe(float x, float y){
        position.x = x;
        position.y = y;
        model_matrix = Matrix4f.translate(position);
    }
    public float getX(){
        return position.x;
    }
    public float getY(){
        return position.y;
    }
    public static VertexArray getMesh(){
        return mesh;
    }
    public static Texture getTexture(){
        return pipeTexture;
    }
    public static float getWidth(){
        return width;
    }
    public static float getHeight(){
        return height;
    }
    public Matrix4f getModelMatrix(){
        return model_matrix;
    }


}
