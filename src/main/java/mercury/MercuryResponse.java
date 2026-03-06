package mercury;

/**
 * Represents a response from Mercury, carrying the message and whether it is an error.
 */
public class MercuryResponse {
    private final String message;
    private final boolean isError;

    /**
     * Constructs a MercuryResponse.
     *
     * @param message The response message.
     * @param isError True if the response represents an error condition.
     */
    public MercuryResponse(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    /**
     * Returns the response message.
     *
     * @return The message string.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns whether this response is an error.
     *
     * @return True if this is an error response.
     */
    public boolean isError() {
        return isError;
    }
}
