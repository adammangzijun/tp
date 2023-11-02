package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Allergy;

public class JsonAdaptedAllergyTest {
    private static final String INVALID_ALLERGY = "Dust+";

    private static final String INVALID_ALLERGY2 = "";

    private static final Allergy VALID_ALLERGY = new Allergy("Dust");


    @Test
    public void toModelType_validRecordDetails_returnsCondition() throws Exception {
        JsonAdaptedAllergy allergy = new JsonAdaptedAllergy(VALID_ALLERGY);
        assertEquals(VALID_ALLERGY, allergy.toModelType());
    }

    @Test
    public void toModelType_invalidCondition_throwsIllegalArgumentException() {
        JsonAdaptedAllergy allergy = new JsonAdaptedAllergy(INVALID_ALLERGY);
        String expectedMessage = Allergy.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, allergy::toModelType);
    }
    @Test
    public void toModelType_invalidCondition2_throwsIllegalArgumentException() {
        JsonAdaptedAllergy allergy = new JsonAdaptedAllergy(INVALID_ALLERGY2);
        String expectedMessage = Allergy.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, allergy::toModelType);
    }
    @Test
    public void toModelType_nullCondition_throwsIllegalValueException() {
        JsonAdaptedAllergy allergy = new JsonAdaptedAllergy((String) null);
        String expectedMessage = Allergy.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, allergy::toModelType);
    }

}
