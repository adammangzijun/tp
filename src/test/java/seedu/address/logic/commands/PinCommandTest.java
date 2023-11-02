package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
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
 * Contains integration tests (interaction with the Model) and unit tests for PinCommand.
 */
public class PinCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientToPin = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient pinnedPatient = new PatientBuilder(patientToPin).withIsPinned(true).build();
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PATIENT_SUCCESS, Messages.format(patientToPin));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), pinnedPatient);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        PinCommand pinCommand = new PinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }
    @Test
    public void execute_validIndexFilteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient patientToPin = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient pinnedPatient = new PatientBuilder(patientToPin).withIsPinned(true).build();
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PATIENT_SUCCESS, Messages.format(patientToPin));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPatientAtIndex(expectedModel, INDEX_FIRST_PATIENT);
        expectedModel.setPatient(model.getFilteredPatientList().get(0), pinnedPatient);
        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPatientList().size());

        PinCommand pinCommand = new PinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_PATIENT);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertTrue(pinFirstCommand.equals(pinFirstCommand));

        // same values -> returns true
        PinCommand unpinFirstCommandCopy = new PinCommand(INDEX_FIRST_PATIENT);
        assertTrue(pinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(pinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(pinFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(pinFirstCommand.equals(pinSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PinCommand pinCommand = new PinCommand(targetIndex);
        String expected = PinCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, pinCommand.toString());
    }

}
