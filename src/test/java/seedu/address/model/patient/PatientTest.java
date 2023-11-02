package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PatientBuilder;

public class PatientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient patient = new PatientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> patient.getAllergies().remove(0));
    }

    @Test
    public void isSamePatient() {
        // same object -> returns true
        assertTrue(ALICE.isSamePatient(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePatient(null));

        // same nric, all other attributes different -> returns true
        Patient editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withGender(VALID_GENDER_BOB).withAge(VALID_AGE_BOB).withBloodType(VALID_BLOODTYPE_BOB)
                .withAllergies(VALID_ALLERGY_PEANUTS).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

        // different nric, all other attributes same -> returns false
        editedAlice = new PatientBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Patient editedBob = new PatientBuilder(BOB).withNric(VALID_NRIC_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePatient(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient aliceCopy = new PatientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different patient -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PatientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different gender -> returns false
        editedAlice = new PatientBuilder(ALICE).withGender(VALID_GENDER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different age -> returns false
        editedAlice = new PatientBuilder(ALICE).withAge(VALID_AGE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different bloodType -> returns false
        editedAlice = new PatientBuilder(ALICE).withBloodType(VALID_BLOODTYPE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different allergies -> returns false
        editedAlice = new PatientBuilder(ALICE).withAllergies(VALID_ALLERGY_PEANUTS).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Patient.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", nric=" + ALICE.getNric()
                + ", email=" + ALICE.getEmail()
                + ", phone=" + ALICE.getPhone()
                + ", gender=" + ALICE.getGender()
                + ", age=" + ALICE.getAge()
                + ", bloodType=" + ALICE.getBloodType()
                + ", allergies=" + ALICE.getAllergies()
                + ", records=" + ALICE.getRecords()
                + ", appointments=" + ALICE.getAppointments()
                + ", isPinned=" + ALICE.isPinned()
                + "}";
        assertEquals(expected, ALICE.toString());
    }
}
