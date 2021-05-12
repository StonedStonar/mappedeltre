package no.stonedstonar.deltre.postalApp.model;

import javafx.geometry.Pos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A test class that tests the different functions of the County class.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TestPostalFacade {

    /**
     * Tests if the addPostalSystem works with invalid country name.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addPostalSystem works with invalid country name.")
    public void TestIfAddPostalSystemWorksWithInvalidCountryName(){
        PostalFacade postalFacade = new PostalFacade();
        try {
            postalFacade.addPostalSystem("", 0L, 9999L, 0L, 9999L);
            fail("Expected to get a exception since the country name is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the addPostalSystem works with invalid max county and municipality value.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addPostalSystem works with invalid max county and municipality value.")
    public void TestIfAddPostalSystemWorksWithInvalidMaxCountyInput(){
        PostalFacade postalFacade = new PostalFacade();
        try {
            postalFacade.addPostalSystem("Norway", 0L, 9999L, 0L, 0L);
            fail("Expected to get a exception since the input max is invalid.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the addPostalSystem works with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the addPostalSystem works with valid input.")
    public void TestIfaddPostalSystemWorksWithValidInput(){
        PostalFacade postalFacade = new PostalFacade();
        try {
            postalFacade.addPostalSystem("Norway", 0L, 9999L, 0L, 9999L);
            assertTrue(true);
        }catch (IllegalArgumentException exception){
            fail("Expected the postal system to be added since its a valid format.");
        }
    }

    /**
     * Tests if the getPostalSystemByName method works as intended with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the getPostalSystemByName method works as intended with invalid input.")
    public void TestIfGetPostalSystemByNameWorksWithInvalidInput(){
        PostalFacade postalFacade = new PostalFacade();
        postalFacade.addPostalSystem("Norway", 0L, 9999L, 0L, 9999L);
        try {
            postalFacade.getPostalSystemByName("Svergie");
            fail("Expected to get a exception since the input is not in the postal facade.");
        }catch (IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getPostalSystemByName method works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the getPostalSystemByName method works as intended with valid input.")
    public void TestIfGetPostalSystemByNameWorksWithValidInput(){
        PostalFacade postalFacade = new PostalFacade();
        postalFacade.addPostalSystem("Norway", 0L, 9999L, 0L, 9999L);
        try {
            postalFacade.getPostalSystemByName("Norway");
            assertTrue(true);
        }catch (IllegalArgumentException exception){
            fail("Expected the test to find the country Norway since its in the Postal Facade.");
        }
    }
}
