package no.stonedstonar.deltre.postalApp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A test class that tests the different functions of the PostalInformation class.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TestPostalInformation {

    /**
     * Tests if the constructor works as intended with valid input.
     */
    @Test
    @DisplayName("Tests if the constructor works as intended with valid input.")
    public void TestIfConstructorWorksWithValidName(){
        try {
            PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, 3451L);
            assertTrue(true);
        }catch (IllegalArgumentException exception){
            fail("Expected the postal information to be made since the format is valid.");
        }
    }
    /**
     * Tests if the constructor works as intended with invalid name.
     */
    @Test
    @DisplayName("Tests if the constructor works as intended with invalid name.")
    public void TestIfConstructorWorksWithInvalidName(){
        try {
            PostalInformation postalInformation = new PostalInformation("", 2910L, 3451L);
            fail("Expected to get a IllegalArguemntException since the name is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the constructor works as intended with invalid postalcode.
     */
    @Test
    @DisplayName("Tests if the constructor works as intended with invalid postalcode.")
    public void TestIfConstructorWorksWithInvalidPostalCode(){
        try {
            PostalInformation postalInformation = new PostalInformation("Aurdal", 0L, 3451L);
            fail("Expected to get a IllegalArguemntException since the postal code is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the constructor works as intended with invalid countyAndMunicipalityNumber.
     */
    @Test
    @DisplayName("Tests if the constructor works as intended with invalid countyAndMunicipalityNumber.")
    public void TestIfConstructorWorksWithInvalidCountyAndMunicipalityNumber(){
        try {
            PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, 0L);
            fail("Expected to get a IllegalArguemntException since the County and municipality number is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }
}
