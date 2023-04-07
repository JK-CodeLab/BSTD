module com.jk.bstd {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires json.simple;


    opens com.jk.bstd to javafx.fxml;
    exports com.jk.bstd;
    exports com.jk.bstd.entities;
    exports com.jk.bstd.ui;
}