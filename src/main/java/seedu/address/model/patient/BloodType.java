package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Patient's blood type in the MedBook.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidBloodType(String)}
 */
public class BloodType {
    public static final String
        MESSAGE_CONSTRAINTS = "Blood Type should only be one of the following: A+, A-, B+, B-, AB+, AB-, O+, O-";

    public static final String[] POSSIBLE_BLOOD_TYPES = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };

    public final String bloodType;

    /**
     * Constructs a {@code Gender}.
     *
     * @param bloodType A valid blood type.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_CONSTRAINTS);
        this.bloodType = bloodType;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidBloodType(String test) {
        for (int i = 0; i < POSSIBLE_BLOOD_TYPES.length; i++) {
            if (test.equals(POSSIBLE_BLOOD_TYPES[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return this.bloodType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BloodType)) {
            return false;
        }

        BloodType otherBloodType = (BloodType) other;
        return Objects.equals(this.bloodType, otherBloodType.bloodType);
    }

    @Override
    public int hashCode() {
        return this.bloodType.hashCode();
    }
}
