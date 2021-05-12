package no.stonedstonar.deltre.postalApp.model.exceptions;

/**
 * A exception that gets thrown when the file is of a wrong format.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class InvalidFileFormatException extends Exception{

    private static long version = 1L;

    /**
     * Makes an instance of the InvalidFileFormatException.
     * @param message the error message that the exception should contain.
     */
    public InvalidFileFormatException(String message){
        super(message);
    }
}
