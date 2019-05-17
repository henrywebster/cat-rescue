package info.hwebs.media;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.Clip;

public final class Audio {

    // TODO: make custom fixed pool executor with limited queue
    private static ExecutorService executor;

    static {
        executor = Executors.newSingleThreadExecutor();
    }

    // TODO error handling
    public static void submit(Clip clip) {
        assert (null != clip);
        executor.submit(
                () -> {
                    clip.drain();
                    clip.setMicrosecondPosition(0);
                    clip.start();
                });
    }

    private Audio() {}
}
