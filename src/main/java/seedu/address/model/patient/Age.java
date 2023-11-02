package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's age in the MedBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(Integer)}
 */
public class Age {
    public static final String MESSAGE_CONSTRAINTS =
            "Age should only be a positive integer or 0";

    public final Integer age;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(Integer age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        this.age = age;
    }

    /**
     * Returns true if a given integer is a valid age.
     */
    public static boolean isValidAge(Integer test) {
        return test > 0;
    }

    @Override
    public String toString() {
        return String.valueOf(this.age);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Age)) {
            return false;
        }

        Age otherAge = (Age) other;
        return this.age == otherAge.age;
    }

    @Override
    public int hashCode() {
        return this.age.hashCode();
    }

}
