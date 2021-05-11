package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import no.stonedstonar.deltre.postalApp.model.PostalFacade;
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


    /**
     * Makes an instance of the main window.
     * @param postalFacade the postal facade this program uses as its backbone.
     */
    public MainWindow(PostalFacade postalFacade){
        if (postalFacade == null){
            throw new IllegalArgumentException("The postal facade cannot be null.");
        }
        postalList = postalFacade.getPostalRegister();
        setUpObservablePostalInformation();
        TableView<PostalInformation> postalInformationTableView = makeTableView(postalFacade);
        mainController = new MainController(postalInformationTableView);
        fxmlName = "MainWindow";
        title = "Framside";
        mainWindow = this;
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
     * @param postalFacade the postal facade this program uses.
     * @return a table with all the information the user needs to use the program.
     */
    private TableView<PostalInformation> makeTableView(PostalFacade postalFacade){
        TableView<PostalInformation> tableView = new PostalInformationTableViewBuilder(postalFacade).addTableViewPostalCodeCol()
                .addTableViewPostalPlaceCol().addTableViewCountyCol()
                .addTableViewMunicipalityCol().build();
        tableView.setItems(observablePostalInformation);
        return tableView;
    }
}
