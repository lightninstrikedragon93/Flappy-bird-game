package flappy.utils;

import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
    private ShaderUtils(){}
    public static int load (String vertexPath, String fragmentPath)
    {
        String vertex =  FileUtils.loadAsString(vertexPath);
        String fragment =  FileUtils.loadAsString(fragmentPath);
        return create(vertex, fragment);
    }
    public static int create (String vertex, String fragment){
        int program = glCreateProgram();
        int vertexID = glCreateShader(GL_VERTEX_SHADER);
        int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertexID, vertex);
        glShaderSource(fragmentID, fragment);

        glCompileShader(vertexID);
        glCompileShader(fragmentID);

        if(glGetShaderi(vertexID,GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("error shader vertex compile failed");
            System.err.println(glGetShaderInfoLog(vertexID));
            return -1;
        }

        if(glGetShaderi(fragmentID,GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("error shader fragment compile failed");
            System.err.println(glGetShaderInfoLog(fragmentID));
            return -1;
        }
        glAttachShader(program, vertexID);
        glAttachShader(program, fragmentID);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);


        return program;
    }
}
