module br.com.uniq.uniq {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;

    opens br.com.uniq to javafx.fxml;
    exports br.com.uniq;
    exports br.com.uniq.controllers;
    opens br.com.uniq.controllers to javafx.fxml;
}