package no.stonedstonar.deltre.postalApp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A test class that tests the different functions of the County class.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TestCounty {

    /**
     * Sets up a test space.
     * @return a county with one municipality.
     */
    private County setUpTestSpace(){
        County county = new County("Innlandet", 34);
        county.addMunicipality("Nord-Aurdal", 51L);
        return county;
    }
    /**
     * Tests if the county constructor works with invalid input.
     * Test type: Negativ test
     */
    @Test
    @DisplayName("Tests if the county constructor works with invalid input.")
    public void TestIfCountryConstructorWorksWithInvalidInput(){
        try {
            County county = new County(null, 34);
            fail("Expected to get a IllegalArgumentException since the input is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the county constructor works with valid input.
     * Test type: Positiv test
     */
    @Test
    @DisplayName("Tests if the county constructor works with valid input.")
    public void TestIfCountyConstructorWorksWithValidInput(){
        try {
            County county = new County("Innlandet", 34);
            assertTrue(true);
        }catch (IllegalArgumentException exception){
            fail("Expected the county to be made since the format is valid.");
        }
    }

    /**
     * Tests if the addMunicipality method works with invalid name.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addMunicipality method works with invalid name.")
    public void TestIfAddMunicipalityMethodWorksWithInvalidName(){
        County county = setUpTestSpace();
        try {
            county.addMunicipality(null, 50L);
            fail("Expected to get a IllegalArgumentException since the input is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the addMunicipality method works with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the addMunicipality method works with valid input.")
    public void TestIfAddMunicipalityMethodWorksWithValidInput(){
        County county = setUpTestSpace();
        try {
            county.addMunicipality("Etnedal", 50L);
            assertTrue(true);
        }catch (IllegalArgumentException exception){
            fail("Expected to let the municipality to be added since the format is valid.");
        }
    }

    /**
     * Tests if the getMunicipality method works as intended with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the getMunicipality method works as intended with invalid input.")
    public void TestIfGetMunicipalityMethodWorksWithInvalidInput(){
        County county = setUpTestSpace();
        try {
            county.getMunicipality(50L);
            fail("Expected to get a IllegalArgumentException since the input is not in the register.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getMunicipality method works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the getMunicipality method works as intended with valid input.")
    public void TestIfGetMunicipalityMethodWorksWithValidInput(){
        County county = setUpTestSpace();
        try {
            county.getMunicipality(51L);
            assertTrue(true);
        }catch (IllegalArgumentException exception){
            fail("Expected the result to be valid since the input is valid and its in the system.");
        }
    }

    /**
     * Tests if the checkMunicipalityIsInCounty method works as intended with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the checkMunicipalityIsInCounty method works as intended with invalid input.")
    public void TestIfCheckMunicipalityIsInCountyWorksWithInvalidInput(){
        County county = setUpTestSpace();
        try {
            county.getMunicipality(50L);
            fail("Expected to get a exception since the input municipality is not in the county.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the checkMunicipalityIsInCounty method works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the checkMunicipalityIsInCounty method works as intended with valid input.")
    public void TestIfCheckMunicipalityIsInCountyWorksWithValidInput(){
        County county = setUpTestSpace();
        try {
            county.getMunicipality(51L);
            assertTrue(true);
        }catch (IllegalArgumentException exception){
            fail("Didnt expect to get a exception since the input is valid.");
        }
    }
}
