module ma.enset.javafx_basics {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ma.enset.javafx_basics to javafx.fxml;
    exports ma.enset.javafx_basics;
}