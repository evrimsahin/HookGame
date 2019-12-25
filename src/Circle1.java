import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Circle1 extends GraphicalContent implements Mortal, setOnDying, SetAlpha, SetText {
    // Location vector and radius of circle
    public Circle cir;
    public Vector2 location;
    public float radius;
    Button button;
    boolean isConnected;
    boolean isDying;
    Pane pane;
    public float radiusConst;
    public float lifespan;
    public Text title;
    int R, G, B;


    public Circle1(Vector2 location, float radius) {
        R = 40;
        G = 40;
        B = 40;
        title = new Text();
        title.setMouseTransparent(true);

        this.location = location;
        this.radius = radius;

        isConnected = false;
        isDying = false;

        lifespan = 0;
        radiusConst = radius / 6;

        button = new Button();
        button.setLayoutX(location.x - radius);
        button.setLayoutY(location.y - radius);

        button.setShape(new Circle(radius));
        button.setMinSize(radius * 2, radius * 2);
        button.setMaxSize(radius * 2, radius * 2);
        button.setStyle("-fx-background-color: transparent;");

        cir = new Circle();

        updateGraphics();
        updateCoordinates();
    }


    public void updateConnected(boolean isConnected) {
        this.isConnected = isConnected;


    }

    @Override
    public void updateGraphics() {
        cir.setFill(Color.rgb(R, G, B, lifespan));
        cir.setStrokeWidth(0);
        title.setFont(Font.font("Bauhaus 93", 20));
        title.setFill(Color.rgb(204, 255, 204, Math.pow(lifespan, 10)));
        title.setMouseTransparent(true);

        title.setStroke(Color.rgb(102, 102, 153, Math.pow(lifespan, 10)));

        title.setStrokeWidth(0.5);
    }

    @Override
    public void updateCoordinates() {
        cir.setCenterX(location.x);
        cir.setCenterY(location.y);
        cir.setRadius(radius);

        title.setStrokeType(StrokeType.OUTSIDE);
        title.setX(location.x - radiusConst * 5);
        title.setY(location.y + radiusConst * 1);


    }

    @Override
    public void insertToPane(Pane pane) {

        if (!pane.getChildren().contains(cir))
            pane.getChildren().add(cir);


        if (!pane.getChildren().contains(button))
            pane.getChildren().add(button);

        if (!pane.getChildren().contains(title))
            pane.getChildren().add(title);

        this.pane = pane;


    }


    @Override
    public void setOnDying() {
        isDying = true;

    }


    @Override
    public boolean isDead() {
        if (isDying) {
            if (cir.getRadius() > radiusConst) {
                radius -= 0.5;
                lifespan -= 0.020;
                if (lifespan < 0)
                    lifespan = 0;
            } else {

                pane.getChildren().remove(cir);
                return true;
            }

        }


        return false;
    }


    @Override
    public void setAlpha(float alpha) {
        lifespan = alpha;

    }


    @Override
    public void setText(String text) {
        this.title.setText(text);


    }


}