package info.hwebs.game;

import info.hwebs.controller.TileController;
import info.hwebs.model.Direction;
import info.hwebs.model.Model;
import info.hwebs.model.Models;
import info.hwebs.resource.Resources;
import info.hwebs.ui.Window;
import info.hwebs.ui.WindowContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.sound.sampled.Clip;

public class Game extends Application {

    private static final Logger logger = Logger.getGlobal();

    private static final String TITLE = "Cat Rescue";

    private static Window window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // TODO figure out something else?
        window = new Window(stage, TITLE);

        startUp();

        // TODO: game loop

        shutDown();
    }

    private void startUp() {

        logger.setLevel(Level.FINE);
        logger.info("Starting application");

        WindowContext context = window.getContext();

        Resources.registerClip("key", "/key-up-2.wav");
        Resources.registerImage("heroine", "/heroine.png");
        Resources.registerImage("grass", "/grass.png");

        // TODO stream it

        Collection<TileController> tileControllers = new ArrayList<>();
        Collection<Model> tileModels = new ArrayList<>();

        final Image grassImage = Resources.getImage("grass");

        for (int i = 0; i < context.count; ++i) {
            for (int j = 0; j < context.count; ++j) {

                // TODO: dangerous null!
                Model tile = Models.tileModelOf(i, j, context.size, grassImage, null, window);
                tileModels.add(tile);
            }
        }

        final Image playerImage = Resources.getImage("heroine");
        final Clip clip = Resources.getClip("key");
        Model player = Models.tileModelOf(0, 0, context.size, playerImage, clip, window);
        TileController c = new TileController(player);

        Text text = new Text("Cat Rescue!");
        text.setFont(new Font(60));

        window.addToInfoScene(text);

        tileModels.forEach(Model::update);

        window.addKeyEvent(
                event -> {
                    switch (event.getCode()) {
                        case DOWN:
                            c.orient(Direction.DOWN);
                            c.move();
                            break;
                        case UP:
                            c.orient(Direction.UP);
                            c.move();
                            break;
                        case LEFT:
                            c.orient(Direction.LEFT);
                            c.move();
                            break;
                        case RIGHT:
                            c.orient(Direction.RIGHT);
                            c.move();
                            break;
                    }
                    player.update();
                });
    }

    private void shutDown() {}

    private void update() {}

    private void render() {}
}
