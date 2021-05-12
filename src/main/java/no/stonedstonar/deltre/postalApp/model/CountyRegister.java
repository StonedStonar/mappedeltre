package no.stonedstonar.deltre.postalApp.model;

import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddCountyException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a county register where all the county's of this country are stored in case the government changes
 * the postal system later.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CountyRegister {

    private List<County> countyList;

    private Long maxCountyAndMunicipalityLength;

    private Long minCountyAndMunicipalityLength;

    /**
     * Makes an instance of the county register.
     * @param minCountyAndMunicipalityValue the min value the register should hold.
     * @param maxCountyAndMunicipalityValue the max value that the register should hold.
     */
    public CountyRegister(Long minCountyAndMunicipalityValue,Long maxCountyAndMunicipalityValue){
        countyList = new ArrayList<>();
        if ((minCountyAndMunicipalityValue == null) || (maxCountyAndMunicipalityValue == null)){
            throw new IllegalArgumentException("Min and max county and municipality values cannot be null");
        }
        this.maxCountyAndMunicipalityLength = maxCountyAndMunicipalityValue;
        this.minCountyAndMunicipalityLength = minCountyAndMunicipalityValue;
    }

    /**
     * Gets the max value that the county and municipality can be.
     * @return a long that says how big a county and municiplaity number can be in this class.
     */
    public long getMaxCountyAndMunicipalityLength(){
        return maxCountyAndMunicipalityLength;
    }

    /**
     * Gets the min value that the county and municipality can be.
     * @return a long that says how small a county and municipality number can be in this class.
     */
    public long getMinCountyAndMunicipalityLength(){
        return minCountyAndMunicipalityLength;
    }

    /**
     *
     * @param countyName
     * @param countyAndMunicipalityNumber
     * @throws CouldNotAddCountyException
     */
    public void addCountyWithName(String countyName, Long countyAndMunicipalityNumber) throws CouldNotAddCountyException {
        checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
        long camLong = getSubNumberAsString(countyAndMunicipalityNumber, 0, 2);
        County county = new County(countyName, camLong);
        boolean nameInRegister = countyList.stream().anyMatch(c -> c.getNameOfCounty().equals(countyName));
        boolean numberInRegister = countyList.stream().anyMatch(c2 -> c2.getCountyNumber() == camLong);
        if ((!nameInRegister) && (!numberInRegister)){
            countyList.add(county);
        }else {
            String message = "name";
            if (numberInRegister){
                message = "county number";
            }
            throw new IllegalArgumentException("The county by the " + message + " " + countyName + " is already in the register.");
        }

    }

    /**
     * Adds a municipality to the wanted county.
     * @param municipalityName the name of the new municipality.
     * @param countyAndMunicipalityNumber the county and municipality number.
     * @throws CouldNotGetCountyException gets thrown if the county does not exist.
     */
    public void addMunicipalityToCounty(String municipalityName, Long countyAndMunicipalityNumber) throws CouldNotGetCountyException {
        PostalSystem.checkString(municipalityName, "municipality name");
        checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
        if (checkIfCountyIsInRegister(countyAndMunicipalityNumber)){
            County county = getCounty(countyAndMunicipalityNumber);
            county.addMunicipality(municipalityName, countyAndMunicipalityNumber);
        }
    }

    /**
     * Checks if the county is in the register.
     * @param countyAndMunicipalityNumber the county and municipality number you want to check.
     * @return <code>true</code> if the county is in the register.
     *         <code>false</code> if the county is not in the register.
     */
    public boolean checkIfCountyIsInRegister(Long countyAndMunicipalityNumber){
        boolean valid;
        checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
        Long countyNumber = getSubNumberAsString(countyAndMunicipalityNumber, 0, 2);
        try {
            valid = countyList.stream().filter(county -> county.getCountyNumber() == countyNumber).findFirst().isPresent();
        }catch (NoSuchElementException exception){
            valid = false;
        }
        return valid;
    }

    /**
     * Get's a county by its county number.
     * @param countyAndMunicipalityNumber the countyAndMunicipalityNumber of this county.
     * @return a county that matches this number.
     * @throws CouldNotGetCountyException gets thrown if the county is not found in the register.
     */
    public County getCounty(Long countyAndMunicipalityNumber) throws CouldNotGetCountyException {
        Long countyLong = getSubNumberAsString(countyAndMunicipalityNumber, 0, 2);
        County countyToGet;
        try {
            countyToGet = countyList.stream().filter(county -> county.getCountyNumber() == countyLong).findFirst().get();

        }catch (NoSuchElementException exception){
            throw new CouldNotGetCountyException("Could not find the county with the code " + countyLong + " in this register.");
        }
        return countyToGet;
    }

    /**
     * Checks if the municipality is already in the register.
     * @param countyAndMunicipalityNumber the county and municipality number you want to check.
     * @return <code>true</code> if the municipality is in the county.
     *         <code>false</code> if the municipality is not in the county.
     * @throws CouldNotGetCountyException gets thrown if the county is not found in the register.
     */
    public boolean checkIfMunicipalityIsInCounty(Long countyAndMunicipalityNumber) throws CouldNotGetCountyException{
        boolean valid;
        checkIfCountyAndMunicipalityNumberIsValid(countyAndMunicipalityNumber);
        County county = getCounty(countyAndMunicipalityNumber);
        valid = county.checkIfMunicipalityIsInCounty(countyAndMunicipalityNumber);
        return valid;
    }

    /**
     * Gets a municipality name based on the countyWithMunicipality number.
     * @param countyWithMunicipality the number that contains the county and municipality.
     * @return the municipality that matches this number.
     * @throws CouldNotGetCountyException gets thrown if the county is not found in the register.
     */
    public String getMunicipality(Long countyWithMunicipality) throws CouldNotGetCountyException {
        long countyNumber = getSubNumberAsString(countyWithMunicipality, 0, 2);
        String municipalityName;
        if (checkIfCountyIsInRegister(countyNumber)){
            County county = getCounty(countyNumber);
            municipalityName = county.getMunicipality(countyWithMunicipality);
        }else {
            throw new IllegalArgumentException("The county with the number" + countyNumber + " is not in this county register.");
        }
        return municipalityName;
    }

    /**
     * Gets the parts of a number that you want.
     * @param numberToBeSplit the number you want the substring of.
     * @param firstPlace the first character you want.
     * @param lastPlace the last character you want
     * @return a new value that is the right formnat you want.
     */
    private Long getSubNumberAsString(Long numberToBeSplit, int firstPlace, int lastPlace){
        if (numberToBeSplit == null){
            throw new IllegalArgumentException("The municipality number cannot be null.");
        }
        String numString = Long.toString(numberToBeSplit);
        int max = lastPlace;;
        int min = firstPlace;
        if (numString.length() < lastPlace){
            max = lastPlace - (lastPlace - (numString.length()));
        }

        String substring = numString.substring(min,max);

        long value = Long.parseLong(substring);

        return value;
    }

    /**
     * Gets the amount of Municipalities in the register.
     * @return a int that is the amount of Municipalities in the register.
     */
    public int getSize(){
        int size = 0;
        ArrayList<Number> values = new ArrayList<>();
        countyList.forEach(county -> {
            values.add(county.getSize());
        });
        Iterator<Number> it = values.iterator();
        while (it.hasNext()){
            size += it.next().intValue();
        }
        return size;
    }

    /**
     * Checks if the CountyAndMunicipalityNumber is a valid format.
     * @param countyAndMunicipalityNumber the number of the county and the municipality number that you want to check .
     */
    public void checkIfCountyAndMunicipalityNumberIsValid(Long countyAndMunicipalityNumber){
        if ((countyAndMunicipalityNumber == null) || (countyAndMunicipalityNumber <= minCountyAndMunicipalityLength.longValue()) || (countyAndMunicipalityNumber > maxCountyAndMunicipalityLength.longValue())){
            String error = "must be between 0000 and 9999.";
            if (countyAndMunicipalityNumber == null){
                error = "cannot be null.";
            }
            throw new IllegalArgumentException("The county and municipality number or postal code " + error);
        }
    }
}
