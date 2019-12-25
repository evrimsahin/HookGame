import java.util.ArrayList;

import javafx.scene.layout.Pane;

public class HookSystem extends GraphicalContent implements Adder, Mortal, setOnDying, SetAlpha, SetText {
    ArrayList<Hook> hooks;
    ArrayList<Gate> gates;
    public boolean isDying;
    public ArrayList<HookSystem> otherSystems;
    LevelGen levGen;
    public boolean justCreated;
    public int waitTime;
    public int waitTimeForDying;
    public float lifespan;
    public float lifespanSpeed;
    public boolean selfSwitch;
    public char type;

    public HookSystem() {
        lifespan = 0;
        lifespanSpeed = 0.02f;
        waitTime = 2;
        waitTimeForDying = 10;
        justCreated = true;
        hooks = new ArrayList<Hook>();
        gates = new ArrayList<Gate>();
        isDying = false;
        selfSwitch = true;
    }

    public void addHook() {
        hooks.add(new Hook());
    }

    public void addGate(float radius, boolean isConnected) {
        Vector2 endPoint = getLastHook().getEndPoint();
        Vector2 dir = ((Line1) getLastHook().getLastComponent()).direction;
        gates.add(new Gate(endPoint, dir, radius, isConnected));
        getLastHook().addedGate = true;
        getLastHook().connectedGates.add(gates.get(gates.size() - 1));
        gates.get(gates.size() - 1).isFull[0] = true;
        gates.get(gates.size() - 1).hooks.add(getLastHook());

    }

    public void connectToGate() {
        Gate tempGate = getClosestGate();

        Vector2 start;

        Vector2 tempLineEnd = getLastHook().getEndPoint().getCopy();

        float magnitudeToPoint3 = Vector2.substract(tempLineEnd, tempGate.points[3]).magnitude();
        float magnitudeToPoint1 = Vector2.substract(tempLineEnd, tempGate.points[1]).magnitude();

        if (magnitudeToPoint3 < magnitudeToPoint1) {
            start = tempGate.points[3].getCopy();
            tempGate.isFull[3] = true;
        } else {
            start = tempGate.points[1].getCopy();
            tempGate.isFull[1] = true;
        }

        getLastHook().addLine(start.getCopy());
        getLastHook().connectedToGate = true;
        getLastHook().connectedGates.add(tempGate);
        tempGate.hooks.add(getLastHook());
    }


    public Gate getClosestGate() {
        Vector2 temp = getLastHook().getEndPoint();

        int recordIndex = 0;
        float recordMag = 90000;

        for (int i = 0; i < gates.size(); i++) {
            if (Vector2.substract(temp, gates.get(i).centerPoint).magnitude() < recordMag && gates.get(i).gotSpace()) {
                recordIndex = i;
                recordMag = Vector2.substract(temp, gates.get(i).centerPoint).magnitude();
            }
        }
        return gates.get(recordIndex);
    }

    public Hook getLastHook() {
        if (!hooks.isEmpty())
            return (Hook) hooks.get(hooks.size() - 1);
        return null;
    }

    @Override
    public void addLine(Vector2 location) {
        ((Adder) getLastHook()).addLine(location);

    }

    @Override
    public void addCircle(Vector2 location, float radius) {
        ((Adder) getLastHook()).addCircle(location, radius);

    }

    @Override
    public void addGrasp(Vector2 size) {
        ((Adder) getLastHook()).addGrasp(size);
        ;

    }

    @Override
    public void addSickle(Vector2 size, boolean way) {
        ((Adder) getLastHook()).addSickle(size, way);

    }

    @Override
    public void addStick(Vector2 size) {
        ((Adder) getLastHook()).addStick(size);

    }


    @Override
    public void updateGraphics() {
        for (int i = hooks.size() - 1; i >= 0; i--) {
            ((GraphicalContent) hooks.get(i)).updateGraphics();
        }

        for (int i = gates.size() - 1; i >= 0; i--) {
            ((GraphicalContent) gates.get(i)).updateGraphics();
        }

        if (justCreated) {
            if (waitTime > 0) {
                waitTime--;
            } else {
                lifespan += lifespanSpeed;
                if (lifespan > 1) {
                    lifespan = 1;
                    setAlpha(lifespan);
                    justCreated = false;
                } else {
                    setAlpha(lifespan);
                }


            }

        }


    }

    @Override
    public void updateCoordinates() {
        for (int i = hooks.size() - 1; i >= 0; i--) {
            ((GraphicalContent) hooks.get(i)).updateCoordinates();
        }

        for (int i = gates.size() - 1; i >= 0; i--) {
            ((GraphicalContent) gates.get(i)).updateCoordinates();
        }

        setOnDying();
        isDead();

    }

