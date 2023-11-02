package seedu.address.model.shared;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's Nric in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS = "NRIC should follow the format: a letter, "
            + "followed by seven digits, and then another letter";

    /*
     * The Nric starts with a letter (either uppercase or lowercase),
     * followed by seven digits, and ends with another letter (either uppercase or
     * lowercase).
     */
    public static final String VALIDATION_REGEX = "[A-Za-z]\\d{7}[A-Za-z]";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid Nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        this.nric = nric;
    }

    /**
     * Returns true if a given string is a valid Nric.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Nric)) {
            return false;
        }

        Nric otherNric = (Nric) other;
        return nric.equals(otherNric.nric);
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }

}
