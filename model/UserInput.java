package model;

import validation.InputValidator;

public class UserInput {

    private final int passengers;
    private final int days;
    private final int mileage;

    public UserInput(int passengers, int days, int mileage) {
        // Fail safe + Least privilege + Minimize trust
        if (!InputValidator.validatePassengers(passengers)) {
            throw new IllegalArgumentException("Invalid number of passengers");
        }
        if (!InputValidator.validateDays(days)) {
            throw new IllegalArgumentException("Invalid number of rental days");
        }
        if (!InputValidator.validateMileage(mileage)) {
            throw new IllegalArgumentException("Invalid mileage");
        }

        this.passengers = passengers;
        this.days = days;
        this.mileage = mileage;
    }

    public int getPassengers() {
        return passengers;
    }

    public int getDays() {
        return days;
    }

    public int getMileage() {
        return mileage;
    }
}