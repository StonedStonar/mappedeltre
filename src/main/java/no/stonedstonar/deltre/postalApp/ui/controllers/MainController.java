package no.stonedstonar.deltre.postalApp.ui.controllers;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.model.PostalSystem;
import no.stonedstonar.deltre.postalApp.model.exceptions.InvalidFileFormatException;
import no.stonedstonar.deltre.postalApp.ui.views.MainWindow;
import no.stonedstonar.deltre.postalApp.ui.views.PostalApp;

import java.io.File;
import java.util.List;
import java.util.Optional;
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
    @FXML
    private MenuItem helpMenu;

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
            Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);
            closeAlert.setTitle("Bekreftelse vindu - Lukke applikasjon");
            closeAlert.setHeaderText("Bekreftelse vindu - Lukke applikasjon");
            closeAlert.setContentText("Er du sikker på at du vil lukke applikasjonen?");
            Optional result = closeAlert.showAndWait();
            if ((result.isPresent()) && (result.get().equals(ButtonType.OK))){
                PostalApp.getApp().exitApplication();
            }
        });
        aboutMenu.setOnAction(actionEvnet -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasjons vindu - Om Postkode applikasjonen");
            alert.setHeaderText("Postkode applikasjon \nv0.1");
            alert.setContentText("\nJava Version: " + System.getProperty("java.version") + "\nOperating system: " + System.getProperty("os.name") + "\n\nThis application was made by:\nSteinar Hjelle Midthus.");
            alert.showAndWait();
        });
        importMenu.setOnAction(action -> {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Informasjons vindu - Importere egen fil.");
            infoAlert.setHeaderText("Importere fil");
            infoAlert.setContentText("Når du importerer en ny fil i applikasjonen blir det gamle innholdet slettet.");
            infoAlert.showAndWait();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Fil velger - Importer postkode fil.");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tekst fil", "*.txt"));
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Tekst fil", "*.txt"));
            File fileSelected = fileChooser.showOpenDialog(null);
            if (fileSelected != null){
                try {
                    PostalSystem postalSystem = MainWindow.getMainWindow().getPostalSystem();
                    postalSystem.loadSelectedFile(fileSelected);
                    MainWindow.getMainWindow().updateObservablePostalInformation();
                    searchField.setText("");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informasjons vindu - Fil importert");
                    alert.setHeaderText("Fil importert");
                    alert.setContentText("Filen du valgte har blitt importert.");
                    alert.showAndWait();
                } catch (InvalidFileFormatException exception) {
                    Alert invalidFormatALert = new Alert(Alert.AlertType.ERROR);
                    invalidFormatALert.setTitle("En feil har oppstått.");
                    invalidFormatALert.setHeaderText("En feil har oppstått.");
                    invalidFormatALert.setContentText("En feil har oppstått i lastingen av filen. \nDen er av feil format for denne applikasjonen. \nVennligst prøv en annen fil som har riktig format.");
                    invalidFormatALert.showAndWait();
                }catch (IllegalArgumentException exception){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("En feil har oppstått.");
                    errorAlert.setHeaderText("En feil har oppstått i lastingen av filen.");
                    errorAlert.setContentText("En feil har oppstått i lastingen av filen. \nVennligst prøv på nytt.");
                    errorAlert.showAndWait();
                }
            }
        });
        helpMenu.setOnAction(action -> {
            Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
            informationAlert.setTitle("Informasjons vindu - Hvordan bruke applikasjonen");
            informationAlert.setHeaderText("Hvordan bruke applikasjonen");
            informationAlert.setContentText("For å bruke applikasjonen må du først importere en fil. \nDette gjør du ved å trykke på fil også importer fil.\n\nEtter at du har importert filen kan du bruke søkefeltet over tabellen. \nHvis du søker på et post nummer kan du ikke bruke bosktaver.\nOg hvis du søker på stedsnavn kan du heller ikke bruke nummer siden Oslo og stedsnavn sjeldent inneholder et nummer.");
            informationAlert.showAndWait();
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
