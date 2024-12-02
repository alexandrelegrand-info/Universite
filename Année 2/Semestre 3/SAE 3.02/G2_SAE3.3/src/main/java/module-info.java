module sae {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires opencsv;
    requires java.sql;
    requires javafx.base;

    opens fr.univlille.g2.sae302 to javafx.graphics;
    opens fr.univlille.g2.sae302.controller to javafx.fxml;
    opens fr.univlille.g2.sae302.utils;

}
