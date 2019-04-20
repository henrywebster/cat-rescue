package info.hwebs.media;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.io.IOException;
import javafx.scene.image.Image;

public class ImageManager {

    public static final int RESERVE_CNT = 20;

    private static final Map<String, Image> images;

    static {
        images = new HashMap<>(RESERVE_CNT);
    }

    private ImageManager() {}

    public static void loadImage(String imgName, String filepath) {
        assert(!images.containsKey(imgName));

        try (InputStream is = ImageManager.class.getResourceAsStream(filepath)) {
            images.put(imgName, new Image(is));
        } catch (IOException ex) {
            // TODO: log error
            assert(false);
        } 
    } 

}
