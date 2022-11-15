module com.group38.prisonbreak {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.group38.prisonbreak to javafx.fxml;
    exports com.group38.prisonbreak;
}