package no.stonedstonar.deltre.postalApp.model.exceptions;

/**
 * Represents a exception that gets thrown when a postal information object does not exsist.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetPostalInformationException extends Exception{

    private static long version = 1L;

    /**
     * Makes an instance of the CouldNotGetPostalInformationException.
     * @param message the error message that the exception should contain.
     */
    public CouldNotGetPostalInformationException(String message){
        super(message);
    }
}
