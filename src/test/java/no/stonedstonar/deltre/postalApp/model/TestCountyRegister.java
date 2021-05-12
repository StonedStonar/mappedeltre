package no.stonedstonar.deltre.postalApp.model;

import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddCountyException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A test class that tests the different functions of the CountyRegister class.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TestCountyRegister {

    /**
     * Sets up a basic test space.
     * @return a CountyRegister with one county.
     */
    private CountyRegister setUpTestSpace(){
        CountyRegister countyRegister = new CountyRegister(0L, 9999L);
        try {
            countyRegister.addCountyWithName("Innlandet", 30L);
            countyRegister.addMunicipalityToCounty("Aurdal", 3051L);
        }catch (Exception exception){

        }
        return countyRegister;
    }

    /**
     * Tests if the addCountyWithName works with invalid name.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addCountyWithName works with invalid name.")
    public void TestIfAddCountyWithNameWorksWithInvalidName(){
        CountyRegister countyRegister = setUpTestSpace();
        try {
            countyRegister.addCountyWithName("", 3051L);
            fail("Expected to get a Exception since the input is invalid.");
        }catch (IllegalArgumentException | CouldNotAddCountyException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the addCountyWithName works with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the addCountyWithName works with valid input")
    public void TestIfAddCountyWithNameWorksWithValidInput(){
        CountyRegister countyRegister = setUpTestSpace();
        try {
            countyRegister.addCountyWithName("Oslo", 0301L);
            assertTrue(true);
        }catch (IllegalArgumentException | CouldNotAddCountyException exception){
            fail("Expected the county to be added since the input is valid.");
        }
    }

    /**
     * Tests if the addCountyWithName works with duplicate county.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addCountyWithName works with duplicate county.")
    public void TestIfDuplicateCountyCanBeAdded(){
        CountyRegister countyRegister = setUpTestSpace();
        try {
            countyRegister.addCountyWithName("Innlandet", 49L);
            fail("Expected to get a exception since the county is allready in the register.");
        }catch (IllegalArgumentException | CouldNotAddCountyException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the addMunicipalityToCounty works with invalid municipality name.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addMunicipalityToCounty works with invalid municipality name.")
    public void TestIfAddMunicipalityToCountyWorksWithInvalidInput(){
        CountyRegister countyRegister = setUpTestSpace();
        try {
            countyRegister.addMunicipalityToCounty(null, 3051L);
            fail("Expected to get a Exception since the input is invalid.");
        }catch (CouldNotGetCountyException | IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the addMunicipalityToCounty works with wrong countyAndMunicipalityNumber.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addMunicipalityToCounty works with wrong countyAndMunicipalityNumber.")
    public void TestIfAddMunicipalityToCountyWorksWithInvalidNumberInput(){
        CountyRegister countyRegister = setUpTestSpace();
        try {
            countyRegister.addMunicipalityToCounty("Nord-Aurdal", 10000L);
            fail("Expected to get a Exception since the input is invalid.");
        }catch (CouldNotGetCountyException | IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the checkIfCountyIsInRegister works as intended with wrong countyAndMunicipalityNumber.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the checkIfCountyIsInRegister works as intended with wrong countyAndMunicipalityNumber.")
    public void TestIfCheckIfCountyIsInRegisterWorksWithWrongNumber(){
        CountyRegister countyRegister = setUpTestSpace();
        boolean valid = countyRegister.checkIfCountyIsInRegister(2010L);
        if (valid){
            fail("Expected to get false since the input is not in the register.");
        }else {
            assertTrue(true);
        }
    }

    /**
     * Tests if the checkIfCountyIsInRegister works with valid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the checkIfCountyIsInRegister works with valid input.")
    public void TestIfCheckIfCountyIsInRegisterWorksWithValidInput(){
        CountyRegister countyRegister = setUpTestSpace();
        boolean valid = countyRegister.checkIfCountyIsInRegister(3052L);
        if (valid){
            assertTrue(true);
        }else {
            fail("Expected to get false since the input is not in the register.");
        }
    }

    /**
     * Tests if the getMunicipality method works with wrong input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the getMunicipality method works with wrong input.")
    public void TestIfGetMunicipalityMethodWorksWithWrongInput(){
        CountyRegister countyRegister = setUpTestSpace();
        try {
            countyRegister.getMunicipality(3052L);
            fail("Expected to get a exception since the input is not in the system.");
        }catch (IllegalArgumentException | CouldNotGetCountyException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getMunicipality method works with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the getMunicipality method works with valid input.")
    public void TestIfGetMunicipalityMethodWorksWithValidInput(){
        CountyRegister countyRegister = setUpTestSpace();
        try {
            countyRegister.getMunicipality(3051L);
            assertTrue(true);
        }catch (IllegalArgumentException | CouldNotGetCountyException exception){
            fail("Expected to get the municipality since the input is valid.");
        }
    }
}
