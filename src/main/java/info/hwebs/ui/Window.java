package info.hwebs.ui;

import info.hwebs.resource.Resources;
import java.util.Properties;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

// TODO rename to something like Renderer

import javafx.scene.shape.Rectangle;

public class Window {

    private static final Logger logger = Logger.getGlobal();

    // TODO move to properties
    private static final int COLUMNS = 13;

    private final Pane root;
    private final Group main;
    private final GridPane info;
    private final Group game;

    // TODO figure out better variables

    public final Camera gameCamera;

    private final SubScene interfaceScene;
    private final SubScene gameScene;

    private final Properties properties;

    private final WindowContext context;

    private Properties initProperties() {

        // TODO put in startup code
        Resources.registerConfig("config", "/config.properties");
        return Resources.getConfig("config");
    }

    private Background initBackground() {

        Background bg = null;

        // TODO move to startup
        Resources.registerImage("background", properties.getProperty("background.image"));
        final Image image = Resources.getImage("background");

        final double scale = Double.parseDouble(properties.getProperty("background.image.scale"));
        final double width = image.getWidth() * scale;
        final double height = image.getHeight() * scale;

        BackgroundImage bgImage =
                new BackgroundImage(
                        image,
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(width, height, false, false, false, false));

        bg = new Background(bgImage);
        if (null == bg) {
            BackgroundFill bgFill = new BackgroundFill(Color.BLUEVIOLET, null, null);
            bg = new Background(bgFill);
        }
        return bg;
    }

    public Window(final Stage stage, final String title) {
        assert (null != stage);

        info = new GridPane();
        game = new Group();
        main = new Group();
        root = new Pane();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        properties = initProperties();

        root.setBackground(initBackground());

        final Scene scene = new Scene(root, Color.BLUE);

        final double screenHeight = screenBounds.getHeight();
        final double screenWidth = screenBounds.getWidth();

        final Group interfaceGroup = new Group();

        this.interfaceScene = new SubScene(info, screenWidth, screenHeight);

        gameCamera = new ParallelCamera();

        Camera cameraInterface = new ParallelCamera();

        interfaceScene.setCamera(cameraInterface);

        double minDimension = Math.min(screenBounds.getHeight(), screenBounds.getWidth());

        double targetSize = Math.floor(minDimension / (COLUMNS + 1));
        double height = targetSize * (COLUMNS + 1);
        double width = targetSize * COLUMNS;

        double startX = (screenBounds.getWidth() - width) / 2;
        double startY = (screenBounds.getHeight() - height) / 2;

        this.gameScene = new SubScene(game, width, width);
        gameScene.setTranslateX(startX);
        gameScene.setTranslateY(startY + targetSize);
        gameScene.setCamera(gameCamera);
        gameScene.setFill(Color.BLUE);

        // TODO this will change
        // TODO resuse CC object
        info.getColumnConstraints().add(new ColumnConstraints(startX));
        info.getColumnConstraints().add(new ColumnConstraints(width));
        info.getColumnConstraints().add(new ColumnConstraints(startX));

        stage.setScene(scene);

        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        root.setPrefSize(stage.getWidth(), stage.getHeight());

        main.getChildren().add(interfaceScene);
        main.getChildren().add(gameScene);

        root.getChildren().add(main);

        this.context = new WindowContext(targetSize, COLUMNS);

        stage.setTitle(title);
        stage.show();

        root.requestFocus();
    };

    // This is very basic...
    public void addToGameScene(Node node) {
        assert (!game.getChildren().contains(node));

        game.getChildren().add(node);
    }

    // TODO fix
    public void addKeyEvent(EventHandler<? super KeyEvent> value) {
        this.root.setOnKeyPressed(value);
    }

    public WindowContext getContext() {
        return this.context;
    }

    public void addToInfoScene(Node node) {
        assert (!info.getChildren().contains(node));
        // TODO, keep track of row #
        info.add(node, 1, 0);
    }
}
