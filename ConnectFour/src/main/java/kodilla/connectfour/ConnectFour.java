package kodilla.connectfour;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ConnectFour extends Application {

    static final int TILE_SIZE = 120;
    static final int COLUMNS = 6;
    static final int ROWS = 7;
    GameLogic gameLogic = new GameLogic();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Connect Four");
        primaryStage.setScene(new Scene(createGame()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Game Setup
    private Parent createGame() {

        Pane root = new Pane();
        root.getChildren().add(gameLogic.getDiscRoot());

        Shape gridShape = createGameBoard();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());

        return root;
    }

    private Shape createGameBoard() {
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
                circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);

                shape = Shape.subtract(shape, circle);
            }
        }
        shape.setFill(Color.PURPLE);
        return shape;
    }

    private List<Rectangle> makeColumns() {
        List<Rectangle> columnList = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rectangle = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
            rectangle.setTranslateX(x * (TILE_SIZE) + TILE_SIZE / 2);
            rectangle.setFill(Color.TRANSPARENT);

            final int column = x;
            rectangle.setOnMouseEntered(p -> rectangle.setFill(Color.rgb(150, 150, 0, 0.4)));
            rectangle.setOnMouseExited(p -> rectangle.setFill(Color.TRANSPARENT));
            rectangle.setOnMouseClicked(p -> gameLogic.playDisc(new GameLogic.Disc(gameLogic.isFirstPlayerMove()), column));

            columnList.add(rectangle);
        }
        return columnList;
    }

}