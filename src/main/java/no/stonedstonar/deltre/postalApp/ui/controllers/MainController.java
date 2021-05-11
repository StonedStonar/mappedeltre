package no.stonedstonar.deltre.postalApp.ui.controllers;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import no.stonedstonar.deltre.postalApp.model.PostalFacade;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.ui.views.MainWindow;
import no.stonedstonar.deltre.postalApp.ui.views.PostalApp;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the main window's controller. Handles all the actions that the scene want to perform.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class MainController implements Controller{

    @FXML
    private MenuItem quitMenu;
    @FXML
    private MenuItem aboutMenu;
    @FXML
    private TextField searchField;
    @FXML
    private GridPane mainGridPane;

    private TableView<PostalInformation> postalInformationTableView;

    private MainWindow mainWindow;

    /**
     * Makes a instance of the main controller class.
     */
    public MainController(TableView<PostalInformation> tableView, MainWindow mainWindow){
        if (tableView == null){
            throw new IllegalArgumentException("The tableview cannot be null or empty.");
        }
        postalInformationTableView = tableView;
        this.mainWindow = mainWindow;
    }

    @Override
    public void updateContent() {
        if (mainGridPane.getChildren().size() == 1){
            mainGridPane.add(postalInformationTableView, 0, 1);
            setButtonActions();
        }
    }

    /**
     * Sets all the actions of the buttons and the fields.
     */
    private void setButtonActions(){
        PostalFacade postalFacade = PostalApp.getApp().getPostalFacade();
        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isEmpty()){
               try {
                   Integer.parseInt(newValue);
                   sortByPostalCode(newValue, oldValue, searchField);
               }catch (NumberFormatException exception){
                   try{
                       Integer.parseInt(oldValue);
                       searchField.setText(oldValue);
                   }catch (NumberFormatException exception2){
                       searchForName(newValue, oldValue);
                   }
               }
            }else {
                mainWindow.updateObservablePostalInformation();
            }
            if (postalInformationTableView.getItems().size() == 0){
                postalInformationTableView.setPlaceholder(new Label("Ingen resultater funnet med søkeord \"" + newValue + "\"" ));
            }
        });
    }

    /**
     *
     * @param searchWord
     */
    private void searchForName(String searchWord, String oldValue){
        if (oldValue.length() > searchWord.length()){
            mainWindow.updateObservablePostalInformation();
        }
        ObservableList<PostalInformation> obs = postalInformationTableView.getItems();
        int start = 0;
        int stop = searchWord.length();
        List<PostalInformation> newObs = obs.stream().filter(postalInformation -> {
            boolean valid = false;
            if (postalInformation.getNameOfPlace().length() >= stop){
                String subString = getSubString(postalInformation.getNameOfPlace(), start, stop);
                System.out.println(subString);
                if (subString.equals(searchWord.toLowerCase())){
                    valid = true;
                }
            }

            return valid;
        }).collect(Collectors.toList());
        obs.setAll(newObs);
    }

    /**
     *
     * @param newValue
     * @param oldValue
     * @param textField
     */
    private void sortByPostalCode(String newValue, String oldValue, TextField textField){
        if (oldValue.length() > newValue.length()){
            mainWindow.updateObservablePostalInformation();
        }
        int start = 0;
        int stop = newValue.length();
        ObservableList<PostalInformation> obs = postalInformationTableView.getItems();
        List<PostalInformation> newObs = obs.stream().filter(postalInformation -> {
            boolean valid = false;
            long postC = postalInformation.getPostalCodeOfPlace();
            String postCode = Long.toString(postC);
            if (postCode.length() >= stop){
                String subString = getSubString(postCode, start, stop);
                if (subString.equals(newValue)){
                    valid = true;
                }
            }
            return valid;
        }).collect(Collectors.toList());
        obs.setAll(newObs);
    }

    private String getSubString(String word, int start, int stop){
        return word.substring(start, stop).toLowerCase();
    }
}
