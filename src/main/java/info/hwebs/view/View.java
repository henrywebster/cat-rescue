package info.hwebs.view;

import info.hwebs.model.Model;

import javafx.scene.Node;

public interface View {

    public void update(Model model);

    // TODO fix
    public Node getNode();

}
