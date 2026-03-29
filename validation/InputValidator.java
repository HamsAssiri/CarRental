package validation;
public class InputValidator {

    private static final int MAX_PASSENGERS = 7;
    private static final int MAX_DAYS = 365;
    private static final int MAX_MILEAGE = 100000;

    public static boolean validatePassengers(int p) {
        return p > 0 && p <= MAX_PASSENGERS;
    }

    public static boolean validateDays(int d) {
        return d > 0 && d <= MAX_DAYS;
    }

    public static boolean validateMileage(int m) {
        return m > 0 && m <= MAX_MILEAGE;
    }
}