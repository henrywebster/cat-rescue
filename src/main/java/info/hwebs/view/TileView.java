package info.hwebs.view;

import javafx.scene.Node;
import info.hwebs.model.Model;

public class TileView implements View {

    private final Node node;

    TileView(Node node) {
        this.node = node;
    }

    @Override
    public void update(Model model) {

 //       System.out.print("(" + model.getX() + "," + model.getY() + ")");
        this.node.relocate(model.getX() * node.getLayoutBounds().getWidth(), model.getY() * node.getLayoutBounds().getWidth());
//        this.node.setTranslateX(model.getX() * node.getLayoutBounds().getWidth());
//        this.node.setTranslateY(model.getY() * node.getLayoutBounds().getHeight()); 
    }

    @Override
    public Node getNode() {
        return this.node;
    }
}
