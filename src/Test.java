import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Test extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox hbox = new HBox();
        BorderPane borderPane = new BorderPane();
        LevelGen levelgen = new LevelGen(borderPane);


        borderPane.setStyle("-fx-background: #FFFFdc");

        borderPane.setBottom(hbox);


        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(15), e -> levelgen.updateAll()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();


        Scene mainScene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                if (arg0.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    System.out.println("x = " + arg0.getSceneX() + " y = " + arg0.getSceneY());

                }
            }
        });

    }


}


