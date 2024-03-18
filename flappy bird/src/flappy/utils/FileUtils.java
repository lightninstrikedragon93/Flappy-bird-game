package flappy.utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtils {
    private FileUtils(){}

    public static String loadAsString(String filename){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String buffer = "";
            while((buffer = reader.readLine())!= null){
                result.append(buffer + '\n');
            }
            reader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
