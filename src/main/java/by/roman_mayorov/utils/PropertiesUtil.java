package by.roman_mayorov.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static void loadProperties(){
        try(InputStream inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("app.properties")){
            PROPERTIES.load(inputStream);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    private PropertiesUtil(){}
}
