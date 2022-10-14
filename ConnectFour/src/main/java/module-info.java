module kodilla.connectfour {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens kodilla.connectfour to javafx.fxml;
    exports kodilla.connectfour;
}