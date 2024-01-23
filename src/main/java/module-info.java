module com.example.searoutes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.almasb.fxgl.all;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    requires ch.qos.logback.classic;
    requires org.slf4j;
    requires java.desktop;

    opens com.example.searoutes to javafx.fxml;
    exports com.example.searoutes;
    exports User;
    opens User to javafx.fxml;
    exports Boats;
    opens Boats to javafx.fxml;
    exports Orders;
    opens Orders to javafx.fxml;
}