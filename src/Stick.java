import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

class Stick extends GraphicalContent implements HasEndPoint, UpdateEndPoint, CollisionData, Mortal, SetAlpha {
    public Vector2 startPoint;
    public Vector2 endPoint;
    public Vector2 size;
    public Vector2 direction;
    public Vector2[] corners;
    Polygon poly;
    ObservableList<Double> list;
    ArrayList<Vector2> collisionData;
    public float lifespan;
    int R, G, B;

    public Stick(Vector2 startPoint, Vector2 direction, Vector2 size) {

        this.size = size;
        this.direction = direction;
        this.startPoint = startPoint;
        R = 40;
        G = 40;
        B = 40;
        lifespan = 0;
        corners = new Vector2[4];

        poly = new Polygon(0, 0, 0, 0, 0, 0, 0, 0);
        list = poly.getPoints();


        collisionData = new ArrayList<Vector2>();

        updateGraphics();
        updateCoordinates();


    }

    public Vector2 getEndPoint() {
        return Vector2.substract(endPoint, direction);
    }

    public void calculateEndPoint() {
        Vector2 dirCopy = new Vector2(direction.x, direction.y);
        Vector2 strtCopy = new Vector2(startPoint.x, startPoint.y);
        endPoint = strtCopy.getScaledVector(dirCopy, size.x);
    }

    public void updateCorners() {
        calculateEndPoint();

        Vector2 tempDir = direction.turn90Degree();
        corners[0] = startPoint.getScaledVector(tempDir, size.y / 2);
        corners[1] = endPoint.getScaledVector(tempDir, size.y / 2);
        corners[2] = endPoint.getScaledVector(tempDir.getInverted(), size.y / 2);
        corners[3] = startPoint.getScaledVector(tempDir.getInverted(), size.y / 2);


    }

    @Override
    public void updateGraphics() {
        poly.setFill(Color.rgb(R, G, B, lifespan));
        poly.setStrokeWidth(0);
    }

    @Override
    public void updateCoordinates() {

        calculateEndPoint();
        updateCorners();

        float offsetY = -direction.y * 0; // 8 di
        float offsetX = -direction.x * 0;

        list.set(0, (double) corners[0].x + offsetX);
        list.set(1, (double) corners[0].y + offsetY);
        list.set(2, (double) corners[1].x);
        list.set(3, (double) corners[1].y);
        list.set(4, (double) corners[2].x);
        list.set(5, (double) corners[2].y);
        list.set(6, (double) corners[3].x + offsetX);
        list.set(7, (double) corners[3].y + offsetY);

    }

    Pane temp123;

    @Override
    public void insertToPane(Pane pane) {
        if (!pane.getChildren().contains(poly))
            pane.getChildren().add(poly);

//		temp123 = pane;
//		ArrayList<Vector2> temp = new ArrayList<Vector2>();
//		calculateCollisionData(temp);
    }

    @Override
    public void updateEndPoint(Vector2 endPoint) {
        this.startPoint = endPoint.getCopy();


    }


    @Override
    public void calculateCollisionData(ArrayList<Vector2> collisionPoints) {

        Vector2 tempDir = direction.getCopy();
        Vector2 tempDirNormal = tempDir.getCopy().turnMinus90Degree();
        Vector2 topLeftCorner = corners[0].getCopy();

        for (int i = 0; i < size.y / 4; i++) {
            Vector2 tempTopLeftCorner = topLeftCorner.getCopy();
            tempTopLeftCorner.addScaledVector(tempDirNormal, 4 * i);

            for (int k = 0; k < size.x / 4; k++) {
                Vector2 temp = tempTopLeftCorner.getCopy();

                collisionPoints.add(new Vector2((int) temp.x, (int) temp.y));
                tempTopLeftCorner.addScaledVector(tempDir, 4);

//				Circle circ = new Circle();
//				circ.setCenterX(temp.x);
//				circ.setCenterY(temp.y);
//				circ.setFill(Color.BLUE);
//				circ.setRadius(1);
//
//				if(temp123 != null && !temp123.getChildren().contains(circ) )
//					temp123.getChildren().add(circ);
            }

        }


    }

    @Override
    public boolean isDead() {
        if (size.x <= 0) {
            poly.setFill(null);
            return true;
        }

        return false;
    }

    @Override
    public void setAlpha(float alpha) {
        lifespan = alpha;
    }


}