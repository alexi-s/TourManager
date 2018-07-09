package exception;

public class CountryExistsException extends Exception {

    @Override
    public String getMessage() {
        return "Country exists";
    }
}
