package no.stonedstonar.deltre.postalApp.model.exceptions;

/**
 * Represents a exception that is thrown when a postal information object is added and its invalid.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddPostalInformationException extends Exception{

    private static long version = 1L;

    /**
     * Makes an instance of the CouldNotAddPostalInformationException
     * @param message the error message that the exception should contain.
     */
    public CouldNotAddPostalInformationException(String message){
        super(message);
    }
}
