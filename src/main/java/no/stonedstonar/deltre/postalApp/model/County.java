package no.stonedstonar.deltre.postalApp.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a county and hold the name of its municipalities.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class County {

    private Map<Long, String> municipalityMap;

    private String nameOfCounty;

    /**
     * Makes an instance of the county class.
     * @param nameOfNewCounty the name of the county you want to create.
     */
    public County(String nameOfNewCounty){
        PostalFacade.checkString(nameOfNewCounty, "name of the county");
        nameOfCounty = nameOfNewCounty;
        municipalityMap = new HashMap<>();
    }


    //Todo: Vurder en metode som sjekker at en kommune med samme navn ikke allerede finnes i registeret.
    /**
     * Adds a municipality to this county if they are not already in it.
     * @param nameOfMunicipality the name of the new municipality.
     * @param numberOfMunicipality the number of the new municipality.
     */
    public void addMunicipality(String nameOfMunicipality, Long numberOfMunicipality){
        PostalFacade.checkString(nameOfMunicipality, "municipality name");
        checkMunicipalityNumber(numberOfMunicipality);
        if (!municipalityMap.containsKey(numberOfMunicipality)){
            municipalityMap.put(numberOfMunicipality, nameOfMunicipality);
        }else {
            throw new IllegalArgumentException("The municipality is already in this county's list." + nameOfMunicipality + " current county:" + nameOfCounty);
        }
    }

    /**
     * Gets the name of the county.
     * @return the name of this county.
     */
    public String getNameOfCounty(){
        return nameOfCounty;
    }

    /**
     * Gets a municipality by its municipality code.
     * @param municipalityNumber the number of the municipality.
     * @return the name of the municipality that matches this number.
     */
    public String getMunicipality(Long municipalityNumber){
        if ((municipalityNumber == null) || (municipalityNumber == 0)){
            throw new IllegalArgumentException("The municipality number cannot be null or zero.");
        }
        String nameOfMunicipality;
        if (municipalityMap.containsKey(municipalityNumber)){
            nameOfMunicipality = municipalityMap.get(municipalityNumber);
        }else {
            throw new IllegalArgumentException("The municipality by this number " + municipalityNumber + " is not in " + nameOfCounty + ".");
        }
        return nameOfMunicipality;
    }

    /**
     * Checks if the municipality number is a valid format.
     * @param numberToCheck the number you want to check.
     */
    private void checkMunicipalityNumber(Long numberToCheck){
        if ((numberToCheck < 00) || (numberToCheck > 99)){
            String error = "less than 0";
            if (numberToCheck > 99){
                error = "greater than 99";
            }
            throw new IllegalArgumentException("The municipality number cannot be " + error);
        }
    }
}
