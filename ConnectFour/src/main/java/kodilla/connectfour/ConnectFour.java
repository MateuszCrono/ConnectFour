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

    // Background ze zdjeciami, niewykorzystany
//    private Image imageBack = new Image("file:src/main/resources/connect4-background.png");
//    private Image yellowDisc = new Image("file:src/main/resources/yellowDisc.png");
//    private Image greenDisc = new Image("file:src/main/resources/greenDisc.png");
//    private  FlowPane discs = new FlowPane(Orientation.HORIZONTAL);

    private static final int TILE_SIZE = 60;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    private boolean firstPlayerMove = true;


    private Disc[][] grid = new Disc[COLUMNS][ROWS];

    private Parent createGame() {

        Pane root = new Pane();

        Shape gridShape = createGameBoard();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());

        return root;
    }

    private Shape createGameBoard() {
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

        for (int y = 0; y < ROWS; y++) {
            shape = getShape(shape, y, COLUMNS, TILE_SIZE);
        }
        shape.setFill(Color.GREEN);

        return shape;
    }

    static Shape getShape(Shape shape, int y, int columns, int tileSize) {
        for (int x = 0; x < columns; x++) {
            Circle circle = new Circle(tileSize / 2);
            circle.setCenterX(tileSize / 2);
            circle.setCenterY(tileSize / 2);
            circle.setTranslateX(x * (tileSize) + tileSize / 2);
            circle.setTranslateY(y * (tileSize) + tileSize / 2);

            shape = shape.subtract(shape, circle);
        }
        return shape;
    }

    private List<Rectangle> makeColumns() {
        List<Rectangle> columnList = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rectangle = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
            rectangle.setTranslateX(x * (TILE_SIZE) + TILE_SIZE / 2);
            rectangle.setFill(Color.TRANSPARENT);


            rectangle.setOnMouseEntered(p -> rectangle.setFill(Color.rgb(150, 150, 0, 0.4)));
            rectangle.setOnMouseExited(p -> rectangle.setFill(Color.TRANSPARENT));
//            rectangle.setOnMouseClicked(p -> playDisc(new Disc()));

            columnList.add(rectangle);
        }
        return columnList;
    }

    private static class Disc extends Circle {
        private final boolean firstPlayerMove;

        public Disc(boolean firstPlayerMove) {
            super(TILE_SIZE / 2, firstPlayerMove ? Color.BLUE : Color.RED);
            this.firstPlayerMove = firstPlayerMove;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
    }

//    private void PlayDisc(Disc disc, int column) {
//        int row = ROWS - 1;
//        do {
//            if (!Do)
//        }
//    }

//    private Disc (int column, int row) {
//        if (column <= 0 || column >= COLUMNS || row <= 0 || row >= ROWS {
//            return null;
//            else if () {
//
//            }
//else {
//
//            }
//        }
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Connect Four");
        primaryStage.setScene(new Scene(createGame()));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}