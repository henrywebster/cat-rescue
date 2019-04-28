package info.hwebs.game;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.text.Text;
import javafx.scene.text.Font;


import info.hwebs.ui.Window;
import info.hwebs.ui.WindowContext;

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

        WindowContext context = window.getContext();


        // TODO fix later

        Image image = new Image(Window.class.getResourceAsStream("/grass.png"), context.size, context.size, false, true);
        
        for (int i = 0; i < context.count; ++i) {
            for (int j = 0; j < context.count; ++j) {            

                ImageView imageView = new ImageView(image);
                imageView.setX(i * context.size);
                imageView.setY(j * context.size); 
                window.addToGameScene(imageView);
           }
        }

        Text text = new Text("Cat Rescue!");
        text.setFont(new Font(60));

        window.addToInfoScene(text);




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
