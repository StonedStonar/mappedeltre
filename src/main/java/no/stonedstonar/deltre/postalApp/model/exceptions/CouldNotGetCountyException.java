package no.stonedstonar.deltre.postalApp.model.exceptions;

/**
 * Represents a exception that is thrown when a county with that county number could not be found in the register.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetCountyException extends Exception{

    private static long version = 1L;

    /**
     * Makes an instance of the CouldNotGetCountyException.
     * @param message the error message that the exception should contain.
     */
    public CouldNotGetCountyException(String message){
        super(message);
    }
}
