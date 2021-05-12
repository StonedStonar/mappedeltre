package no.stonedstonar.deltre.postalApp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class that tests the different functions of the PostalInformation class.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TestPostalInformation {

    /**
     * Tests if the constructor works as intended with valid input.
     * Test type: Positive
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
     * Test type: Negative
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
     * Tests if the constructor works as intended with invalid countyAndMunicipalityNumber.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the constructor works as intended with invalid countyAndMunicipalityNumber.")
    public void TestIfConstructorWorksWithInvalidCountyAndMunicipalityNumber(){
        try {
            PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, null);
            fail("Expected to get a IllegalArguemntException since the County and municipality number is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the setCountyAndMunicipalityNumber works with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the setCountyAndMunicipalityNumber works with invalid input.")
    public void TestIfSetCountyAndMunicipalityNumberWorksWithInvalidInput(){
        try {
            PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, 3451L);
            postalInformation.setCountyAndMunicipalityNumber(null);
            fail("Expected to get a exception since the input is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the setCountyAndMunicipalityNumber works with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the setCountyAndMunicipalityNumber works with valid input.")
    public void TestIfSetCountyAndMunicipalityNumberWorksWithValidInput(){
        try {
            Long code = 3560L;
            PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, 3451L);
            postalInformation.setCountyAndMunicipalityNumber(code);
            assertEquals(code, postalInformation.getCountyAndMunicipalityNumber());
        }catch (IllegalArgumentException exception){
            fail("Expected the test to go smoothly since the input is valid.");
        }
    }

    /**
     * Tests if the setNameOfPlace method works as intended with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the setNameOfPlace method works as intended with invalid input.")
    public void TestIfSetNameOfPlaceWorksWithInvalidInput(){
        try {
            PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, 3451L);
            postalInformation.setNameOfPlace("");
            fail("Expected to get a exception since the input is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the setNameOfPlace method works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the setNameOfPlace method works as intended with valid input.")
    public void TestIfSetNameOfPlaceWorksWithValidInput(){
        try {
            String nameOfPalce = "Etnedal";
            PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, 3451L);
            postalInformation.setNameOfPlace(nameOfPalce);
            if (nameOfPalce.equals(postalInformation.getNameOfPlace())){
                assertTrue(true);
            }else {
                fail("Expected the value to be the same since the name was set to a new value.");
            }
        }catch (IllegalArgumentException exception){
            fail("Expected the name to be set to a new value since the input is valid.");
        }
    }
}
