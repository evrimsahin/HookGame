import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

class Gate extends GraphicalContent implements Mortal, setOnDying, SetAlpha {

    public boolean[] isFull = {false, false, false, false};
    public Vector2[] points;
    public Vector2 direction;
    public Vector2 centerPoint;
    public boolean isConnected;
    public float radius;
    public Circle cir;
    public Line line;
    public Button button;
    public boolean isDying;
    public ArrayList<Hook> hooks;
    public float lifespan;
    public boolean isDisappearing;
    Pane pane;
    public int RL, GL, BL;
    public int RC, GC, BC;


    public Gate(Vector2 startAnchorPoint, Vector2 direction, float radius, boolean isConnected) {
        points = new Vector2[4];

        RL = 250;
        GL = 40;
        BL = 40;
        RC = 40;
        GC = 40;
        BC = 40;

        isDisappearing = false;
        lifespan = 0;
        isFull[0] = true;
        this.direction = direction.getCopy();
        this.radius = radius * 2;
        this.isConnected = isConnected;
        hooks = new ArrayList<Hook>();


        isDying = false;

        // Create new line and circle object
        cir = new Circle();
        line = new Line();


        points[0] = startAnchorPoint.getCopy();


        // Calculate the center and points
        calculateCenterPoint();
        calculatePoints();


        button = new Button();
        button.setLayoutX(centerPoint.x - radius);
        button.setLayoutY(centerPoint.y - radius);

        button.setShape(new Circle(radius));
        button.setMinSize(radius * 2, radius * 2);
        button.setMaxSize(radius * 2, radius * 2);
        button.setStyle("-fx-background-color: transparent;");


    }

    public void calculatePoints() {
        Vector2 temp1 = new Vector2(points[0].x, points[0].y);
        Vector2 temp2 = new Vector2(direction.x, direction.y);
        temp2.multiply(radius);
        temp1.add(temp2);
        points[2] = temp1;


        Vector2 temp = Vector2.substract(points[0], centerPoint);
        temp = temp.turn90Degree();
        Vector2 tempInverted = temp.getInverted();

        points[1] = Vector2.add(temp, centerPoint);
        points[3] = Vector2.add(tempInverted, centerPoint);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public Vector2 getCenterPoint() {
        return centerPoint;
    }

    public Vector2 getEndAnchorPoint() {
        return points[2];
    }

    public void calculateCenterPoint() {
        Vector2 temp1 = new Vector2(points[0].x, points[0].y);
        Vector2 temp2 = new Vector2(direction.x, direction.y);
        temp2.multiply(radius / 2);
        temp1.add(temp2);
        centerPoint = temp1;
    }


    public void invertGate() {
        // If sword is not on action !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        isConnected = !isConnected;

    }

    @Override
    public void updateGraphics() {

        cir.setFill(null);
        cir.setStroke(Color.rgb(RC, GC, BC, lifespan));
        cir.setStrokeWidth(4);


        line.setStroke(Color.rgb(RL, GL, BL, lifespan));
        line.setStrokeWidth(4);

    }

    @Override
    public void updateCoordinates() {
        cir.setCenterX(centerPoint.x);
        cir.setCenterY(centerPoint.y);
        cir.setRadius(radius / 2);


        if (isConnected) {
            line.setStartX(points[0].x);
            line.setStartY(points[0].y);
            line.setEndX(points[2].x);
            line.setEndY(points[2].y);
        } else {
            line.setStartX(points[1].x);
            line.setStartY(points[1].y);
            line.setEndX(points[3].x);
            line.setEndY(points[3].y);

        }

        if (!isBusy() && !isDisappearing) {
            button.setOnAction(e -> {
                invertGate();
                updateCoordinates();
                updateGraphics();
            });
        } else {
            button.setOnAction(e -> {
            });
        }

    }

    @Override
    public void insertToPane(Pane pane) {
        if (!pane.getChildren().contains(line))
            pane.getChildren().add(line);

        if (!pane.getChildren().contains(cir))
            pane.getChildren().add(cir);

        if (!pane.getChildren().contains(button))
            pane.getChildren().add(button);

        this.pane = pane;


    }

    @Override
    public void setOnDying() {
        isDying = true;

    }

    public boolean isAllHooksDead() {
        for (int i = 0; i < hooks.size(); i++) {
            if (hooks.get(i) != null && !hooks.get(i).isDying)
                return false;
        }

        return true;

    }

    @Override
    public boolean isDead() {
        if (isAllHooksDead()) {

            isDisappearing = true;
            Vector2 temp = Vector2.substract(points[2], points[0]);
            temp.normalize();
            temp.multiply(2);
            points[2].subtract(temp);
            points[0].subtract(temp.getInverted());


            Vector2 temp2 = Vector2.substract(points[1], points[3]);
            temp2.normalize();
            temp2.multiply(2);
            points[1].subtract(temp2);
            points[3].subtract(temp2.getInverted());


            if (lifespan > 0) {
                lifespan -= 0.05;
                if (lifespan < 0)
                    lifespan = 0;
            } else {
                lifespan = 0;
                pane.getChildren().remove(line);
                pane.getChildren().remove(cir);
                return true;
            }


        }


        return false;
    }


    public boolean isBusy() {
        for (Hook i : hooks) {
            if (i.isGateBusy)
                return true;

        }
        return false;

    }

    public boolean gotSpace() {
        for (int i = 0; i < 4; i++) {
            if (isFull[i] == false)
                return true;
        }

        return false;

    }

    @Override
    public void setAlpha(float alpha) {
        lifespan = alpha;
    }


}