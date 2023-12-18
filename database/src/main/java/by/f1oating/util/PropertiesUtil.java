package by.f1oating.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private final static Properties PROPERTIES = new Properties();

    static{
        loadProperties();
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getByKey(String key){
        return PROPERTIES.getProperty(key);
    }

    private PropertiesUtil() {
    }
}
