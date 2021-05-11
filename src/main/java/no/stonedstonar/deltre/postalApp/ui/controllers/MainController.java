package no.stonedstonar.deltre.postalApp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 * Represents the main window's controller. Handles all the actions that the scene want to perform.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class MainController implements Controller{

    @FXML
    private MenuItem importFileMenu;
    @FXML
    private MenuItem exportFileMenu;
    @FXML
    private MenuItem quitMenu;
    @FXML
    private MenuItem aboutMenu;
    @FXML
    private TextField searchField;

    /**
     * Makes a instance of the maincontroller class.
     */
    public MainController(){

    }

    @Override
    public void updateContent() {

    }
}
