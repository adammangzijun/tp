package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.shared.Name;

public class AllergyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Allergy.isValidAllergy(null));
    }

    @Test
    public void equals() {
        Allergy allergy = new Allergy("Peanut");
        assertTrue(allergy.equals(allergy));
        assertTrue(allergy.equals(new Allergy("Peanut")));
        assertFalse(allergy.equals(new Allergy("Dust")));
        assertFalse(allergy.equals(new Name("Alice Pauline")));
    }

}
