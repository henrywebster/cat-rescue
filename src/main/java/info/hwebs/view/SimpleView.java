package info.hwebs.view;

import javafx.scene.Node;
import info.hwebs.model.Model;

public class SimpleView implements View {

    private final Node node;

    SimpleView(Node node) {
        this.node = node;
    }

    @Override
    public void update(Model model) {
        this.node.setLayoutX(model.getX());
        this.node.setLayoutY(model.getY()); 
    }

    @Override
    public Node getNode() {
        return this.node;
    }
}
