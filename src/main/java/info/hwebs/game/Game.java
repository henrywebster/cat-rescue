package info.hwebs.game;

import info.hwebs.controller.Controller;
import info.hwebs.controller.Controllers;
import info.hwebs.event.EntityVisitor;
import info.hwebs.event.TerrainVisitor;
import info.hwebs.maps.GameMap;
import info.hwebs.maps.MapTile;
import info.hwebs.maps.Terrain;
import info.hwebs.media.Audio;
import info.hwebs.model.Direction;
import info.hwebs.model.Model;
import info.hwebs.model.Models;
import info.hwebs.resource.Resources;
import info.hwebs.ui.Window;
import info.hwebs.ui.WindowContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
        Resources.registerImage("sand", "/sand.png");
        Resources.registerImage("water", "/water.png");

        List<Integer> mapData = new Random().ints(1000, 0, 3).boxed().collect(Collectors.toList());

        Collection<Controller> tileControllers = new ArrayList<>();
        List<Model> tileModels = new ArrayList<>();

        List<MapTile> mapTiles = new ArrayList<>();

        final Image grassImage = Resources.getImage("grass");
        final Image sandImage = Resources.getImage("sand");
        final Image waterImage = Resources.getImage("water");

        final Map<Terrain, Image> tileImages =
                Map.of(
                        Terrain.GRASS,
                        grassImage,
                        Terrain.SAND,
                        sandImage,
                        Terrain.WATER,
                        waterImage);

        GameMap map = GameMap.load(30, 30, tileImages, mapData, window);

        final Image playerImage = Resources.getImage("heroine");
        Model player = Models.spriteModelOf(6, 6, context.size, playerImage, window);
        Controller c =
                Controllers.entityControllerOf(player, map, Set.of(Terrain.SAND, Terrain.GRASS));

        final Clip clip = Resources.getClip("key");
        TerrainVisitor.addEvent(EntityVisitor.class, () -> Audio.submit(clip));

        Model cameraModel = Models.cameraModelOf(0, 0, context.size, window.gameCamera);
        Controller cameraCon = Controllers.trackingControllerOf(13.0, cameraModel, player, map);

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
                            cameraCon.orient(Direction.DOWN);
                            cameraCon.move();
                            break;
                        case UP:
                            c.orient(Direction.UP);
                            c.move();
                            cameraCon.orient(Direction.UP);
                            cameraCon.move();
                            break;
                        case LEFT:
                            c.orient(Direction.LEFT);
                            c.move();
                            cameraCon.orient(Direction.LEFT);
                            cameraCon.move();
                            break;
                        case RIGHT:
                            c.orient(Direction.RIGHT);
                            c.move();
                            cameraCon.orient(Direction.RIGHT);
                            cameraCon.move();
                            break;
                    }
                    player.update();
                    cameraModel.update();
                });
    }

    private void shutDown() {}

    private void update() {}

    private void render() {}
}
