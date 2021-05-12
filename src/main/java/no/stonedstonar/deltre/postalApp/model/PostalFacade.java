package no.stonedstonar.deltre.postalApp.model;

import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddCountyException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotAddPostalInformationException;
import no.stonedstonar.deltre.postalApp.model.exceptions.CouldNotGetCountyException;
import no.stonedstonar.deltre.postalApp.model.exceptions.InvalidFileFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents the backbone of the application. Handles all the GUI interaction and logs the error that could occur in
 * the program.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalFacade {

    private static Long minLengthCountyAndMunicipalityNumber = 0000L;

    private static Long maxLengthCountyAndMunicipalityNumber = 9999L;

    private static Long minPostalCodeValue = 0000L;

    private static Long maxPostalCodeValue = 9999L;

    private PostalRegister postalRegister;

    private CountyRegister countyRegister;

    private Logger logger;


    /**
     * Makes an instance of the postal facade object.
     */
    public PostalFacade(){
        postalRegister = new PostalRegister();
        countyRegister = new CountyRegister();
        logger = Logger.getLogger(getClass().toString());
        addAllCounties();
    }

    /**
     * Gets the postal list from the register.
     * @return the list with all the postal information in the system.
     */
    public List<PostalInformation> getPostalRegister(){
        return postalRegister.getPostalList();
    }

    /**
     * Gets the municipality with that number if its in the system.
     * @param countyAndMunicipalityNumber the county and municipality number.
     * @return the name of the municipality if its in the system.
     * @throws CouldNotGetCountyException gets thrown if the county could not be found.
     */
    public String getMunicipalityName(long countyAndMunicipalityNumber) throws CouldNotGetCountyException {
        checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(countyAndMunicipalityNumber);
        String municipality;
        try {
            municipality = countyRegister.getMunicipality(countyAndMunicipalityNumber);
        }catch (CouldNotGetCountyException | IllegalArgumentException exception){
            logger.log(Level.WARNING, exception.getMessage());
            throw exception;
        }
        return municipality;
    }

    /**
     * Gets the county with that number if its in the system.
     * @param countyAndMunicipalityNumber the county and municipality number.
     * @return the county that mathces this number.
     * @throws CouldNotGetCountyException gets thrown if the county could not be found.
     */
    public County getCounty(long countyAndMunicipalityNumber) throws CouldNotGetCountyException {
        checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(countyAndMunicipalityNumber);
        County county;
        try {
            county = countyRegister.getCounty(countyAndMunicipalityNumber);
        }catch (CouldNotGetCountyException | IllegalArgumentException exception){
            logger.log(Level.WARNING, exception.getMessage());
            throw exception;
        }
        return county;
    }

    /**
     * Adds all the counties in Norway to the register.
     */
    private void addAllCounties(){
        try {
            countyRegister.addCountyWithName("Oslo", 0300L);
            countyRegister.addCountyWithName("Rogaland", 1100L);
            countyRegister.addCountyWithName("Møre og Romsdal", 1500L);
            countyRegister.addCountyWithName("Nordland", 1800L);
            countyRegister.addCountyWithName("Viken", 3000L);
            countyRegister.addCountyWithName("Innlandet", 3400L);
            countyRegister.addCountyWithName("Vestfold og Telemark", 3800L);
            countyRegister.addCountyWithName("Agder", 4200L);
            countyRegister.addCountyWithName("Vestland", 4600L);
            countyRegister.addCountyWithName("Trøndelag", 5000L);
            countyRegister.addCountyWithName("Troms og Finnmark", 5400L);
            countyRegister.addCountyWithName("Svalbard", 2100L);
            countyRegister.addCountyWithName("Jan Mayen", 2200L);
        }catch (CouldNotAddCountyException exception){
            logger.log(Level.WARNING, exception.getMessage());
        }
    }

    /**
     * Loads the standard file.
     * @param path the path of the file you want to load.
     * @throws InvalidFileFormatException gets thrown if the path is invalid, the file format is invalid or the file is empty.
     */
    private void loadFile(Path path) throws InvalidFileFormatException {
        if (path == null){
            throw new InvalidFileFormatException("The path cannot be null.");
        }
        List<String[]> listOfAllLines = new ArrayList<>();
        try(BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("Cp1252"))) {
            String lineToRead = bufferedReader.readLine();
            while (lineToRead != null){
                lineToRead = bufferedReader.readLine();
                if (lineToRead != null){
                    String[] listOfStrings = lineToRead.split("\t+");
                    listOfAllLines.add(listOfStrings);
                }
            }
        }catch (IOException exception){
          logger.log(Level.SEVERE, exception.getMessage());
        }
        if (listOfAllLines.isEmpty()){
            throw new InvalidFileFormatException("The file cannot be empty.");
        }
        for (String[] listOfAllLine : listOfAllLines) {
            checkIfFileIsRightFormat(listOfAllLine);
        }
        postalRegister.getPostalList().clear();
        countyRegister = new CountyRegister();
        addAllCounties();
        listOfAllLines.forEach(this::addNewInformation);
    }

    /**
     * Loads the file that you want to load.
     * @param file the file you want to load.
     * @throws InvalidFileFormatException gets thrown if the path is invalid, the file format is invalid or the file is empty.
     */
    public void loadSelectedFile(File file) throws InvalidFileFormatException {
        if (file.isFile()){
            Path path = file.toPath();
            try {
                loadFile(path);
            }catch (InvalidFileFormatException exception){
                logger.log(Level.WARNING, exception.getMessage());
                throw exception;
            }
        }else {
            throw new IllegalArgumentException("The file cannot be null.");
        }
    }

    /**
     * Adds all the post information objects and also adds the Counties and Municipalities.
     * @param listOfStrings the set of strings that you want to be made into objects.
     */
    private void addNewInformation(String[] listOfStrings){
        Long postalCode = Long.parseLong(listOfStrings[0]);
        String place = listOfStrings[1];
        Long countyAndMunicipalityNumber = Long.parseLong(listOfStrings[2]);
        String municipality = listOfStrings[3];
        String postCategory = listOfStrings[4];
        if (!countyRegister.checkIfCountyIsInRegister(countyAndMunicipalityNumber)){
            logger.log(Level.WARNING, "County is not found in the list: " + countyAndMunicipalityNumber);
        }

        try {
            if (!countyRegister.checkIfMunicipalityIsInCounty(countyAndMunicipalityNumber)) {
                addMunicipality(municipality, countyAndMunicipalityNumber);
            }
        }catch (CouldNotGetCountyException exception){
            logger.log(Level.WARNING, exception.getMessage());
        }

        try {
            addPostalInformation(place, postalCode, countyAndMunicipalityNumber);
        }catch (IllegalArgumentException exception){
            logger.log(Level.WARNING, exception.getMessage());
        }
    }

    /**
     * Adds a postal information object to the postal register.
     * @param nameOfPlace the name of the place.
     * @param postalCode the postal code of the place.
     * @param countyAndMunicipalityNumber the county and municipality number.
     */
    private void addPostalInformation(String nameOfPlace, Long postalCode, long countyAndMunicipalityNumber) {
        PostalInformation postalInformation = new PostalInformation(nameOfPlace, postalCode, countyAndMunicipalityNumber);
        try {
            postalRegister.addPostalInformation(postalInformation);
        }catch (CouldNotAddPostalInformationException exception){
            logger.log(Level.INFO, exception.getMessage());
        }
    }

    /**
     * Adds the wanted county to the county register.
     * @param nameOfCounty the name of the county you want to add.
     * @param countyAndMunicipalityNumber the county and municipality number that this county belongs to.
     */
    private void addCounty(String nameOfCounty, Long countyAndMunicipalityNumber) {
        try {
            checkString(nameOfCounty, "name of county");
            checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(countyAndMunicipalityNumber);
            countyRegister.addCountyWithName(nameOfCounty, countyAndMunicipalityNumber);
        }catch (CouldNotAddCountyException | IllegalArgumentException exception){
            logger.log(Level.WARNING, exception.getMessage());
        }
    }

    /**
     * Adds a municipality to the county it belongs to.
     * @param municipalityName the name of the new municipality.
     * @param countyAndMunicipalityNumber the county and municipality number.
     * @throws CouldNotGetCountyException gets thrown if the county could not be found.
     */
    private void addMunicipality(String municipalityName, Long countyAndMunicipalityNumber) throws CouldNotGetCountyException {
        try {
            checkString(municipalityName, "municipality name");
            checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(countyAndMunicipalityNumber);
            countyRegister.addMunicipalityToCounty(municipalityName, countyAndMunicipalityNumber);
        }catch (CouldNotGetCountyException | IllegalArgumentException exception){
            logger.log(Level.WARNING, exception.getMessage());
            throw exception;
        }
    }

    /**
     * Checks if the string is empty or null.
     * @param wordToCheck the word you want to check if its empty or null.
     * @param prefix the prefix you want in the exception.
     */
    public static void checkString(String wordToCheck, String prefix){
        if ((wordToCheck == null) || (wordToCheck.isEmpty())){
            String error = "empty";
            if (wordToCheck == null){
                error = "null";
            }
            throw new IllegalArgumentException("The " + prefix + " cannot be " + error + ".");
        }
    }

    /**
     * Checks if the CountyAndMunicipalityNumber is a valid format.
     * @param countyAndMunicipalityNumber the number of the county and the municipality number that you want to check .
     */
    public static void checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(Long countyAndMunicipalityNumber){
        if ((countyAndMunicipalityNumber == null) || (countyAndMunicipalityNumber <= minLengthCountyAndMunicipalityNumber) || (countyAndMunicipalityNumber > maxLengthCountyAndMunicipalityNumber)){
            String error = "must be between 0000 and 9999.";
            if (countyAndMunicipalityNumber == null){
                error = "cannot be null.";
            }
            throw new IllegalArgumentException("The county and municipality number or postal code " + error);
        }
    }

    /**
     * Checks if the input is the right format that this program needs.
     * @param listOfStrings the list of strings you want to check the format of.
     * @throws InvalidFileFormatException gets thrown if the list of strings are of the wrong format.
     */
    private void checkIfFileIsRightFormat(String[] listOfStrings) throws InvalidFileFormatException {
        String error = "The file is of wrong format.";
        if (listOfStrings.length != 5){
            throw new InvalidFileFormatException(error);
        }
        try {
            PostalInformation.checkIfPostalCodeIsValid(Long.parseLong(listOfStrings[0]));
            checkString(listOfStrings[1], "place name");
            checkIfCountyAndMunicipalityNumberOrPostalCodeIsValid(Long.parseLong(listOfStrings[2]));
            checkString(listOfStrings[3], "Municipality is the wrong format.");
            checkString(listOfStrings[4], "Postal letter for what postbox they have.");
        }catch (NumberFormatException exception){
            throw new InvalidFileFormatException(error + " " + exception.getMessage() + " a file read is of the wrong format.");
        }catch (IllegalArgumentException exception){
            throw new InvalidFileFormatException(error +  " " + exception.getMessage());
        }
    }




    /**
     * Gets the min value that the postal code should be.
     * @return the min value that a postal code should be.
     */
    public static Long getMinPostalCodeValue(){
        return minPostalCodeValue;
    }

    /**
     * Gets the max value that the postal code should be.
     * @return the max value that the postal code should be.
     */
    public static Long getMaxPostalCodeValue(){
        return maxPostalCodeValue;
    }
}
