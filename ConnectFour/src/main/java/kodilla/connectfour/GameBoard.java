package kodilla.connectfour;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private static final int TILE_SIZE = 80;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

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
}

