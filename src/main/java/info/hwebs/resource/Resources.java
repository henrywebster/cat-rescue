package info.hwebs.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.scene.image.Image;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// TODO interface for manager?
public final class Resources {

    // TODO generalize
    private static final Map<String, Properties> configStore = new HashMap<>();

    private static final Map<String, Image> imageStore = new HashMap<>();

    private static final Map<String, Clip> clipStore = new HashMap<>();

    public static void registerConfig(String name, String filename) {
        assert (!configStore.containsKey(name));

        try (InputStream input = Resources.class.getResourceAsStream(filename)) {

            Properties properties = new Properties();
            properties.load(input);
            configStore.put(name, properties);

        } catch (IOException | NullPointerException e) {
            // TODO handle
        }
    }

    // TODO throw exception when not in store
    public static Properties getConfig(String name) {
        assert (configStore.containsKey(name));
        return configStore.get(name);
    }

    public static void registerImage(String name, String filename) {
        assert (!configStore.containsKey(name));

        try (InputStream input = Resources.class.getResourceAsStream(filename)) {
            imageStore.put(name, new Image(input));
        } catch (IOException e) {
            // TODO handle
        }
    }

    public static Image getImage(String name) {
        return imageStore.get(name);
    }

    public static void registerClip(String name, String filename) {
        try (AudioInputStream input =
                AudioSystem.getAudioInputStream(Resources.class.getResourceAsStream(filename))) {
            final Clip clip = AudioSystem.getClip();
            clipStore.put(name, clip);
            clip.open(input);
        } catch (Exception e) {
            // TODO handle
            e.printStackTrace();
        }
    }

    public static Clip getClip(String name) {
        return clipStore.get(name);
    }
}
