package no.stonedstonar.deltre.postalApp.model;

import javafx.geometry.Pos;
import jdk.jfr.StackTrace;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddPostalInformationException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;
import no.stonedstonar.deltre.postalApp.model.exceptions.InvalidFileFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class that tests the different functions of the PostalFacade class.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TestPostalFacade {

    /**
     * Makes a basic postal facade that we can use for testing.
     * @return a postal facade that has been loaded from a file.
     */
    private PostalFacade setUpTestSpace(){
        PostalFacade postalFacade = new PostalFacade();
        try {
            Path path = Path.of("src\\main\\resources\\postnummer.txt");
            File file = path.toFile();
            postalFacade.loadSelectedFile(file);
        }catch (InvalidFileFormatException exception){

        }
        return postalFacade;
    }

    /**
     * Tests if the loadSelectedFile works as intended with invalid file type.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the loadSelectedFile works as intended with invalid file type.")
    public void TestIfLoadSelectedFileWorksAsIntendedWithInvalidInput(){
        PostalFacade postalFacade = new PostalFacade();
        try {
            Path path = Path.of("src\\main\\resources\\invalidFile.txt");
            File file = path.toFile();
            postalFacade.loadSelectedFile(file);
            fail("Expected to get a exception sine the input is invalid.");
        } catch (InvalidFileFormatException | IllegalArgumentException exception) {
            assertTrue(true);
        }
    }

    /**
     * Tests if the loadSelectedFile works as intended with valid file type.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the loadSelectedFile works as intended with valid file type.")
    public void TestIfLoadSelectedFileWorksAsIntendedWithValidFile(){
        PostalFacade postalFacade = new PostalFacade();
        try {
            Path path = Path.of("src\\main\\resources\\postnummer.txt");
            File file = path.toFile();
            postalFacade.loadSelectedFile(file);
            assertTrue(true);
        } catch (IllegalArgumentException | InvalidFileFormatException exception) {
            fail("Expected the file to be loaded since the input is valid.");
        }
    }

    /**
     * Tests if the getMunicipalityName works as intended with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the getMunicipalityName works as intended with invalid input.")
    public void TestIfGetMunicipalityNameWorksWithInvalidInput(){
        PostalFacade postalFacade = setUpTestSpace();
        try {
            postalFacade.getMunicipalityName(0000L);
            fail("Expected to get a exception since the number 0L is invalid format.");
        }catch (CouldNotGetCountyException | IllegalArgumentException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getMunicipalityName works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the getMunicipalityName works as intended with valid input.")
    public void TestIfGetMunicipalityNameWorksWithValidInput(){
        PostalFacade postalFacade = setUpTestSpace();
        try {
            postalFacade.getMunicipalityName(3451L);
            assertTrue(true);
        }catch (IllegalArgumentException | CouldNotGetCountyException exception){
            fail("Expected the postal facade to get a name since the input is valid.");
        }
    }

    /**
     * Tests if the getCounty method works as intended with invalid input.
     * Test type: Negative
     */
    @Test
    @DisplayName("Tests if the getCounty method works as intended with invalid input.")
    public void TestIFGetCountyWorksWithInvalidInput(){
        PostalFacade postalFacade = setUpTestSpace();
        try {
            postalFacade.getCounty(4001L);
            fail("Expected to get a exception since this number is not in the register.");
        }catch (IllegalArgumentException | CouldNotGetCountyException exception){
            assertTrue(true);
        }
    }

    /**
     * Tests if the getCounty method works as intended with valid input.
     * Test type: Positive
     */
    @Test
    @DisplayName("Tests if the getCounty method works as intended with valid input.")
    public void TestIfGetCountyWorksWithValidInput(){
        PostalFacade postalFacade = setUpTestSpace();
        try {
            postalFacade.getCounty(3451L);
            assertTrue(true);
        }catch (IllegalArgumentException | CouldNotGetCountyException exception){
            fail("Expected to get a county since its a valid input.");
        }
    }
}
