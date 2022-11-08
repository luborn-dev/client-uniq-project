module br.com.uniq.uniq {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;

    opens br.com.uniq.uniq to javafx.fxml;
    exports br.com.uniq.uniq;
    exports br.com.uniq.uniq.controllers;
    opens br.com.uniq.uniq.controllers to javafx.fxml;
}