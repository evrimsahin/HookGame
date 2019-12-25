import javafx.scene.layout.Pane;

public abstract class GraphicalContent {
    // Method for updating graphical property
    public abstract void updateGraphics();

    // Method for updateing coordinate property
    public abstract void updateCoordinates();

    // Method for inserting object to pane
    public abstract void insertToPane(Pane pane);


}
