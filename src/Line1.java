import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class Line1 extends GraphicalContent implements HasEndPoint, Mortal, setOnDying, SetAlpha {

    public Line line = new Line();
    private ArrayList<Vector2> locations;
    public ArrayList<Line> lines;
    public int preLocationsSize;
    public Vector2 direction;
    public boolean isDying;
    public float lifespan;
    public float appearSpeed;
    public int preAppear;
    Pane pane;
    float speed;
    int R, G, B;


    public Line1() {
        locations = new ArrayList<Vector2>();
        lines = new ArrayList<Line>();
        preLocationsSize = 0;
        isDying = false;

        R = 40;
        G = 40;
        B = 40;
        speed = 1f;
        lifespan = 0f;
        updateCoordinates();
    }

    public void addPoint(Vector2 location) {
        locations.add(location.getCopy());
        calculateDirection();
    }

    public void calculateDirection() {
        if (locations.size() > 1) {
            Vector2 temp = Vector2.substract(locations.get(locations.size() - 1), locations.get(locations.size() - 2));
            temp.normalize();
            direction = temp;
        }
    }

    public ArrayList<Vector2> getPointsArrayList() {
        return locations;
    }

    @Override
    public void updateGraphics() {
        for (Line i : lines) {
            i.setStroke(Color.rgb(R, G, B, lifespan));
            i.setStrokeWidth(4);
        }


    }

    @Override
    public void updateCoordinates() {
        int temp = 0;

        if (locations.size() < preLocationsSize)
            temp = preLocationsSize - locations.size();

        preLocationsSize = locations.size();
        int size = locations.size() - 1;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (i < lines.size()) {
                    lines.get(i).setStartX(locations.get(i).x);
                    lines.get(i).setStartY(locations.get(i).y);
                    lines.get(i).setEndX(locations.get(i + 1).x);
                    lines.get(i).setEndY(locations.get(i + 1).y);
                } else {
                    lines.add(i, new Line(locations.get(i).x, locations.get(i).y, locations.get(i + 1).x, locations.get(i + 1).y));
                }

            }
            for (int i = size; i < temp + size; i++) {
                if (lines.size() > i)
                    lines.remove(i);
            }

        }

    }

    @Override
    public void insertToPane(Pane pane) {
        for (Line i : lines) {
            if (!pane.getChildren().contains(i))
                pane.getChildren().add(i);
        }

        this.pane = pane;
    }

    @Override
    public Vector2 getEndPoint() {
        return locations.get(locations.size() - 1);
    }

    @Override
    public boolean isDead() {
        if (isDying) {
            for (int i = 0; i < lines.size(); i++) {
                if (lifespan <= 0) {

                    pane.getChildren().remove(lines.get(i));
                    lifespan = 0;

                } else {


                    if (locations.size() >= 2) {
                        Vector2 lastDir = getLastLineDirection();
                        if (lastDir != null) {
                            lastDir.multiply(speed);
                            locations.get(locations.size() - 1).add(lastDir);
                        }

                        Vector2 firstDir = getFirstLineDirection();
                        if (firstDir != null) {
                            firstDir.multiply(speed);
                            locations.get(0).add(firstDir);
                        }
                        updateCoordinates();
                    }

                    lifespan -= 0.005;
                    if (lifespan < 0)
                        lifespan = 0;
                }
            }

            updateCoordinates();
            updateGraphics();


            if (lifespan == 0 || lines.isEmpty()) {

                return true;

            }

        }
        return false;
    }

    @Override
    public void setOnDying() {
        isDying = true;

    }

    public Vector2 getLastLineDirection() {
        if (locations.size() >= 2) {
            Vector2 first = locations.get(locations.size() - 1).getCopy();
            Vector2 second = locations.get(locations.size() - 2).getCopy();

            Vector2 temp = Vector2.substract(second, first);


            if (temp.magnitude() <= 1) {
                locations.remove(locations.size() - 2);
                pane.getChildren().remove(lines.get(lines.size() - 1));
                lines.remove(lines.size() - 1);

            }


            temp.normalize();
            return temp;
        }

        return null;

    }

    public Vector2 getFirstLineDirection() {
        if (locations.size() >= 2) {
            Vector2 first = locations.get(0).getCopy();
            Vector2 second = locations.get(1).getCopy();

            second.subtract(first);
            if (second.magnitude() <= 1) {
                locations.remove(0);
                pane.getChildren().remove(lines.get(0));
                lines.remove(0);
            }
            second.normalize();
            return second;
        }

        return null;


    }

    @Override
    public void setAlpha(float alpha) {
        lifespan = alpha;
    }

}