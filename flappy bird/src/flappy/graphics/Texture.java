package flappy.graphics;

import flappy.utils.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.BufferUtils.createIntBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL11C.glGenTextures;

public class Texture {

    private int width, height;
    private int texture;
    public Texture(String path){
        texture = load(path);
    }
    public int load(String path){
        int [] pixels = null;
        try{
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        }catch (IOException e){
            e.printStackTrace();

        }
        int[] data = new int [width * height];
        for (int i = 0; i < width * height; i++){
            int a = (pixels[i] & 0xFF000000) >> 24;
            int r = (pixels[i] & 0xFF0000) >> 16;
            int g = (pixels[i] & 0xFF00) >> 8;
            int b = (pixels[i] & 0xFF);

            data[i] = a << 24 | b << 16 | g << 8 | r;

        }
        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, createIntBuffer(width * height).put(data).flip());
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }
    public void bind(){
        glBindTexture(GL_TEXTURE_2D, texture);
    }
    public void unbind(){
        glBindTexture(GL_TEXTURE, 0);
    }

}
