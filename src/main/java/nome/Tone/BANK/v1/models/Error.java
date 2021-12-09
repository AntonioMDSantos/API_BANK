package nome.Tone.BANK.v1.models;

public class Error {

    private final int status;
    private final String message;

    public Error(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static Error from(final int status) {
        switch (status) {
            case 400:
                return new Error(status, "Bad Request");
            case 404:
                return new Error(status, "Not Found");
            case 500:
                return new Error(status, "Internal Server Error");
            default:
                throw new IllegalArgumentException();
        }
    }
}
