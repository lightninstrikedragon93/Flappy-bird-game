package flappy.level;

import flappy.graphics.Texture;
import flappy.graphics.VertexArray;
import flappy.input.Input;
import flappy.math.Matrix4f;
import flappy.math.Vector3f;
import flappy.graphics.Shader;
import static org.lwjgl.glfw.GLFW.*;

public class Bird {
    private float SIZE = 1.0f;
    private VertexArray mesh;
    private Texture birdTexture;
    private Vector3f position = new Vector3f();
    private float rotation;
    private float delta = 0.0f;
    public Bird() {
        float [] vertices = new float[]{
                -SIZE/2.6f, -SIZE/2.6f, 0.05f,
                -SIZE/2.6f, SIZE/2.6f, 0.05f,
                SIZE/2.6f, SIZE/2.6f, 0.05f,
                SIZE/2.6f, -SIZE/2.6f, 0.05f
        };

        byte[] indices = new byte[]{
                0, 1, 2,
                2, 3, 0
        };

        float [] texture_points = new float[]{
                0,1,
                0,0,
                1,0,
                1,1
        };
        mesh = new VertexArray(vertices, indices, texture_points);
        birdTexture = new Texture("res\\bird.png");

    }
    public void update(){
       position.y -= delta;
       if(Input.isKeyDown(GLFW_KEY_SPACE)){
           delta = -0.10f;
       }
       else delta+= 0.01f;
       rotation = -delta * 60.0f;

    }
    public void fall(){
        delta = -0.15f;

    }
    public void render(){
        Shader.BIRD.enable();
        Shader.BIRD.setUniformMat4f("model_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rotation)));
        birdTexture.bind();
        mesh.render();
        Shader.BIRD.disable();
    }

    public float getY() {
        return position.y;
    }

    public float getSize() {
        return SIZE;
    }
}
