package flappy.level;

import flappy.graphics.Shader;
import flappy.graphics.Texture;
import flappy.graphics.VertexArray;
import flappy.math.Matrix4f;
import flappy.math.Vector3f;

import java.util.Random;

public class Level {
    private VertexArray background;
    private Texture backgroundTexture;
    private int map = 0; //advance the bg
    private int xScroll = 0;
    private Bird bird;
    private Pipe[] pipes = new Pipe[5 * 2];
    private Random random = new Random();
    private float OFFSET = 5.0f;
    private int index = 0;
    private boolean control = true;
    public Level(){
        float [] vertices = new float[]{
                -10.0f, -10.0f * 9.0f/16.0f, 0.0f,
                -10.0f, 10.0f * 9.0f/16.0f, 0.0f,
                0.0f, 10.0f * 9.0f/16.0f, 0.0f,
                0.0f, -10.0f * 9.0f/16.0f, 0.0f
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

        background = new VertexArray(vertices, indices, texture_points);
        backgroundTexture = new Texture("res\\background.png");
        bird = new Bird();

        createPipes();

    }
    public void createPipes(){
        Pipe.create();
        for(int i = 0; i <5 * 2; i += 2){
            pipes[i] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.5f);
            pipes[i + 1] = new Pipe(pipes[i].getX(), pipes[i].getY() - 11.5f);
            index += 2;
        }

    }
    public void update(){
        if(control) {
            xScroll--;
            if (xScroll % 335 == 0) {
                map++;
            }
            if (-xScroll > 250 && -xScroll % 120 == 0)
                updatePipes();
        }
        bird.update();
        if(control && collision()){
            bird.fall();
            control = false;

        }
    }
    private void updatePipes(){
        pipes[index % 10] = new Pipe(OFFSET + index * 3.0f, random.nextFloat() * 4.5f);
        pipes[(index + 1)% 10] = new Pipe(pipes[index % 10].getX(), pipes[index % 10].getY() - 11.5f);
        index += 2;
    }
    private void renderPipes(){
        Shader.PIPE.enable();
        Shader.PIPE.setUniformMat4f("view_matrix", Matrix4f.translate(new Vector3f(xScroll * 0.05f, 0.0f, 0.0f)));
        Pipe.getTexture().bind();
        Pipe.getMesh().bind();
        for(int i = 0; i< 5 * 2; i++)
        {
            Shader.PIPE.setUniformMat4f("model_matrix",pipes[i].getModelMatrix());
            Shader.PIPE.setUniform1i("top", i % 2 == 0 ? 1 : 0);
            Pipe.getMesh().draw();
        }
        Pipe.getMesh().unbind();
        Pipe.getTexture().unbind();

    }

    private boolean collision(){
        for(int i = 0; i < 5 * 2; i++)
        {
            float birdX = -xScroll * 0.05f;
            float birdY = bird.getY();
            float pipeX = pipes[i].getX();
            float pipeY = pipes[i].getY();

            float birdX0 = birdX - bird.getSize()/2.0f;
            float birdX1 = birdX + bird.getSize()/2.0f;
            float birdY0 = birdY - bird.getSize()/2.0f;
            float birdY1 = birdY + bird.getSize()/2.0f;

            float pipeX0 = pipeX;
            float pipeX1 = pipeX + Pipe.getWidth();
            float pipeY0 = pipeY;
            float pipeY1 = pipeY + Pipe.getHeight();
            if(birdX1 >pipeX && birdX0 < pipeX1){
                if(birdY1 > pipeY && birdY0 < pipeY1){
                    return true;
                }
            }
        }
        return false;
    }
    public void render(){
        backgroundTexture.bind();
        Shader.BACKGROUND.enable();
        background.bind();
        for(int i = map; i < map + 3; i++)
        {
            Shader.BACKGROUND.setUniformMat4f("view_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
            background.draw();
        }
        background.render();
        Shader.BACKGROUND.disable();
        backgroundTexture.unbind();

        renderPipes();
        bird.render();
    }
    
}

