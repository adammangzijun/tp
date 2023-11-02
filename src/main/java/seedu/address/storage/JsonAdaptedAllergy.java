package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Allergy;

/**
 * Jackson-friendly version of {@link Allergy}.
 */
public class JsonAdaptedAllergy {
    private final String allergy;

    /**
     * Constructs a {@code JsonAdaptedAllergy} with the given {@code allergy}.
     */

    @JsonCreator
    public JsonAdaptedAllergy(String allergy) {
        this.allergy = allergy;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedAllergy(Allergy source) {
        this.allergy = source.allergy;
    }

    @JsonValue
    public String getAllergy() {
        return allergy;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Allergy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted allergy.
     */

    public Allergy toModelType() throws IllegalValueException {
        if (allergy == null) {
            throw new IllegalValueException(Allergy.MESSAGE_CONSTRAINTS);
        }
        if (!Allergy.isValidAllergy(allergy)) {
            throw new IllegalValueException(Allergy.MESSAGE_CONSTRAINTS);
        }
        return new Allergy(allergy);
    }
}
