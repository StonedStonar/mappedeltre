package no.stonedstonar.deltre.postalApp.model;

import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddPostalInformationException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetPostalInformationException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a register where all the PostalInformation is stored.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalRegister {

    private List<PostalInformation> postalList;

    private Long minPostalCodeValue;

    private Long maxPostalCodeValue;

    /**
     * Makes an instance of the postal register.
     * @param minPostalCodeValue the minimum value of a post code that this postal register should contain.
     * @param maxPostalCodeValue the max value of a post code that this postal register should contain.
     */
    public PostalRegister(long minPostalCodeValue, long maxPostalCodeValue){
        if (minPostalCodeValue >= maxPostalCodeValue){
            throw new IllegalArgumentException("The maximum value for the postal code should be larger than the minimum value.");
        }
        postalList = new ArrayList<>();
        this.minPostalCodeValue = minPostalCodeValue;
        this.maxPostalCodeValue = maxPostalCodeValue;
    }

    /**
     * Gets the postal code max.
     * @return the max amount that the postalcode can be.
     */
    public long getPostalCodeMax(){
        return maxPostalCodeValue;
    }

    /**
     * Gets the postal list.
     * @return a list that holds all the postal information.
     */
    public List<PostalInformation> getPostalList(){
        return postalList;
    }

    /**
     * Adds a postal information object to the register.
     * @param postalInformation the postal information object that you want to add to the register.
     * @throws CouldNotAddPostalInformationException gets thrown if the input is null or allready in the system.
     */
    public void addPostalInformation(PostalInformation postalInformation) throws CouldNotAddPostalInformationException {
        if (postalInformation == null) {
            throw new CouldNotAddPostalInformationException("The postal information object cannot be null.");
        }
        checkIfPostalCodeIsValid(postalInformation.getPostalCodeOfPlace());
        if (postalList.contains(postalInformation)){
            throw new CouldNotAddPostalInformationException("The postal information object is already in the list.");
        }else {
            postalList.add(postalInformation);
        }
    }

    /**
     * Gets the postal information object that matches this postal code.
     * @param postalCode the postal code that you want to find in the register.
     * @return the postal information object that holds this information.
     * @throws CouldNotGetPostalInformationException gets thrown if the postal code does not match anyone of the postal
     *         information objects in the system.
     */
    public PostalInformation getPostalInformation(Long postalCode) throws CouldNotGetPostalInformationException {
        checkIfPostalCodeIsValid(postalCode);
        PostalInformation postalInformation;
        try {
            postalInformation = postalList.stream().filter(pi -> pi.getPostalCodeOfPlace().longValue() == postalCode.longValue()).findFirst().get();
        }catch (NoSuchElementException exception){
            throw new CouldNotGetPostalInformationException("The postalinformation by the postal code " + postalCode + " is not in this register.");
        }
        return postalInformation;
    }

    /**
     * Checks if the postal code is of a valid format.
     * @param postCode the postal code you want to check.
     */
    public void checkIfPostalCodeIsValid(Long postCode){
        if ((postCode == null) || (postCode <= minPostalCodeValue) || (postCode > maxPostalCodeValue)){
            String error = "is too short";
            if (postCode == null){
                error = "null";
            }else if (postCode > maxPostalCodeValue){
                error = "is too long";
            }
            throw new IllegalArgumentException("The postal code " + error + " must be a number between 0000 and 9999");
        }
    }
}
