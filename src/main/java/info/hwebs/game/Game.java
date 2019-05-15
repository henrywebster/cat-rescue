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

import info.hwebs.view.View;
import info.hwebs.view.Views;
import info.hwebs.model.Model;
import info.hwebs.model.Models;

import info.hwebs.controller.TileController;

import java.util.Collection;
import java.util.ArrayList;


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
        
        Image playerImage = new Image(Window.class.getResourceAsStream("/heroine.png"), context.size, context.size, false, true);


        // TODO stream it

        Collection<TileController> tileControllers = new ArrayList<>();
        Collection<Model> tileModels = new ArrayList<>();

        for (int i = 0; i < context.count; ++i) {
            for (int j = 0; j < context.count; ++j) {            

                View view = Views.tileViewOf(image);
                Model tile = Models.tileModelOf(i, j);
                TileController c = new TileController(view, tile);
                

                tileControllers.add(c);
                tileModels.add(tile);

//                ImageView imageView = new ImageView(image);
//                imageView.setX(i * context.size);
//                imageView.setY(j * context.size); 
                window.addToGameScene(view.getNode());
           }
        }

        View view = Views.tileViewOf(playerImage);
        Model player = Models.tileModelOf(0, 0);
        TileController c = new TileController(view, player);

        window.addToGameScene(view.getNode());

        Text text = new Text("Cat Rescue!");
        text.setFont(new Font(60));

        window.addToInfoScene(text);


        tileControllers.forEach(TileController::update);


        window.addKeyEvent(event -> {
            
            switch (event.getCode()) {
                case DOWN:
                    player.setY(player.getY() + 1.0);
                    break;
                case UP:
                    player.setY(player.getY() - 1.0);
                    break;
                case LEFT:
                    player.setX(player.getX() - 1.0);
                    break;
                case RIGHT:
                    player.setX(player.getX() + 1.0);
                    break;
            }
            c.update();
        });


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
