package flappy.graphics;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArray {
    private int count;
    private int vao, vbo, ibo, tco;
    public VertexArray(float [] vertices, byte[] indices, float[] textureCoordinates){
        count = indices.length;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(16).put(vertices).flip(), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.VERTEX,3, GL_FLOAT, false,0, 0 );
        glEnableVertexAttribArray(Shader.VERTEX);

        tco = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tco);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(16).put(textureCoordinates).flip(), GL_STATIC_DRAW);
        glVertexAttribPointer(Shader.TEXTURECOORD,2, GL_FLOAT, false,0, 0 );
        glEnableVertexAttribArray(Shader.TEXTURECOORD);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(16).put(indices).flip(), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }
    public void bind(){
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
    }
    public void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void draw(){
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);

    }
    public void render(){
        bind();
        draw();
    }
}
