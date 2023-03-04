package kodilla.connectfour;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectFour extends Application {

    static final int TILE_SIZE = 60;
    static final int COLUMNS = 7;
    static final int ROWS = 7;

    private boolean firstPlayerMove = true;
    private Pane discRoot = new Pane();
    private Disc[][] grid = new Disc[COLUMNS][ROWS];

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
        root.getChildren().add(discRoot);

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
            rectangle.setOnMouseClicked(p -> playDisc(new Disc(firstPlayerMove), column));

            columnList.add(rectangle);
        }
        return columnList;
    }

    // Game Logic
    private static class Disc extends Circle {
        private final boolean firstPlayer;

        public Disc(boolean firstPlayer) {
            super(TILE_SIZE / 2, firstPlayer ? Color.GREEN : Color.YELLOW);
            this.firstPlayer = firstPlayer;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
    }

    private void playDisc(Disc disc, int column) {
        int row = ROWS - 1;
        do {
            if (!getDisc(column, row).isPresent())
                break;
            row--;
        } while (row >= 0);
        if (row < 0)
            return;

        grid[column][row] = disc;
        discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);

        final int currentRow = row;

        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.2), disc);
        animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
        animation.setOnFinished(e -> {
            if (gameEnded(column, currentRow)) {
                gameOver();
            }
            firstPlayerMove = !firstPlayerMove;
        });
        animation.play();
    }

    private boolean gameEnded(int column, int row) {
        List<Point2D> verticalVictory = IntStream.rangeClosed(row - 3, row + 3)
                .mapToObj(r -> new Point2D(column, r))
                .collect(Collectors.toList());

        List<Point2D> horizontalVictory = IntStream.rangeClosed(column - 3, column + 3)
                .mapToObj(c -> new Point2D(c, row))
                .collect(Collectors.toList());

        Point2D leftUp = new Point2D(column - 3, row - 3);
        List<Point2D> leftAscendingVictory = IntStream.rangeClosed(0, 6)
                .mapToObj(l -> leftUp.add(l, l))
                .collect(Collectors.toList());

        Point2D leftDown = new Point2D(column - 3, row + 3);
        List<Point2D> leftdescendingVictory = IntStream.rangeClosed(0, 6)
                .mapToObj(l -> leftDown.add(l, -l))
                .collect(Collectors.toList());

        return checkVictoryCondition(verticalVictory) || checkVictoryCondition(horizontalVictory)
                || checkVictoryCondition(leftAscendingVictory) || checkVictoryCondition(leftdescendingVictory);
    }

    private boolean checkVictoryCondition(List<Point2D> points) {
        int sequence = 0;
        for (Point2D p : points) {
            int column = (int) p.getX();
            int row = (int) p.getY();

            Disc disc = getDisc(column, row).orElse(new Disc(!firstPlayerMove));
            if (disc.firstPlayer == firstPlayerMove) {
                sequence++;
                if (sequence == 4) {
                    return true;
                }
            } else {
                sequence = 0;
            }
        }
        return false;
    }

    private void gameOver() {
        System.out.println("Game is Finished");
        System.out.println((firstPlayerMove ? " Green" : "Yellow") + " Player Won");
    }
    private Optional<Disc> getDisc(int column, int row) {
        if ((column < 0 || column >= COLUMNS)
                || (row < 0 || row >= ROWS))
            return Optional.empty();
        return Optional.ofNullable(grid[column][row]);
    }
}