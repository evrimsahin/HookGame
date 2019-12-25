import java.util.ArrayList;

import javafx.scene.layout.Pane;

class Sword extends GraphicalContent implements CollisionData, Mortal, setOnDying, SetAlpha {
    private Vector2 startPoint;
    private Vector2 direction;
    public ArrayList<GraphicalContent> components;
    public boolean isOnAction;
    public boolean isDying;


    public Sword(Vector2 startPoint, Vector2 direction) {
        this.startPoint = startPoint;
        this.direction = direction;
        components = new ArrayList<GraphicalContent>();

        isOnAction = false;
        isDying = false;

        updateGraphics();
        updateCoordinates();

    }


    public void swordOnAction() {
        setOnDying();
        if (components.size() > 1) {
            if (((Grasp) components.get(0)).size.y <= 90) {
                ((Grasp) components.get(0)).size.y += 2;
            }


            if (components.get(1) instanceof Stick) {


                ((Stick) components.get(1)).size.x -= 4f;

                if (((Mortal) components.get(1)).isDead()) {
                    components.remove(1);
                }

            } else {
                ((Sickle) components.get(1)).startPoint.addScaledVector(direction, -4f);
                ((Sickle) components.get(1)).isShrinking = true;
                if (((HasEndPoint) components.get(0)).getEndPoint() != null)
                    ((Sickle) components.get(1)).graspEnd = ((HasEndPoint) components.get(0)).getEndPoint().getCopy();

                if (((Sickle) components.get(1)).isDead()) {
                    components.remove(1);
                }

            }

        }

        if (components.size() == 1)
            isDying = true;

        updateEndPoint();
    }

    public void addGrasp(Vector2 size) {
        components.add(new Grasp(startPoint, direction, size));
    }

    public void addStick(Vector2 size) {
        components.add(new Stick(getLastEndPoint(), direction, size));
    }

    public void addSickle(Vector2 size, boolean way) {
        components.add(new Sickle(getLastEndPoint(), direction, size, way));
    }

    public Vector2 getLastEndPoint() {
        return ((HasEndPoint) components.get(components.size() - 1)).getEndPoint();
    }

    @Override
    public void updateGraphics() {
        for (int i = 0; i < components.size(); i++) {
            ((GraphicalContent) components.get(i)).updateGraphics();
        }

    }

    @Override
    public void updateCoordinates() {

        if (isOnAction)
            swordOnAction();

        for (int i = 0; i < components.size(); i++) {
            ((GraphicalContent) components.get(i)).updateCoordinates();

        }


    }

    @Override
    public void insertToPane(Pane pane) {


        for (int i = 0; i < components.size(); i++) {
            ((GraphicalContent) components.get(i)).insertToPane(pane);
        }

    }

    public void updateEndPoint() {
        if (components.size() > 0) {

            for (int i = 1; i < components.size(); i++) {
                ((UpdateEndPoint) components.get(i)).updateEndPoint(((HasEndPoint) components.get(i - 1)).getEndPoint());
            }

        }


    }


    @Override
    public void calculateCollisionData(ArrayList<Vector2> collisionPoints) {
        for (int i = 1; i < components.size(); i++) {
            ((CollisionData) components.get(i)).calculateCollisionData(collisionPoints);
        }

    }


    @Override
    public boolean isDead() {
        if (isDying && components.size() == 1) {

            return true;

        }


        return false;
    }


    @Override
    public void setOnDying() {
        isDying = true;

    }


    @Override
    public void setAlpha(float alpha) {
        for (int i = 0; i < components.size(); i++) {
            ((SetAlpha) components.get(i)).setAlpha(alpha);
        }
    }

    public void setRandomColors() {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof Stick) {
                ((Stick) components.get(i)).R = (int) (Math.random() * 255);
                ((Stick) components.get(i)).G = (int) (Math.random() * 255);
                ((Stick) components.get(i)).B = (int) (Math.random() * 255);
            } else if (components.get(i) instanceof Grasp) {
                ((Grasp) components.get(i)).R = (int) (Math.random() * 255);
                ((Grasp) components.get(i)).G = (int) (Math.random() * 255);
                ((Grasp) components.get(i)).B = (int) (Math.random() * 255);
            } else if (components.get(i) instanceof Sickle) {
                ((Sickle) components.get(i)).R = (int) (Math.random() * 255);
                ((Sickle) components.get(i)).G = (int) (Math.random() * 255);
                ((Sickle) components.get(i)).B = (int) (Math.random() * 255);
            }
        }
    }

}