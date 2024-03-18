package flappy.input;
import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Input extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[9999];
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;

    }
    public static boolean isKeyDown(int key) {
        return keys[key];
    }
}
