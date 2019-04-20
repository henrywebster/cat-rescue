package info.hwebs.ui;

import java.lang.Math;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.*;

public class Window {

    private static final Logger logger = Logger.getGlobal();

    private static final int COLUMNS = 13;

    private final Pane root;
    private final GridPane main;
    private final GridPane info;
    private final TilePane game;

    public Window(final Stage stage, final String title) {
        assert(null != stage);

        info = new GridPane();
        game = new TilePane();        
        main = new GridPane();       
        root = new Pane(main);
        
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

        info.setStyle("-fx-background-color: yellow;");
        game.setStyle("-fx-background-color: green;"); 
        main.setStyle("-fx-background-color: magenta;");
        
        main.setPrefSize(width, height);
        main.setLayoutX(startX);
        main.setLayoutY(startY);
        
        root.setPrefSize(stage.getWidth(), stage.getHeight());       
        info.setPrefSize(width, targetSize);
        game.setPrefSize(width, width);

        main.add(info, 0, 0);
        main.add(game, 0, 1);

        stage.setTitle(title);
        stage.show();
    };
}
