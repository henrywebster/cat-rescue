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

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import info.hwebs.resource.ResourceManager;

public class Window {

    private static final Logger logger = Logger.getGlobal();

    // TODO move to properties
    private static final int COLUMNS = 13;

    private final Pane root;
    private final GridPane main;
    private final GridPane info;
    private final Pane game;

    private final Properties properties;

    private final WindowContext context;

    private Properties initProperties() {

/*
        final Properties properties = new Properties();

        properties.setProperty("background.image.scale", "1.0");

        try (InputStream input = Window.class.getResourceAsStream("/config.properties")) {
            properties.load(input);
            logger.info("Properties loaded.");
        } catch (IOException | NullPointerException e) {
            logger.warning("Could not load properties file. Using defaults.");
        }
        return properties;
*/

        // TODO put in startup code
        ResourceManager.registerConfig("config", "/config.properties");
        return ResourceManager.getConfig("config");
    }

    private Background initBackground() {

        Background bg = null;
/*
        } catch (NumberFormatException ex) {
            logger.warning("Invalid value in properties.");
        } catch (IOException | NullPointerException ex) {
            logger.warning("Could not load background image.");
            logger.warning(ex.getMessage());
        }
*/

        // TODO move to startup
        ResourceManager.registerImage("background", properties.getProperty("background.image"));
        final Image image = ResourceManager.getImage("background");
 
        final double scale = Double.parseDouble(properties.getProperty("background.image.scale"));
        final double width = image.getWidth() * scale;                                                    
        final double height = image.getHeight() * scale;

        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
                BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, false, false));

            bg = new Background(bgImage);                                                                                                                                               if (null == bg) {
            BackgroundFill bgFill = new BackgroundFill(Color.BLUEVIOLET, null, null);
            bg = new Background(bgFill);
        }
        return bg;
    }

    public Window(final Stage stage, final String title) {
        assert(null != stage);

        info = new GridPane();
        game = new Pane();        
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
 
        root.requestFocus();
 //       root.setOnKeyPressed(event -> logger.info("HELLO"));

    };

    // This is very basic...
    public void addToGameScene(Node node) {
        assert(!game.getChildren().contains(node));
        
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
        assert(!game.getChildren().contains(node));
        info.getChildren().add(node);
    }
}
