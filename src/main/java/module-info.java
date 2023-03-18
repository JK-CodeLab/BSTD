module com.jk.bstd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jk.bstd to javafx.fxml;
    exports com.jk.bstd;
}