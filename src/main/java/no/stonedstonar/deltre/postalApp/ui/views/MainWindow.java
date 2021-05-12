package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import no.stonedstonar.deltre.postalApp.model.PostalSystem;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.ui.controllers.Controller;
import no.stonedstonar.deltre.postalApp.ui.controllers.MainController;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the main scene and holds it's controller.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class MainWindow implements Window{

    private MainController mainController;

    private static volatile MainWindow mainWindow;

    private Scene scene;

    private String fxmlName;

    private ObservableList<PostalInformation> observablePostalInformation;

    private String title;

    private List<PostalInformation> postalList;

    private PostalSystem postalSystem;


    /**
     * Makes an instance of the main window.
     * @param postalSystem the postal system this screen should show.
     */
    public MainWindow(PostalSystem postalSystem){
        if (postalSystem == null){
            throw new IllegalArgumentException("The postal system cannot be null.");
        }
        postalList = postalSystem.getPostalRegister();
        setUpObservablePostalInformation();
        TableView<PostalInformation> postalInformationTableView = makeTableView(postalSystem);
        mainController = new MainController(postalInformationTableView);
        fxmlName = "MainWindow";
        title = "Framside";
        mainWindow = this;
        this.postalSystem = postalSystem;
    }

    /**
     * Gets the postal system that this window is holding.
     * @return a postal system that this window is holding.
     */
    public PostalSystem getPostalSystem(){
        return postalSystem;
    }

    /**
     * Gets the main window object.
     * @return the main window object.
     */
    public static MainWindow getMainWindow(){
        if (mainWindow == null){
            throw new IllegalArgumentException("The mainwindow object havnet been created yet.");
        }
        return mainWindow;
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

    @Override
    public String getTitleName() {
        return title;
    }

    @Override
    public void setScene(Scene scene) {
        if (scene != null){
            this.scene = scene;
        }else {
            throw new IllegalArgumentException("The scene cannot be null or empty.");
        }
    }

    /**
     * Sets up the observablePostalInformation list.
     */
    private void setUpObservablePostalInformation(){
        ArrayList<PostalInformation> list = (ArrayList<PostalInformation>) postalList;
        observablePostalInformation = FXCollections.observableArrayList(list);
    }

    /**
     * Updates the observable list with the information it needs.
     */
    public void updateObservablePostalInformation(){
        observablePostalInformation.setAll(postalList);
    }


    /**
     * Makes a tableview with all its contents.
     * @param postalSystem the postal system this program uses.
     * @return a table with all the information the user needs to use the program.
     */
    private TableView<PostalInformation> makeTableView(PostalSystem postalSystem){
        TableView<PostalInformation> tableView = new PostalInformationTableViewBuilder(postalSystem).addTableViewPostalCodeCol()
                .addTableViewPostalPlaceCol().addTableViewCountyCol()
                .addTableViewMunicipalityCol().build();
        tableView.setItems(observablePostalInformation);
        tableView.setPlaceholder(new Label("Vennligst importer en fil for å se dataene. Filen må være ANSI standard."));
        return tableView;
    }
}
