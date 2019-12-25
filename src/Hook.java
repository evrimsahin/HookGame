import java.util.ArrayList;

import javafx.scene.layout.Pane;

public class Hook extends GraphicalContent implements Adder, HasEndPoint, Mortal, setOnDying, SetAlpha, SetText {
    // Hook contains all components and this arraylist stores them.
    public ArrayList<GraphicalContent> allComponents;
    public ArrayList<Boolean> states;
    public ArrayList<Gate> connectedGates;
    public ArrayList<Vector2> collisionData;
    public HookSystem parent;
    public LevelGen levGen;
    public boolean justCreated;

    public int waitTime;
    public int waitTimeForDying;
    public float lifespan;
    public float lifespanSpeed;


    public boolean isConnected;
    public boolean addedGate;
    public boolean connectedToGate;
    public boolean isActive;
    public boolean isDying;
    public boolean isGateBusy;


    public Hook() {

        lifespan = 1;
        lifespanSpeed = 0.03f;
        waitTime = 10;
        waitTimeForDying = 10;
        allComponents = new ArrayList<GraphicalContent>();
        connectedGates = new ArrayList<Gate>();
        states = new ArrayList<Boolean>();
        collisionData = new ArrayList<Vector2>();
        addedGate = false;
        connectedToGate = false;
        isActive = false;
        isDying = false;
        isGateBusy = false;
        justCreated = true;
    }

//	public Vector2 getEndPoint() {
//		return ((HasEndPoint) getLastComponent()).getEndPoint();
//	}

    // Adds circle with
    public void addCircle(Vector2 location, float radius) {
        allComponents.add(new Circle1(location, radius));

    }

    // Adds line with location vector
    public void addLine(Vector2 location) {

        // if Arraylist is not empty
        if (!allComponents.isEmpty()) {

            if (!addedGate && !connectedToGate) {

                // If last object is an instance of line
                if (getLastComponent() instanceof Line1) {
                    // Just add another point to the line
                    ((Line1) getLastComponent()).addPoint(location);

                    // Else if last components is an instance of circle
                } else if (getLastComponent() instanceof Circle1) {

                    // We create a template vector that points Circle's center to
                    // Line's location.
                    Vector2 temp = new Vector2(location.x - ((Circle1) getLastComponent()).location.x,
                            location.y - ((Circle1) getLastComponent()).location.y);
                    // Then normalize the vector (Simply divide x and y values with
                    // the magnitude)
                    temp.normalize();

                    // Scale it with the radius of the circle (Simply closest edge
                    // of the circle to the line)
                    temp.multiply(((Circle1) getLastComponent()).radius);

                    temp.add(((Circle1) getLastComponent()).location);

                    // Adds a line object to arraylist
                    allComponents.add(new Line1());

                    // Adds the closest circle point to the line array
                    ((Line1) getLastComponent()).addPoint(temp);

                    // Adds the point location
                    ((Line1) getLastComponent()).addPoint(location);

                    // Else if last components is an instance of gate

                }
            } else if (addedGate) {

                allComponents.add(new Line1());
                ((Line1) getLastComponent()).addPoint(connectedGates.get(connectedGates.size() - 1).points[2]);
                ((Line1) getLastComponent()).addPoint(location);
                connectedGates.get(connectedGates.size() - 1).isFull[2] = true;
                states.add(new Boolean(true));
                addedGate = false;


            } else {

                allComponents.add(new Line1());
                if (connectedGates.get(connectedGates.size() - 1).isFull[1])
                    ((Line1) getLastComponent()).addPoint(connectedGates.get(connectedGates.size() - 1).points[3]);
                else
                    ((Line1) getLastComponent()).addPoint(connectedGates.get(connectedGates.size() - 1).points[1]);


                ((Line1) getLastComponent()).addPoint(location);
                states.add(new Boolean(false));
                connectedToGate = false;


            }

        }
    }

    public void addGrasp(Vector2 size) {

        Vector2 startPoint = ((Line1) getLastComponent()).getPointsArrayList()
                .get(((Line1) getLastComponent()).getPointsArrayList().size() - 1);
        Vector2 direction = Vector2.substract(startPoint, ((Line1) getLastComponent()).getPointsArrayList()
                .get(((Line1) getLastComponent()).getPointsArrayList().size() - 2));
        direction.normalize();
        allComponents.add(new Sword(startPoint, direction));

        ((Sword) getLastComponent()).addGrasp(size);

    }

    public void addStick(Vector2 size) {
        ((Sword) getLastComponent()).addStick(size);
    }

    public void addSickle(Vector2 size, boolean way) {
        ((Sword) getLastComponent()).addSickle(size, way);
    }

    public Object getLastComponent() {
        if (!allComponents.isEmpty())
            return allComponents.get(allComponents.size() - 1);
        else return null;
    }

