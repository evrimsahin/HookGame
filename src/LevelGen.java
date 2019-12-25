import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class LevelGen {

    public Pane levelPane;
    public HookSystem[] system;
    BorderPane rootPane;
    ArrayList<HookSystem> otherSystems;

    public LevelGen(BorderPane bpane) {

        otherSystems = new ArrayList<HookSystem>();
        this.rootPane = bpane;
        levelPane = new Pane();
        system = new HookSystem[6];
        calculateLevel1();
        calculateLevel2();
        calculateLevel3();
        calculateLevel4();
        calculateLevel5();
        calculateMenu();
    }

    public void calculateLevel1() {
        setRegularButtons();
        system[0] = new HookSystem();
        system[0].setSystemList(otherSystems);
        system[0].setLevelGen(this);
        system[0].addHook();
        system[0].addCircle(new Vector2(700, 400), 28);
        system[0].addLine(new Vector2(100, 400));
        system[0].addLine(new Vector2(100, 100));
        system[0].addLine(new Vector2(120, 100));
        system[0].addGrasp(new Vector2(10, 50));
        system[0].addStick(new Vector2(300, 10));
        system[0].addSickle(new Vector2(30, 10), true);
        system[0].addStick(new Vector2(150, 10));

        system[0].addHook();
        system[0].addCircle(new Vector2(460, 500), 28);
        system[0].addLine(new Vector2(460, 350));
        system[0].addGrasp(new Vector2(10, 50));
        system[0].addStick(new Vector2(250, 10));

        system[0].updateCoordinates();
        system[0].updateGraphics();


        setOtherSystems();
    }

    public void calculateLevel2() {

        setRegularButtons();
        system[1] = new HookSystem();
        system[1].setSystemList(otherSystems);
        system[1].setLevelGen(this);
        system[1].addHook();
        ;
        system[1].addCircle(new Vector2(700, 530), 28);
        system[1].addLine(new Vector2(700, 300));
        system[1].addLine(new Vector2(600, 300));
        system[1].addGrasp(new Vector2(10, 50));
        system[1].addStick(new Vector2(200, 10));
        system[1].addSickle(new Vector2(30, 10), false);
        system[1].addStick(new Vector2(100, 10));

        system[1].addHook();
        system[1].addCircle(new Vector2(360, 530), 28);
        system[1].addLine(new Vector2(360, 450));
        system[1].addGrasp(new Vector2(10, 50));
        system[1].addStick(new Vector2(150, 10));

        system[1].addHook();
        system[1].addCircle(new Vector2(240, 530), 28);
        system[1].addLine(new Vector2(240, 450));
        system[1].addGrasp(new Vector2(10, 50));
        system[1].addStick(new Vector2(110, 10));
        system[1].addSickle(new Vector2(30, 10), true);
        system[1].addStick(new Vector2(90, 10));
        system[1].addSickle(new Vector2(30, 10), false);
        system[1].addStick(new Vector2(60, 10));

        system[1].addHook();
        system[1].addCircle(new Vector2(100, 530), 28);
        system[1].addLine(new Vector2(100, 150));
        system[1].addLine(new Vector2(120, 150));
        system[1].addGrasp(new Vector2(10, 50));
        system[1].addStick(new Vector2(120, 10));

        setOtherSystems();

    }

    public void calculateLevel3() {
        setRegularButtons();
        system[2] = new HookSystem();
        system[2].setSystemList(otherSystems);
        system[2].setLevelGen(this);
        system[2].addHook();
        system[2].addCircle(new Vector2(300, 530), 28);
        system[2].addLine(new Vector2(300, 100));
        system[2].addLine(new Vector2(350, 100));
        system[2].addGrasp(new Vector2(10, 50));
        system[2].addStick(new Vector2(180, 10));
        system[2].addSickle(new Vector2(30, 10), true);
        system[2].addStick(new Vector2(130, 10));

        system[2].addHook();
        system[2].addCircle(new Vector2(200, 480), 28);
        system[2].addLine(new Vector2(360, 480));
        system[2].addLine(new Vector2(360, 220));
        system[2].addLine(new Vector2(400, 220));
        system[2].addGrasp(new Vector2(10, 50));
        system[2].addStick(new Vector2(170, 10));

        system[2].addHook();
        system[2].addCircle(new Vector2(100, 430), 28);
        system[2].addLine(new Vector2(570, 430));
        system[2].addLine(new Vector2(570, 400));
        system[2].addGrasp(new Vector2(10, 50));
        system[2].addStick(new Vector2(140, 10));
        system[2].addSickle(new Vector2(30, 10), false);
        system[2].addStick(new Vector2(100, 10));


        setOtherSystems();

    }


    public void calculateLevel4() {
        setRegularButtons();
        system[3] = new HookSystem();
        system[3].setSystemList(otherSystems);
        system[3].setLevelGen(this);
        system[3].addHook();
        system[3].addCircle(new Vector2(300, 530), 28);
        system[3].addLine(new Vector2(300, 457.5f));
        system[3].addGate(28, true);
        system[3].addLine(new Vector2(300, 100));
        system[3].addLine(new Vector2(350, 100));
        system[3].addGrasp(new Vector2(10, 50));
        system[3].addStick(new Vector2(180, 10));
        system[3].addSickle(new Vector2(30, 10), true);
        system[3].addStick(new Vector2(130, 10));

        system[3].addHook();
        system[3].addCircle(new Vector2(100, 430.5f), 28);
        system[3].addLine(new Vector2(272, 430.5f));
        system[3].connectToGate();
        system[3].addLine(new Vector2(570, 430));
        system[3].addLine(new Vector2(570, 400));
        system[3].addGrasp(new Vector2(10, 50));
        system[3].addStick(new Vector2(300, 10));


        setOtherSystems();
    }


    public void calculateLevel5() {
        setRegularButtons();

        system[4] = new HookSystem();
        system[4].setSystemList(otherSystems);
        system[4].setLevelGen(this);
        system[4].addHook();
        system[4].addCircle(new Vector2(600, 500), 28);
        system[4].addLine(new Vector2(600, 190));
        system[4].addLine(new Vector2(550, 190));
        system[4].addGrasp(new Vector2(10, 50));
        system[4].addStick(new Vector2(100, 10));

        system[4].addHook();
        system[4].addCircle(new Vector2(300, 500), 28);
        system[4].addLine(new Vector2(300, 428));
        system[4].addGate(28, false);
        system[4].addLine(new Vector2(300, 328));
        system[4].addGate(28, true);
        system[4].addLine(new Vector2(300, 100));
        system[4].addLine(new Vector2(350, 100));
        system[4].addGrasp(new Vector2(10, 50));
        system[4].addStick(new Vector2(70, 10));
        system[4].addSickle(new Vector2(30, 10), true);
        system[4].addStick(new Vector2(200, 10));

        system[4].addHook();
        system[4].addCircle(new Vector2(100, 300), 28);
        system[4].addLine(new Vector2(272, 300));
        system[4].connectToGate();
        system[4].addLine(new Vector2(457, 300));
        system[4].addLine(new Vector2(457, 280));
        system[4].addGrasp(new Vector2(10, 50));
        system[4].addStick(new Vector2(50, 10));
        system[4].addSickle(new Vector2(30, 10), true);
        system[4].addStick(new Vector2(70, 10));

        system[4].addHook();
        system[4].addCircle(new Vector2(100, 400), 28);
        system[4].addLine(new Vector2(272, 400));
        system[4].connectToGate();
        system[4].addLine(new Vector2(680, 400));
        system[4].addLine(new Vector2(680, 380));
        system[4].addGrasp(new Vector2(10, 50));
        system[4].addStick(new Vector2(240, 10));
        system[4].addSickle(new Vector2(30, 10), false);
        system[4].addStick(new Vector2(40, 10));

        setOtherSystems();
//		Rectangle white = new Rectangle();
//		white.setX(0);
//		white.setY(0);
//		white.setWidth(5000);
//		white.setHeight(5000);
//		white.setFill(Color.AZURE);
//		panes[4].getChildren().add(white);
    }


    public void calculateLevel6() {
        setRegularButtons();
        system[5] = new HookSystem();
        system[5].setSystemList(otherSystems);
        system[5].setLevelGen(this);
        system[5].addHook();
        system[5].addCircle(new Vector2(100, 550), 28);
        system[5].addLine(new Vector2(150, 500));
        system[5].addGate(25, false);
        system[5].addLine(new Vector2(205, 445));
        system[5].addGate(20, true);
        system[5].addLine(new Vector2(250, 400));
        system[5].addGate(25, false);
        system[5].addLine(new Vector2(300, 350));
        system[5].addGrasp(new Vector2(10, 70));
        system[5].addStick(new Vector2(80, 10));
        system[5].addSickle(new Vector2(40, 10), false);
        system[5].addStick(new Vector2(80, 10));
        system[5].addSickle(new Vector2(25, 10), true);
        system[5].addStick(new Vector2(80, 10));
        system[5].addSickle(new Vector2(45, 10), false);

        system[5].addHook();
        system[5].addCircle(new Vector2(300, 500), 28);
        system[5].addLine(new Vector2(240, 450));
        system[5].connectToGate();
        system[5].addLine(new Vector2(160, 370));
        system[5].addLine(new Vector2(211, 327));
        system[5].connectToGate();
        system[5].addLine(new Vector2(414, 514));
        system[5].addGate(20, false);
        system[5].addLine(new Vector2(489, 577));
        system[5].addLine(new Vector2(671, 286));
        system[5].addLine(new Vector2(586, 224));
        system[5].addGrasp(new Vector2(10, 70));
        system[5].addStick(new Vector2(110, 10));

        system[5].addHook();
        system[5].addCircle(new Vector2(514, 331), 40);
        system[5].addLine(new Vector2(459, 485));
        system[5].connectToGate();
        system[5].addLine(new Vector2(302, 581));
        system[5].addLine(new Vector2(196, 509));
        system[5].connectToGate();
        system[5].addLine(new Vector2(29, 313));
        system[5].addLine(new Vector2(86, 259));
        system[5].addGate(20, false);
        system[5].addLine(new Vector2(216, 146));
        system[5].addLine(new Vector2(321, 217));

        system[5].addGrasp(new Vector2(10, 70));
        system[5].addStick(new Vector2(100, 10));


        system[5].addHook();
        system[5].addCircle(new Vector2(249, 262), 15);
        system[5].addLine(new Vector2(154, 308));
        system[5].connectToGate();
        system[5].addLine(new Vector2(32, 132));
        system[5].addLine(new Vector2(382, 40));
        system[5].addLine(new Vector2(428, 43));
        system[5].addGrasp(new Vector2(10, 70));
        system[5].addStick(new Vector2(180, 10));

        setOtherSystems();
    }


    public void calculateMenu() {

        otherSystems.clear();
        HookSystem start = new HookSystem();
        start.setSystemList(otherSystems);
        start.setLevelGen(this);
        start.addHook();
        start.addCircle(new Vector2(520, 200), 40);
        start.addLine(new Vector2(620, 100));
        start.addGrasp(new Vector2(10, 50));
        start.addStick(new Vector2(250, 10));
        start.addSickle(new Vector2(30, 10), false);
        start.updateCoordinates();
        start.updateGraphics();
        start.setType('L');
        start.setText(" START");

        HookSystem random = new HookSystem();
        random.setSystemList(otherSystems);
        random.setLevelGen(this);
        random.addHook();
        random.addCircle(new Vector2(280, 200), 40);
        random.addLine(new Vector2(180, 100));
        random.addGrasp(new Vector2(10, 50));
        random.addStick(new Vector2(250, 10));
        random.addSickle(new Vector2(30, 10), true);
        random.updateCoordinates();
        random.updateGraphics();
        random.setType('R');
        random.setText(" LUCKY");


        HookSystem exit = new HookSystem();
        exit.setSystemList(otherSystems);
        exit.setLevelGen(this);
        exit.addHook();
        exit.addCircle(new Vector2(400, 400), 40);
        exit.addLine(new Vector2(400, 525));
        exit.addGrasp(new Vector2(10, 50));
        exit.addStick(new Vector2(80, 10));
        exit.updateCoordinates();
        exit.updateGraphics();
        exit.setType('E');
        exit.setText("   EXIT");


        setOtherSystems();


    }


    public void updateAll() {


        for (int i = 0; i < otherSystems.size(); i++) {
            otherSystems.get(i).updateCoordinates();
            if (otherSystems.size() > i)
                otherSystems.get(i).updateGraphics();

        }

    }


    public void goToNextLevel(HookSystem source) {
        for (int i = 0; i < system.length; i++) {
            if (system[i] == source) {

                switch (i + 1) {
                    case 1:
                        calculateLevel2();
                        break;
                    case 2:
                        calculateLevel3();
                        break;
                    case 3:
                        calculateLevel4();
                        break;
                    case 4:
                        calculateLevel5();
                        break;
                    case 5:
                        calculateLevel6();
                        break;
                    case 6:
                        calculateMenu();
                        break;

                }
            }
        }


    }


    public void setRegularButtons() {
        otherSystems.clear();
        HookSystem menuBut = new HookSystem();
        menuBut.addHook();
        menuBut.addCircle(new Vector2(65, 30), 22);
        menuBut.addLine(new Vector2(30, 30));
        menuBut.addGrasp(new Vector2(7, 30));
        menuBut.addStick(new Vector2(25, 7));
        menuBut.setLevelGen(this);
        menuBut.setType('M');
        menuBut.setSystemList(otherSystems);
        menuBut.insertToPane(levelPane);
        menuBut.setText("  M");

        HookSystem exit = new HookSystem();
        exit.addHook();
        exit.addCircle(new Vector2(745, 30), 22);
        exit.addLine(new Vector2(780, 30));
        exit.addGrasp(new Vector2(7, 30));
        exit.addStick(new Vector2(25, 7));
        exit.setLevelGen(this);
        exit.setType('E');
        exit.setSystemList(otherSystems);
        exit.insertToPane(levelPane);
        exit.setText("  E");
    }

    public void setOtherSystems() {
        levelPane.getChildren().clear();
        updateAll();
        for (int i = 0; i < otherSystems.size(); i++) {
            otherSystems.get(i).insertToPane(levelPane);
        }
        rootPane.setCenter(levelPane);


    }


    public void getRandomLev() {

        setRegularButtons();
        HookSystem random = new HookSystem();
        random.setSystemList(otherSystems);
        random.setLevelGen(this);
        random.addHook();
        random.addCircle(new Vector2(50 + (float) Math.random() * 300, 50 + (float) Math.random() * 200), 10 + (float) Math.random() * 25);
        random.addLine(new Vector2(200 + (float) Math.random() * 400, 50 + (float) Math.random() * 400));
        random.addGate(20 + (float) Math.random() * 15, (Math.random() > 0.5 ? true : false));

        random.addLine(new Vector2(50 + (float) Math.random() * 400, 50 + (float) Math.random() * 400));
        random.addGate(20 + (float) Math.random() * 15, (Math.random() > 0.5 ? true : false));
        random.addLine(new Vector2(600, 200));
        random.addGrasp(new Vector2(10, 80));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));
        random.addSickle(new Vector2(30 + (float) Math.random() * 20, 10), (Math.random() > 0.5 ? true : false));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));

        random.setLevelGen(this);
        random.addHook();
        random.addCircle(new Vector2(350 + (float) Math.random() * 300, 50 + (float) Math.random() * 200), 10 + (float) Math.random() * 25);
        random.addLine(new Vector2(200 + (float) Math.random() * 400, 50 + (float) Math.random() * 400));

        random.connectToGate();
        random.addLine(new Vector2(50 + (float) Math.random() * 400, 50 + (float) Math.random() * 400));
        random.addLine(new Vector2(300, 400));
        random.addGrasp(new Vector2(10, 80));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));
        random.addSickle(new Vector2(30 + (float) Math.random() * 20, 10), (Math.random() > 0.5 ? true : false));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));


        random.setLevelGen(this);
        random.addHook();
        random.addCircle(new Vector2(50 + (float) Math.random() * 300, 250 + (float) Math.random() * 200), 10 + (float) Math.random() * 25);
        random.addLine(new Vector2(200 + (float) Math.random() * 400, 50 + (float) Math.random() * 400));
        random.addLine(new Vector2(300, 200));
        random.addGrasp(new Vector2(10, 80));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));
        random.addSickle(new Vector2(30 + (float) Math.random() * 20, 10), (Math.random() > 0.5 ? true : false));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));


        random.setLevelGen(this);
        random.addHook();
        random.addCircle(new Vector2(350 + (float) Math.random() * 300, 250 + (float) Math.random() * 200), 10 + (float) Math.random() * 25);
        random.addLine(new Vector2(200 + (float) Math.random() * 400, 50 + (float) Math.random() * 400));
        random.connectToGate();
        random.addLine(new Vector2(600, 400));
        random.addGrasp(new Vector2(10, 80));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));
        random.addSickle(new Vector2(30 + (float) Math.random() * 20, 10), (Math.random() > 0.5 ? true : false));
        random.addStick(new Vector2(20 + (float) Math.random() * 200, 10));
        random.setType('R');

        setOtherSystems();
        for (int i = 0; i < random.gates.size(); i++) {

            ((Gate) random.gates.get(i)).RC = (int) (Math.random() * 255);
            ((Gate) random.gates.get(i)).GC = (int) (Math.random() * 255);
            ((Gate) random.gates.get(i)).BC = (int) (Math.random() * 255);
            ((Gate) random.gates.get(i)).RL = (int) (Math.random() * 255);
            ((Gate) random.gates.get(i)).GL = (int) (Math.random() * 255);
            ((Gate) random.gates.get(i)).BL = (int) (Math.random() * 255);
        }
        for (int i = 0; i < random.hooks.size(); i++) {
            for (int k = 0; k < random.hooks.get(i).allComponents.size(); k++) {
                if (random.hooks.get(i).allComponents.get(k) instanceof Circle1) {
                    ((Circle1) random.hooks.get(i).allComponents.get(k)).R = (int) (Math.random() * 255);
                    ((Circle1) random.hooks.get(i).allComponents.get(k)).G = (int) (Math.random() * 255);
                    ((Circle1) random.hooks.get(i).allComponents.get(k)).B = (int) (Math.random() * 255);
                } else if (random.hooks.get(i).allComponents.get(k) instanceof Line1) {
                    ((Line1) random.hooks.get(i).allComponents.get(k)).R = (int) (Math.random() * 255);
                    ((Line1) random.hooks.get(i).allComponents.get(k)).G = (int) (Math.random() * 255);
                    ((Line1) random.hooks.get(i).allComponents.get(k)).B = (int) (Math.random() * 255);
                } else if (random.hooks.get(i).allComponents.get(k) instanceof Sword) {
                    ((Sword) random.hooks.get(i).allComponents.get(k)).setRandomColors();

                }

            }
        }


    }


}
