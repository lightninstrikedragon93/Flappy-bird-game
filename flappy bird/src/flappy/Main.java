package flappy;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11C.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import flappy.graphics.Shader;
import flappy.input.Input;
import flappy.level.Level;
import flappy.math.Matrix4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Main implements  Runnable {
    private int width = 1280;
    private int height = 720;
    private Thread thread;
    private boolean running = false;
    private long window;
    private Level level;

    public void start(){
        running = true;
        thread = new Thread(this, "Flappy bird");
        thread.start();
    }

    private void init(){

        if(!glfwInit()){
            return;
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window  = glfwCreateWindow(width, height, "Flappy bird", NULL, NULL);
        if(window == NULL){
            return;
        }

        GLFWVidMode video_Mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (video_Mode.width() - width)/2, (video_Mode.height() - height)/2);

        glfwSetKeyCallback(window, new Input());

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();


        glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // set color
        glEnable(GL_DEPTH_TEST);
        glActiveTexture(GL_TEXTURE1);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //System.out.println("OpenGL" +  glGetString(GL_VERSION));
        Shader.loadAll();
        Matrix4f projection_matrix = Matrix4f.orthographic(-10.0f, 10f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);

        Shader.BACKGROUND.setUniformMat4f("projection_matrix", projection_matrix);
        Shader.BACKGROUND.setUniform1i("tex", 1);

        Shader.BIRD.setUniformMat4f("projection_matrix", projection_matrix);
        Shader.BIRD.setUniform1i("tex", 1);

        Shader.PIPE.setUniformMat4f("projection_matrix", projection_matrix);
        Shader.PIPE.setUniform1i("tex", 1);

        level = new Level();
    }

    public void run(){
        try {
            Thread.sleep(5000); // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        init();
        long lastTime  = System.nanoTime();
        double ns = 1000000000.0/60.0;
        double delta = 0.0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0)
            {
                update();
                updates ++;
                delta --;
            }
            render();
            frames ++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " +frames / 100.0f + " Updates: " + updates);
                updates = 0;
                frames = 0;
            }
            if(glfwWindowShouldClose(window)){
                running = false;
            }
        }
        glfwDestroyWindow(window);
        glfwTerminate();
    }
    private void update(){
        level.update();
        glfwPollEvents();

    }
    private void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        level.render();

        glfwSwapBuffers(window);

    }

    public static void main(String [] args){

        new Main().start();
    }
}
