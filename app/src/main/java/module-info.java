module br.com.uniq.uniq {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;

    opens br.com.uniq to javafx.fxml;
    exports br.com.uniq;
    exports br.com.uniq.controllers;
    opens br.com.uniq.controllers to javafx.fxml;
}