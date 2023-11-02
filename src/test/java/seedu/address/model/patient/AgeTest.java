package seedu.address.model.patient;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.shared.Name;

public class AgeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        Integer invalidAge = -1;
        assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));

    }

    @Test
    public void isValidAge() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Age.isValidAge(null));
    }
    @Test
    public void equals() {
        Age age = new Age(18);
        assertTrue(age.equals(age));
        assertTrue(age.equals(new Age(18)));
        assertFalse(age.equals(new Age(20)));
        assertFalse(age.equals(new Name("Alice Pauline")));
    }

}