    public void isConnected() {
        if (!connectedGates.isEmpty()) {

            for (int i = 0; i < states.size(); i++) {
                if (states.get(i).booleanValue()) {
                    isConnected = connectedGates.get(i).isConnected();

                } else {
                    isConnected = !connectedGates.get(i).isConnected();

                }


                if (isConnected == false)
                    break;


            }


        } else {
            isConnected = true;
        }


    }

    public void setButton() {
        isConnected();

        if (!allComponents.isEmpty()) {
            if (isConnected && allComponents.get(0) instanceof Circle1 && ((Circle1) allComponents.get(0)).lifespan == 1) {
                ((Circle1) allComponents.get(0)).button.setOnAction(e -> {
                    ((Sword) allComponents.get(allComponents.size() - 1)).isOnAction = true;
                    isActive = true;
                    isGateBusy = true;
                });

            } else if (allComponents.get(0) instanceof Circle1) {
                ((Circle1) allComponents.get(0)).button.setOnAction(e -> {
                });
            }

        }
    }

    @Override
    public void updateGraphics() {
        setButton();

        for (int i = 0; i < allComponents.size(); i++) {

            ((GraphicalContent) allComponents.get(i)).updateGraphics();
        }


    }

    @Override
    public void updateCoordinates() {
        if (isActive) {
            calculateCollisionData();
            compareWithOthers();

        }

        for (int i = 0; i < allComponents.size(); i++) {
            ((GraphicalContent) allComponents.get(i)).updateCoordinates();
        }

    }

    @Override
    public void insertToPane(Pane pane) {
        for (int i = 0; i < allComponents.size(); i++) {

            ((GraphicalContent) allComponents.get(i)).insertToPane(pane);
        }
        calculateCollisionData();
    }

    @Override
    public Vector2 getEndPoint() {
        return ((HasEndPoint) allComponents.get(allComponents.size() - 1)).getEndPoint();
    }


    public void calculateCollisionData() {
        collisionData.clear();
        for (int i = 0; i < allComponents.size(); i++) {
            if (allComponents.get(i) instanceof CollisionData)
                ((CollisionData) allComponents.get(i)).calculateCollisionData(collisionData);
        }

    }


    public ArrayList<Vector2> getCollisionData() {
        return collisionData;
    }

    public void compareWithOthers() {
        ArrayList<Vector2> temp;

        if (parent.getPointsOfOthers(this) != null) {
            temp = parent.getPointsOfOthers(this);

            for (int i = 0; i < collisionData.size(); i++) {
                for (int k = 0; k < temp.size(); k++) {
                    if (Math.abs(temp.get(k).x - collisionData.get(i).x) < 1 && Math.abs(temp.get(k).y - collisionData.get(i).y) < 1) {
                        if (allComponents.get(allComponents.size() - 1) instanceof Sword)
                            ((Sword) allComponents.get(allComponents.size() - 1)).isOnAction = false;


                        if (parent.disappearAll()) {

                            switch (parent.type) {
                                case 'R':
                                    parent.levGen.getRandomLev();
                                    break;


                                default:
                                    for (int j = 0; j < levGen.system.length; j++) {
                                        if (levGen.system[j] == parent) {
                                            switch (j) {
                                                case 0:
                                                    levGen.calculateLevel1();
                                                    break;
                                                case 1:
                                                    levGen.calculateLevel2();
                                                    break;
                                                case 2:
                                                    levGen.calculateLevel3();
                                                    break;
                                                case 3:
                                                    levGen.calculateLevel4();
                                                    break;
                                                case 4:
                                                    levGen.calculateLevel5();
                                                    break;
                                                case 5:
                                                    levGen.calculateLevel6();
                                                    break;
                                            }


                                        }
                                    }
                                    break;

                            }
                        }

                    }
                }
            }


        }
    }

    @Override
    public boolean isDead() {
        if (isActive) {

            for (int i = 0; i < allComponents.size(); i++) {

                if (!(allComponents.get(i) instanceof Sword)) {
                    if (((Mortal) allComponents.get(i)).isDead())
                        allComponents.remove(i);
                } else {
                    if (((Mortal) ((Sword) allComponents.get(i)).components.get(0)).isDead())
                        allComponents.remove(i);
                }

            }

        }
        if (allComponents.isEmpty()) {

            for (int i = 0; i < connectedGates.size(); i++) {
                connectedGates.get(i).hooks.remove(this);
            }
            return true;


        }

        if (!allComponents.isEmpty() && !(allComponents.get(allComponents.size() - 1) instanceof Sword)) {
            setOnDying();
        }


        return false;

    }

    @Override
    public void setOnDying() {

        isDying = true;
        isGateBusy = false;
    }

    @Override
    public void setAlpha(float alpha) {
        for (int i = 0; i < allComponents.size(); i++) {
            ((SetAlpha) allComponents.get(i)).setAlpha(alpha);
        }
    }

    @Override
    public void setText(String text) {
        if (allComponents.get(0) instanceof Circle1)
            ((SetText) allComponents.get(0)).setText(text);

    }


}