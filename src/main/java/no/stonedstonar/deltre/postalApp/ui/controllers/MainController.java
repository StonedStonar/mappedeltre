package no.stonedstonar.deltre.postalApp.ui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;

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
    @FXML
    private GridPane mainGridPane;

    private TableView<PostalInformation> postalInformationTableView;

    /**
     * Makes a instance of the maincontroller class.
     */
    public MainController(TableView<PostalInformation> tableView){
        if (tableView == null){
            throw new IllegalArgumentException("The tableview cannot be null or empty.");
        }
        postalInformationTableView = tableView;
    }

    @Override
    public void updateContent() {
        if (mainGridPane.getChildren().size() == 1){
            mainGridPane.add(postalInformationTableView, 0, 1);
        }
    }
}
