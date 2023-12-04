module ds.datastructprjc {
    requires javafx.controls;
    requires javafx.fxml;


    opens ds.datastructprjc to javafx.fxml;
    exports ds.datastructprjc;
}