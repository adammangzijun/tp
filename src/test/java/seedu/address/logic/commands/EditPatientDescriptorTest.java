package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.testutil.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPatientDescriptor descriptorWithSameValues = new EditPatientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPatientDescriptor editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different gender -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different age -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withAge(VALID_AGE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different bloodType -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withBloodType(VALID_BLOODTYPE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different allergies -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withAllergies(VALID_ALLERGY_PEANUTS).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();
        String expected = EditPatientDescriptor.class.getCanonicalName() + "{name="
                + editPatientDescriptor.getNric().orElse(null) + ", nric="
                + editPatientDescriptor.getName().orElse(null) + ", email="
                + editPatientDescriptor.getEmail().orElse(null) + ", phone="
                + editPatientDescriptor.getPhone().orElse(null) + ", gender="
                + editPatientDescriptor.getGender().orElse(null) + ", age="
                + editPatientDescriptor.getAge().orElse(null) + ", bloodType="
                + editPatientDescriptor.getBloodType().orElse(null) + ", allergies="
                + editPatientDescriptor.getAllergies().orElse(null) + "}";
        assertEquals(expected, editPatientDescriptor.toString());
    }
}
