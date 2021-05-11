package no.stonedstonar.deltre.postalApp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Represents a county and hold the name of its municipalities.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class County {

    private Map<Long, String> municipalityMap;

    private String nameOfCounty;


    private long countyNumber;

    /**
     * Makes an instance of the county class.
     * @param nameOfNewCounty the name of the county you want to create.
     */
    public County(String nameOfNewCounty, long countyNumber){
        PostalFacade.checkString(nameOfNewCounty, "name of the county");
        checkMunicipalityNumber(countyNumber);
        nameOfCounty = nameOfNewCounty;
        municipalityMap = new HashMap<>();
        this.countyNumber = countyNumber;
    }

    /**
     * Gets the county number stored in this object.
     * @return the county number that is valid for this object.
     */
    public long getCountyNumber() {
        return countyNumber;
    }

    /**
     * Adds a municipality to this county if they are not already in it.
     * @param nameOfMunicipality the name of the new municipality.
     * @param numberOfMunicipality the number of the new municipality.
     */
    public void addMunicipality(String nameOfMunicipality, long numberOfMunicipality){
        PostalFacade.checkString(nameOfMunicipality, "municipality name");
        PostalFacade.checkIfCountyAndMunicipalityNumberIsValid(numberOfMunicipality);
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
    public String getMunicipality(long municipalityNumber){
        PostalFacade.checkIfCountyAndMunicipalityNumberIsValid(municipalityNumber);
        String nameOfMunicipality;
        if (checkIfMunicipalityIsInCounty(municipalityNumber)){
            try {
                long key = municipalityMap.keySet().stream().filter(k -> k.longValue() == municipalityNumber).findFirst().get();
                nameOfMunicipality = municipalityMap.get(key);
            }catch (NoSuchElementException exception){
                throw new IllegalArgumentException("The municipality by this number " + municipalityNumber + " is not in " + nameOfCounty + ".");
            }
        }else {
            throw new IllegalArgumentException("The municipality by this number " + municipalityNumber + " is not in " + nameOfCounty + ".");
        }
        return nameOfMunicipality;
    }

    /**
     * Checks if the municipality is in this county already.
     * @param municipalityNumber the municipality number you want to check.
     * @return <code>true</code> if the municipality is already in the county.
     *         <code>false</code> if the municipality is not in the county.
     */
    public boolean checkIfMunicipalityIsInCounty(long municipalityNumber){
        boolean valid = false;
        if (!municipalityMap.isEmpty()){
            valid = municipalityMap.keySet().stream().filter(key -> key.longValue() == municipalityNumber).findFirst().isPresent();
        }
        return valid;
    }
    /**
     * Checks if the municipality number is a valid format.
     * @param numberToCheck the number you want to check.
     */
    private void checkMunicipalityNumber(Long numberToCheck){
        if ((numberToCheck < 0000) || (numberToCheck > 9999)){
            String error = "less than 0";
            if (numberToCheck > 9999){
                error = "greater than 9999";
            }
            throw new IllegalArgumentException("The municipality number cannot be " + error);
        }
    }

    /**
     * Gets the amount of municipalities in this county.
     * @return the amount of municipalities in this county.
     */
    public int getSize(){
        return municipalityMap.values().size();
    }
}
