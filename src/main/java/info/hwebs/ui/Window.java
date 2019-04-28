package info.hwebs.ui;

import java.lang.Math;
import java.util.logging.Logger;
import java.util.Properties;

import java.lang.NullPointerException;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javafx.scene.Node;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Window {

    private static final Logger logger = Logger.getGlobal();

    // TODO move to properties
    private static final int COLUMNS = 13;

    private final Pane root;
    private final GridPane main;
    private final GridPane info;
    private final TilePane game;

    private final Properties properties;

    private final WindowContext context;

    private Properties initProperties() {

        final Properties properties = new Properties();

        properties.setProperty("background.image.scale", "1.0");

        try (InputStream input = Window.class.getResourceAsStream("/config.properties")) {
            properties.load(input);
            logger.info("Properties loaded.");
        } catch (IOException | NullPointerException e) {
            logger.warning("Could not load properties file. Using defaults.");
        }
        return properties;
    }

    private Background initBackground() {

        Background bg = null;

        try (BufferedInputStream input = new BufferedInputStream(Window.class.getResourceAsStream(properties.getProperty("background.image")))) {

            logger.info(Boolean.toString(input.markSupported()));

            input.mark(100_000_000);
            // TODO hack
            final Image info = new Image(input);

            input.reset();

            final double scale = Double.parseDouble(properties.getProperty("background.image.scale"));
            final double width = info.getWidth() * scale;
            final double height = info.getHeight() * scale;

            final Image tileImage = new Image(input, width, height, false, true);            

            BackgroundImage bgImage = new BackgroundImage(tileImage, 
                                                 BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);                         

            bg = new Background(bgImage);

        } catch (NumberFormatException ex) {
            logger.warning("Invalid value in properties.");
        } catch (IOException | NullPointerException ex) {
            logger.warning("Could not load background image.");
            logger.warning(ex.getMessage());
        }

        if (null == bg) {
            BackgroundFill bgFill = new BackgroundFill(Color.BLUEVIOLET, null, null);
            bg = new Background(bgFill);
        }
        return bg;
    }

    public Window(final Stage stage, final String title) {
        assert(null != stage);

        info = new GridPane();
        game = new TilePane();        
        main = new GridPane();       
        root = new Pane(main);

        properties = initProperties();

        root.setBackground(initBackground());
       
        final Scene scene = new Scene(root, Color.BLUE); 
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double minDimension = Math.min(screenBounds.getHeight(), screenBounds.getWidth());

        double targetSize = Math.floor(minDimension / (COLUMNS + 1));
        double height = targetSize * (COLUMNS + 1); 
        double width = targetSize * COLUMNS;    
 
        double startX = (screenBounds.getWidth() - width) / 2;
        double startY = (screenBounds.getHeight() - height) / 2;
   
        stage.setWidth(screenBounds.getWidth());    
        stage.setHeight(screenBounds.getHeight());

        info.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
        game.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        main.setBackground(new Background(new BackgroundFill(Color.MAGENTA, null, null)));   

        main.setPrefSize(width, height);
        main.setLayoutX(startX);
        main.setLayoutY(startY);
        
        root.setPrefSize(stage.getWidth(), stage.getHeight());       
        info.setPrefSize(width, targetSize);
        game.setPrefSize(width, width);

        main.add(info, 0, 0);
        main.add(game, 0, 1);

        this.context = new WindowContext(targetSize, COLUMNS);

        stage.setTitle(title);
        stage.show();
    };

    // This is very basic...
    public void addToGameScene(Node node) {
        assert(!game.getChildren().contains(node));
        
        game.getChildren().add(node);
    }

    public WindowContext getContext() {
        return this.context;
    }

    public void addToInfoScene(Node node) {
        assert(!game.getChildren().contains(node));
        info.getChildren().add(node);
    }
}
