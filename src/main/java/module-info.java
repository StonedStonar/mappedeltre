module no.stonedstonar.deltre.postalApp{
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.logging;

    opens no.stonedstonar.deltre.postalApp.ui.views to javafx.fxml, javafx.graphics;
    opens no.stonedstonar.deltre.postalApp.ui.controllers to javafx.fxml, javafx.graphics;
    opens no.stonedstonar.deltre.postalApp.model;
    opens no.stonedstonar.deltre.postalApp.model.exceptions;

    exports no.stonedstonar.deltre.postalApp.ui.views;
    exports no.stonedstonar.deltre.postalApp.model.exceptions;
    exports no.stonedstonar.deltre.postalApp.model;
}