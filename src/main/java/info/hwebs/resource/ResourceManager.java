package info.hwebs.resource;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;

// TODO interface for manager?
public final class ResourceManager {

    // TODO generalize
    private static final Map<String, Properties> configStore = new HashMap<>();

    private static final Map<String, Image> imageStore = new HashMap<>();

    public static void registerConfig(String name, String filename) {
        assert(!configStore.containsKey(name));
     
        try (InputStream input = ResourceManager.class.getResourceAsStream(filename)) {
 
            Properties properties = new Properties();
            properties.load(input);
            configStore.put(name, properties);        

        } catch (IOException | NullPointerException e) {
            // TODO handle
        }   
    }

    // TODO throw exception when not in store
    public static Properties getConfig(String name) {
        assert(configStore.containsKey(name));
        return configStore.get(name);
    }

    public static void registerImage(String name, String filename) {
        assert(!configStore.containsKey(name));

        try (InputStream input = ResourceManager.class.getResourceAsStream(filename)) {
            imageStore.put(name, new Image(input));
        } catch (IOException e) {
            // TODO handle
        }
    }

    public static Image getImage(String name) {
        return imageStore.get(name);
    }

}
