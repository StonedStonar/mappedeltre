package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.ui.controllers.Controller;
import no.stonedstonar.deltre.postalApp.ui.controllers.MainController;

/**
 * A class that represents the main scene and holds it's controller.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class MainWindow implements Window{

    private MainController mainController;

    private Scene scene;

    private String fxmlName;

    private ObservableList<PostalInformation> observablePostalInformation;


    /**
     * Makes an instance of the main window.
     */
    public MainWindow(){
        mainController = new MainController();
        fxmlName = "MainWindow";
    }

    @Override
    public Controller getController() {
        return mainController;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public String getFXMLName() {
        return fxmlName;
    }
}
