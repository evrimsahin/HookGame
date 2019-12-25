import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

class Grasp extends GraphicalContent implements HasEndPoint, Mortal, setOnDying, SetAlpha {
    public Vector2 startPoint;
    public Vector2 endPoint;
    public Vector2 size;
    public Vector2 direction;
    public Vector2[] corners;
    Pane pane;
    Polygon poly = new Polygon(0, 0, 0, 0, 0, 0, 0, 0);
    ObservableList<Double> list = poly.getPoints();
    public boolean isDying;
    float lifespan;
    float speed;
    int R, G, B;

    public Grasp(Vector2 startPoint, Vector2 direction, Vector2 size) {
        R = 40;
        G = 40;
        B = 40;
        corners = new Vector2[4];
        this.size = size;
        this.direction = direction;
        this.startPoint = startPoint.getCopy();
        lifespan = 0;
        speed = 4;
        isDying = false;

        updateGraphics();
        updateCoordinates();
    }


    @Override
    public void updateGraphics() {
        poly.setFill(Color.rgb(R, G, B, lifespan));
        poly.setStrokeWidth(0);

    }

    @Override
    public void updateCoordinates() {

        // Calculate the end point
        Vector2 dirCopy = new Vector2(direction.x, direction.y);
        Vector2 strtCopy = new Vector2(startPoint.x, startPoint.y);
        endPoint = strtCopy.getScaledVector(dirCopy, size.x);

        // Calculate the coordinates
        Vector2 tempDir = direction.turn90Degree();
        corners[0] = startPoint.getScaledVector(tempDir, size.y / 2);
        corners[1] = endPoint.getScaledVector(tempDir, size.y / 2);
        corners[2] = endPoint.getScaledVector(tempDir.getInverted(), size.y / 2);
        corners[3] = startPoint.getScaledVector(tempDir.getInverted(), size.y / 2);

        // Update the coordinates of graphical object
        list.set(0, (double) corners[0].x);
        list.set(1, (double) corners[0].y);
        list.set(2, (double) corners[1].x);
        list.set(3, (double) corners[1].y);
        list.set(4, (double) corners[2].x);
        list.set(5, (double) corners[2].y);
        list.set(6, (double) corners[3].x);
        list.set(7, (double) corners[3].y);

    }

    @Override
    public void insertToPane(Pane pane) {
        // If pane doesn't already contain graphical object, add it to the pane.
        if (!pane.getChildren().contains(poly))
            pane.getChildren().add(poly);

        this.pane = pane;
    }


    @Override
    public Vector2 getEndPoint() {
        return Vector2.substract(endPoint, direction);
    }


    @Override
    public void setOnDying() {
        isDying = true;

    }


    @Override
    public boolean isDead() {
        if (isDying) {

            if (size.y > 1) {
                size.y -= speed;
                lifespan -= 0.015;
                updateCoordinates();
                updateGraphics();
            } else {
                pane.getChildren().remove(poly);
                return true;
            }
        }
        return false;
    }


    @Override
    public void setAlpha(float alpha) {
        lifespan = alpha;
    }


}