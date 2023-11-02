package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.shared.Name;

public class BloodTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidBloodType = "C";
        assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null tag name
        assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));
    }

    @Test
    public void equals() {
        BloodType bt = new BloodType("AB+");
        assertTrue(bt.equals(bt));
        assertTrue(bt.equals(new BloodType("AB+")));
        assertFalse(bt.equals(new BloodType("AB-")));
        assertFalse(bt.equals(new Name("Alice Pauline")));
    }
}
