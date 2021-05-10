package no.stonedstonar.deltre.postalApp.model.exceptions;

/**
 * Represents a exception that gets thrown when a county could not be added to a register.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddCountyException extends Exception{

    private static long version = 1L;

    /**
     * Makes an instance of the CouldNotAddCountyException.
     * @param message the error message that the exception should contain.
     */
    public CouldNotAddCountyException(String message){
        super(message);
    }
}
