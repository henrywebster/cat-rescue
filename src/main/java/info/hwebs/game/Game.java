package info.hwebs.game;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

import info.hwebs.ui.Window;

public class Game extends Application {

    private static final Logger logger = Logger.getGlobal();

    private static final String TITLE = "Cat Rescue";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        logger.setLevel(Level.FINE);
        logger.info("Starting application");


        Window window = new Window(stage, TITLE);

        startUp();
       

        shutDown();

    }

    private void startUp() {

       

    }

    private void shutDown() {

    }

    private void update() {
    }

    private void render() {
    }

    
}