    @Override
    public void insertToPane(Pane pane) {
        for (int i = hooks.size() - 1; i >= 0; i--) {
            ((GraphicalContent) hooks.get(i)).insertToPane(pane);
        }

        for (int i = gates.size() - 1; i >= 0; i--) {
            ((GraphicalContent) gates.get(i)).insertToPane(pane);
        }

        for (int i = hooks.size() - 1; i >= 0; i--) {
            hooks.get(i).parent = this;
            hooks.get(i).levGen = this.levGen;
        }


    }


    public boolean isCollided(ArrayList<Vector2> first, ArrayList<Vector2> second) {
        for (int i = 0; i < first.size(); i++) {
            for (int k = 0; k < second.size(); k++) {
                if (Math.abs(first.get(i).x - second.get(k).x) < 80 && Math.abs(first.get(i).y - second.get(k).y) < 80) {

                    return true;
                }
            }
        }

        return false;
    }


    public ArrayList<Vector2> getPointsOfOthers(Hook mainHook) {
        ArrayList<Vector2> sumOfAll = new ArrayList<Vector2>();

        for (int i = 0; i < hooks.size(); i++) {

            if (hooks.get(i) != mainHook) {
                ArrayList<Vector2> temp = hooks.get(i).getCollisionData();
                for (int k = 0; k < temp.size(); k++) {
                    sumOfAll.add(temp.get(k).getCopy());
                }

            }

        }


        if (sumOfAll != null) ;
        return sumOfAll;


    }

    @Override
    public boolean isDead() {
        if (gates.isEmpty() && hooks.isEmpty()) {

            if (disappearAll()) {
                if (selfSwitch) {

                    switch (type) {
                        case 'M':
                            levGen.calculateMenu();
                            break;
                        case 'E':
                            System.exit(0);
                            break;
                        case 'L':
                            levGen.calculateLevel1();
                            break;
                        case 'R':
                            levGen.getRandomLev();
                            break;
                        default:
                            levGen.goToNextLevel(this);
                            break;
                    }


                    selfSwitch = false;
                }

                return true;
            }
        }

        for (int i = 0; i < hooks.size(); i++) {
            if (hooks.get(i).isDead())
                hooks.remove(i);
        }

        for (int i = 0; i < gates.size(); i++) {
            if (gates.get(i).isDead())
                gates.remove(i);
        }

        return false;

    }

    @Override
    public void setOnDying() {

        for (int i = 0; i < hooks.size(); i++) {
            if (((Mortal) hooks.get(i).getLastComponent()) != null) {
                if (((Mortal) hooks.get(i).getLastComponent()).isDead()) {
                    for (int k = 0; k < hooks.get(i).allComponents.size(); k++) {

                        if (hooks.get(i).allComponents.get(k) instanceof Sword)
                            ((setOnDying) ((Sword) hooks.get(i).allComponents.get(k)).components.get(0)).setOnDying();

                        ((setOnDying) hooks.get(i).allComponents.get(k)).setOnDying();
                    }
                }

            }

        }

        for (int i = 0; i < gates.size(); i++) {
            gates.get(i).setOnDying();
        }


    }


    public void setLevelGen(LevelGen levGen) {
        this.levGen = levGen;
    }

    @Override
    public void setAlpha(float alpha) {
        for (int i = 0; i < hooks.size(); i++) {
            hooks.get(i).setAlpha(alpha);
        }

        for (int i = 0; i < gates.size(); i++) {
            gates.get(i).setAlpha(alpha);
        }


    }

    public boolean disappearAll() {
        for (int i = 0; i < otherSystems.size(); i++) {
            if (otherSystems.get(i).waitTimeForDying > 0) {
                otherSystems.get(i).waitTimeForDying--;
            } else {

                otherSystems.get(i).lifespan -= otherSystems.get(i).lifespanSpeed;
                if (otherSystems.get(i).lifespan < 0) {
                    otherSystems.get(i).lifespan = 0;
                    otherSystems.get(i).setAlpha(lifespan);

                } else {
                    otherSystems.get(i).setAlpha(lifespan);
                }

            }
        }

        for (int i = 0; i < otherSystems.size(); i++) {
            if (otherSystems.get(i).lifespan != 0)
                return false;
        }

        return true;


    }

    public void setSystemList(ArrayList<HookSystem> otherSystems) {
        this.otherSystems = otherSystems;
        otherSystems.add(this);

    }

    public void setType(char type) {
        this.type = type;
    }

    @Override
    public void setText(String text) {
        for (int i = 0; i < hooks.size(); i++) {
            hooks.get(i).setText(text);
        }
    }

}
