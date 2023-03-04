package kodilla.connectfour;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static kodilla.connectfour.ConnectFour.*;

public class GameLogic {

    private boolean firstPlayerMove = true;
    private Disc[][] grid = new Disc[COLUMNS][ROWS];

    public Pane getDiscRoot() {
        return discRoot;
    }

    private Pane discRoot = new Pane();

    static class Disc extends Circle {
        private final boolean firstPlayer;

        public Disc(boolean firstPlayer) {
            super(TILE_SIZE / 2, firstPlayer ? Color.GREEN : Color.YELLOW);
            this.firstPlayer = firstPlayer;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
    }

    void playDisc(Disc disc, int column) {
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
                if (sequence >= 4) {
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

    public boolean isFirstPlayerMove() {
        return firstPlayerMove;
    }
}
