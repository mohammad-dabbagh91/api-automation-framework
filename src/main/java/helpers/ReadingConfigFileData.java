package helpers;

import com.sun.istack.NotNull;

import java.io.*;
import java.util.Properties;

import static helpers.OSNames.*;

public class ReadingConfigFileData {

    private ReadingConfigFileData() {

    }


    // We usually do not use this function since it for new config file and we wanna pass its path directly
    public static String readConfig(String key, String filePath) {
        File file = new File(filePath);
        return getPropValue(key, file);
    }

    // We usually use this function since its for the default confic file so no need to pass the file path we just pass the key
    public static String readConfig(String key) {
        File file = new File("src/main/resources/config.properties");
        return getPropValue(key, file);
    }

    @NotNull
    private static String getPropValue(String key, File file) {

        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            properties.load(bufferedReader);

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }


}
