package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import no.stonedstonar.deltre.postalApp.model.PostalFacade;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.ui.controllers.Controller;
import no.stonedstonar.deltre.postalApp.ui.controllers.MainController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

    private String title;


    /**
     * Makes an instance of the main window.
     * @param postalFacade the postal facade this program uses as its backbone.
     */
    public MainWindow(PostalFacade postalFacade){
        setUpObservablePostalInformation(postalFacade);
        TableView<PostalInformation> postalInformationTableView = makeTableView(postalFacade);
        mainController = new MainController(postalInformationTableView);
        fxmlName = "MainWindow";
        title = "Framside";
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

    /**
     * Sets up the observablePostalInformation list.
     * @param postalFacade the postal facade this program uses as its backbone.
     */
    private void setUpObservablePostalInformation(PostalFacade postalFacade){
        ArrayList<PostalInformation> list = (ArrayList<PostalInformation>) postalFacade.getPostalRegister();
        observablePostalInformation = FXCollections.observableArrayList(list);
    }

    /**
     *
     * @param postalFacade
     */
    public void updateObservablePostalInformation(PostalFacade postalFacade){

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
