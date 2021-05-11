package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import no.stonedstonar.deltre.postalApp.model.County;
import no.stonedstonar.deltre.postalApp.model.PostalFacade;
import no.stonedstonar.deltre.postalApp.model.PostalInformation;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;

/**
 * Represents a builder to make a table view for postal information.
 */
public class PostalInformationTableViewBuilder {

    private TableView<PostalInformation> tableView;

    private PostalFacade posFacade;

    /**
     * Makes an instance of the PostalInformationTableViewBuilder.
     * @param postalFacade the postal facade that the tableview should use as default.
     */
    public PostalInformationTableViewBuilder(PostalFacade postalFacade){
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
        TableColumn<PostalInformation, Long> postalCodeCol = new TableColumn<>("Post kode");
        postalCodeCol.setCellValueFactory(new PropertyValueFactory("postalCodeOfPlace"));
        tableView.getColumns().add(postalCodeCol);
        return this;
    }

    /**
     * Adds a place column to the table.
     * @return the PostalInformationTableViewBuilder.
     */
    public PostalInformationTableViewBuilder addTableViewPostalPlaceCol(){
        TableColumn<PostalInformation, String> placeCol = new TableColumn<>("Stedsnavn");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("nameOfPlace"));
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
            SimpleStringProperty string = new SimpleStringProperty(display);
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
            SimpleStringProperty string = new SimpleStringProperty(municipality);
            return string;
        });
        tableView.getColumns().add(municipalityCol);
        return this;
    }

//    public PostalInformationTableViewBuilder addTableViewPostBoxCol(){
//
//    }

    /**
     * Returns the tableview this class has constructed.
     * @return the tableview that is made by this tableview builder.
     */
    public TableView<PostalInformation> build(){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tableView;
    }
}
