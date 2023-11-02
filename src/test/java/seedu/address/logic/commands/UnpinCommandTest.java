package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnpinCommand.
 */
public class UnpinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Patient patientToUnpin = model.getPinnedPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient unpinnedPatient = new PatientBuilder(patientToUnpin).withIsPinned(false).build();
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PATIENT_SUCCESS,
                Messages.format(patientToUnpin));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getPinnedPatientList().get(0), unpinnedPatient);

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPinnedPatientList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PATIENT);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        UnpinCommand unpinFirstCommandCopy = new UnpinCommand(INDEX_FIRST_PATIENT);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unpinFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnpinCommand unpinCommand = new UnpinCommand(targetIndex);
        String expected = UnpinCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unpinCommand.toString());
    }

}
