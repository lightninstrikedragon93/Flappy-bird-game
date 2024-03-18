package flappy.graphics;

import flappy.math.Matrix4f;
import flappy.math.Vector3f;
import flappy.utils.ShaderUtils;

import static org.lwjgl.BufferUtils.createFloatBuffer;
import static org.lwjgl.opengl.GL20.*;
import java.util.HashMap;
import java.util.Map;

public class Shader {
    private final int ID;
    public static final int VERTEX = 0;
    public static final int TEXTURECOORD = 1;
    public static Shader BACKGROUND;
    public static Shader BIRD;
    public static Shader PIPE;
    private boolean enabled = false;
    private Map<String, Integer> locationCache = new HashMap<String, Integer>();
    public static void loadAll(){
        BACKGROUND = new Shader("shaders\\background.vert", "shaders\\background.frag");
        BIRD = new Shader("shaders\\bird.vert", "shaders\\bird.frag");
        PIPE = new Shader("shaders\\pipe.vert", "shaders\\pipe.frag");
    }
    public Shader (String vertex, String fragment){
        ID = ShaderUtils.load(vertex, fragment);
    }

    public int getUniform(String name){
        if(locationCache.containsKey(name))
        {
            return locationCache.get(name);
        }

        int result = glGetUniformLocation(ID, name);
        if(result  == -1)
            System.err.println("could not find uniform " + name);
        else
            locationCache.put(name, result);
        return result;
    }
    public void setUniform1i(String name, int value){
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }
    public void setUniform1f(String name, float value){
        glUniform1f(getUniform(name), value);
    }
    public void setUniform2f(String name, float x, float y){
        glUniform2f(getUniform(name), x, y);
    }
    public void setUniform2f(String name, Vector3f vector){
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }
    public void setUniformMat4f(String name, Matrix4f matrix)
    {
        if(!enabled) enable();
        glUniformMatrix4fv(getUniform(name),false, createFloatBuffer(16).put(matrix.toFloatBuffer()).flip());
    }
    public void enable(){
        glUseProgram(ID);
        enabled = true;
    }

    public void disable(){
        glUseProgram(0);
        enabled = false;
    }
}
