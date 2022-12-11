module com.group38.prisonbreak {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens com.group38.prisonbreak to javafx.fxml;
    opens com.group38.prisonbreak.controllers to javafx.fxml;

    exports com.group38.prisonbreak;
    exports com.group38.prisonbreak.controllers;
    exports com.group38.prisonbreak.utilities;
    opens com.group38.prisonbreak.utilities to javafx.fxml;
	exports com.group38.prisonbreak.entities;
	opens com.group38.prisonbreak.entities to javafx.fxml;
}