module com.labtasks.task1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.labtasks.task1 to javafx.fxml;
    opens com.labtasks.task2 to javafx.fxml;
    exports com.labtasks.task1;
    exports com.labtasks.task2;

}