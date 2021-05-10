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

    /**
     * Makes an instance of the postal register.
     */
    public PostalRegister(){
        postalList = new ArrayList<>();
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
        PostalInformation.checkIfPostalCodeIsValid(postalCode);
        PostalInformation postalInformation;
        try {
            postalInformation = postalList.stream().filter(pi -> pi.getPostalCodeOfPlace().longValue() == postalCode.longValue()).findFirst().get();
        }catch (NoSuchElementException exception){
            throw new CouldNotGetPostalInformationException("The postalinformation by the postal code " + postalCode + " is not in this register.");
        }
        return postalInformation;
    }

    //Todo: Vurder en remove metode.
}
