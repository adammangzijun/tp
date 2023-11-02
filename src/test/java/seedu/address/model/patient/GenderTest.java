package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.shared.Name;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidGender = "C";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));
    }
    @Test
    public void equals() {
        Gender gender = new Gender("M");
        assertTrue(gender.equals(gender));
        assertTrue(gender.equals(new Gender("M")));
        assertFalse(gender.equals(new Gender("F")));
        assertFalse(gender.equals(new Name("Alice Pauline")));
    }
}
