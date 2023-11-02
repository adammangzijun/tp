package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an allergy in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAllergy(String)}
 */
public class Allergy {

    public static final String MESSAGE_CONSTRAINTS = "Allergy names should be alphanumeric";

    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String allergy;

    /**
     * Constructs a {@code Allergy}.
     *
     * @param allergy A valid allergy name.
     */

    public Allergy(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidAllergy(allergy), MESSAGE_CONSTRAINTS);
        this.allergy = allergy;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidAllergy(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Allergy)) {
            return false;
        }

        Allergy otherAllergy = (Allergy) other;
        return allergy.equals(otherAllergy.allergy);
    }

    @Override
    public int hashCode() {
        return allergy.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + allergy + ']';
    }

}
