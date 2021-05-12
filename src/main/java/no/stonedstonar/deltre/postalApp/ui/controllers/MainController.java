package no.stonedstonar.deltre.postalApp.ui.controllers;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.ui.views.MainWindow;

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
    @FXML
    private MenuItem importMenu;

    private TableView<PostalInformation> postalInformationTableView;


    /**
     * Makes a instance of the main controller class.
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
            setButtonActions();
        }
    }

    /**
     * Sets all the actions of the buttons and the fields.
     */
    private void setButtonActions(){
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
                MainWindow.getMainWindow().updateObservablePostalInformation();
            }
            if (postalInformationTableView.getItems().size() == 0){
                postalInformationTableView.setPlaceholder(new Label("Ingen resultater funnet med søkeord \"" + newValue + "\"" ));
            }
        });
        searchField.setPromptText("Skriv inn et postnummer eller stedsnavn.");
        quitMenu.setOnAction(actionEvent -> {

        });
        aboutMenu.setOnAction(actionEvnet -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasjons boks - Om Postkode applikasjonen");
            alert.setHeaderText("Postkode applikasjon \nv0.1");
            alert.setContentText("\nJava Version: " + System.getProperty("java.version") + "\nOperating system: " + System.getProperty("os.name") + "\n\nThis application was made by:\nSteinar Hjelle Midthus.");
            alert.showAndWait();
        });
    }

    /**
     * Searches for the name that user puts in.
     * @param searchWord the new value the user has put in.
     * @param oldValue the old value that was in the searchfield.
     */
    private void searchForName(String searchWord, String oldValue){
        if (oldValue.length() > searchWord.length()){
            MainWindow.getMainWindow().updateObservablePostalInformation();
        }
        ObservableList<PostalInformation> obs = postalInformationTableView.getItems();
        int start = 0;
        int stop = searchWord.length();
        List<PostalInformation> newObs = obs.stream().filter(postalInformation -> {
            boolean valid = false;
            if (postalInformation.getNameOfPlace().length() >= stop){
                String subString = getSubString(postalInformation.getNameOfPlace(), start, stop);
                if (subString.equals(searchWord.toLowerCase())){
                    valid = true;
                }
            }

            return valid;
        }).collect(Collectors.toList());
        obs.setAll(newObs);
    }

    /**
     * The search function to search the table by number.
     * @param newValue the new value in the searchfield.
     * @param oldValue the old value from the searchfield.
     * @param textField the textfield we want to set to an old value if the user puts in a letter.
     */
    private void sortByPostalCode(String newValue, String oldValue, TextField textField){
        if (oldValue.length() > newValue.length()){
            MainWindow.getMainWindow().updateObservablePostalInformation();
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

    /**
     * Gets a substring of a word.
     * @param word the word you want the substring of.
     * @param start the letter the substring should start on.
     * @param stop the last letter the substring should end on.
     * @return a substring that is within the start and stop value.
     */
    private String getSubString(String word, int start, int stop){
        if (start > stop){
            throw new IllegalArgumentException("The start value must be less than the stop value.");
        }
        return word.substring(start, stop).toLowerCase();
    }
}
