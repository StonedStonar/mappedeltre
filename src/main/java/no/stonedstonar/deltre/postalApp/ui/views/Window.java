package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.scene.Scene;
import no.stonedstonar.deltre.postalApp.ui.controllers.Controller;

/**
 * A general class that houses methods that all windows should have.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface Window {

    /**
     * Gets the controller of the window.
     */
    Controller getController();

    /**
     * Gets the scene that the window has.
     * @return a scene that this window is loading and making GUI elements for.
     */
    Scene getScene();

    /**
     * Gets the name of the FXML file so the main app can load it.
     * @return a FXML file name that can be used to load the scene.
     */
    String getFXMLName();

    /**
     * Gets the title of the window so it can be displayed in the far left corner.
     * @return the title you want displayed in the far left of the window.
     */
    String getTitleName();
}
