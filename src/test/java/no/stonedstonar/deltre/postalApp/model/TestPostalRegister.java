package no.stonedstonar.deltre.postalApp.model;

import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddPostalInformationException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetPostalInformationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A test class that tests the different functions of the PostalRegister class.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TestPostalRegister {

    /**
     * Sets up a test space.
     * @return a register with one postal information as content.
     */
    private PostalRegister setupTestSpace(){
        PostalRegister postalRegister = new PostalRegister();
        PostalInformation postalInformation = new PostalInformation("Aurdal", 2910L, 3451L);
        try {
            postalRegister.addPostalInformation(postalInformation);
        }catch (CouldNotAddPostalInformationException exception){

        }
        return postalRegister;
        }

    /**
     * Tests if the addPostalInformation works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the addPostalInformation works as intended with valid input")
    public void TestIfAddPostalInformationWorksWithValidInput(){
        PostalRegister postalRegister = setupTestSpace();
        PostalInformation information = new PostalInformation("Leira", 2903L, 3451L);
        try {
            postalRegister.addPostalInformation(information);
            assertTrue(true);
        }catch (CouldNotAddPostalInformationException exception){
            fail("Expected the postal information object to be added since its a valid format.");
        }
    }


    /**
     * Tests if the addPostalInformation works as intended with null as input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the addPostalInformation works as intended with null as input.")
    public void TestIfAddPostalInformationWorksWithNullInput(){
        PostalRegister postalRegister = setupTestSpace();
        try {
            postalRegister.addPostalInformation(null);
            fail("Expected to get a CouldNotAddPostalInformationException since the input is invalid.");
        }catch (CouldNotAddPostalInformationException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getPostalInformation works as intended with null as input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the getPostalInformation works as intended with null as input.")
    public void TestIfGetPostalInformationWorksWithNullInput(){
        PostalRegister postalRegister = setupTestSpace();
        try {
            postalRegister.getPostalInformation(null);
            fail("Expeceted to get a CouldNotGetPostalInformationException since the format is invalid.");
        }catch (CouldNotGetPostalInformationException | IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getPostalInformation works as intended with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the getPostalInformation works as intended with invalid input.")
    public void TestIfGetPostalInformationWorksWithInvalidInput(){
        PostalRegister postalRegister = setupTestSpace();
        try {
            postalRegister.getPostalInformation(3054L);
            fail("Expected to get a CouldNotGetPostalInformationException since the input is not in the list.");
        }catch (CouldNotGetPostalInformationException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getPostalInformation works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the getPostalInformation works as intended with valid input.")
    public void TestIfGetPostalInformationWorksWithValidInput(){
        PostalRegister postalRegister = setupTestSpace();
        try {
            postalRegister.getPostalInformation(2910L);
            assertTrue(true);
        }catch (CouldNotGetPostalInformationException exception){
            fail("Expected the method to work since the input is valid.");
        }
    }
}
