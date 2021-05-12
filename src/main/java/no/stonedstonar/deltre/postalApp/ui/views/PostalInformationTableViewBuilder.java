package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import no.stonedstonar.deltre.postalApp.model.County;
import no.stonedstonar.deltre.postalApp.model.PostalSystem;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;

/**
 * Represents a builder to make a table view for postal information.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalInformationTableViewBuilder {

    private TableView<PostalInformation> tableView;

    private PostalSystem posFacade;

    /**
     * Makes an instance of the PostalInformationTableViewBuilder.
     * @param postalFacade the postal facade that the tableview should use as default.
     */
    public PostalInformationTableViewBuilder(PostalSystem postalFacade){
        tableView = new TableView<>();
        if (postalFacade != null){
            posFacade = postalFacade;
        }else {
            throw new IllegalArgumentException("Postal facade cannot be null");
        }
    }

    /**
     * Adds a postal code column to the table.
     * @return the PostalInformationTableViewBuilder.
     */
    public PostalInformationTableViewBuilder addTableViewPostalCodeCol(){
        TableColumn<PostalInformation, String> postalCodeCol = new TableColumn<>("Post kode");
        postalCodeCol.setCellValueFactory(postalInformation -> {
            long postalCode = postalInformation.getValue().getPostalCodeOfPlace();
            String postalString = Long.toString(postalCode);
            while (postalString.length() < MainWindow.getMainWindow().getPostalSystem().getPostalCodeMaxLength()){
                String addZero = "0";
                postalString = addZero + postalString;
            }
            SimpleStringProperty postalCodeFormat = new SimpleStringProperty(postalString);
            return postalCodeFormat;
        });
        tableView.getColumns().add(postalCodeCol);
        return this;
    }

    /**
     * Adds a place column to the table.
     * @return the PostalInformationTableViewBuilder.
     */
    public PostalInformationTableViewBuilder addTableViewPostalPlaceCol(){
        TableColumn<PostalInformation, String> placeCol = new TableColumn<>("Stedsnavn");
        placeCol.setCellValueFactory(postalInformation -> {
            String word = postalInformation.getValue().getNameOfPlace();
            SimpleStringProperty wordToDisplay = new SimpleStringProperty(makeStringLowerCaseAndFirstLargeCase(word));
            return wordToDisplay;
        });
        tableView.getColumns().add(placeCol);
        return this;
    }

    /**
     * Adds a county col to the table.
     * @return the PostalInformationTableViewBuilder.
     */
    public PostalInformationTableViewBuilder addTableViewCountyCol(){
        TableColumn<PostalInformation, String> countyCol = new TableColumn<>("Fylke");
        countyCol.setCellValueFactory(postalInformation -> {
            long countyAndMunNum = postalInformation.getValue().getCountyAndMunicipalityNumber();
            County county;
            try {
                county = posFacade.getCounty(countyAndMunNum);
            }catch (IllegalArgumentException | CouldNotGetCountyException exception){
                county = null;
            }
            String display = "Fylke ikke funnet.";
            if (county != null){
                display = county.getNameOfCounty();
            }
            SimpleStringProperty string = new SimpleStringProperty(makeStringLowerCaseAndFirstLargeCase(display));
            return string;
        });
        tableView.getColumns().add(countyCol);
        return this;
    }

    /**
     * Adds a municipality column to the table.
     * @return the PostalInformationTableViewBuilder.
     */
    public PostalInformationTableViewBuilder addTableViewMunicipalityCol(){
        TableColumn<PostalInformation, String> municipalityCol = new TableColumn<>("Kommune");
        municipalityCol.setCellValueFactory(postalInformation -> {
            long countyAndMunNum = postalInformation.getValue().getCountyAndMunicipalityNumber();
            String municipality;
            try {
                municipality = posFacade.getMunicipalityName(countyAndMunNum);
            }catch (CouldNotGetCountyException | IllegalArgumentException exception){
                municipality = "Ingen kommune";
            }
            SimpleStringProperty string = new SimpleStringProperty(makeStringLowerCaseAndFirstLargeCase(municipality));
            return string;
        });
        tableView.getColumns().add(municipalityCol);
        return this;
    }

    /**
     * Returns the tableview this class has constructed.
     * @return the tableview that is made by this tableview builder.
     */
    public TableView<PostalInformation> build(){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tableView;
    }

    /**
     * Makes the string to a large first case and the rest to a lower case.
     * @param word the word you want to have a first letter capetalized and the rest lower case.
     * @return the word in the format described above.
     */
    private String makeStringLowerCaseAndFirstLargeCase(String word){
        PostalSystem.checkString(word, "string to make lower case");
        String newWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        return newWord;
    }
}
