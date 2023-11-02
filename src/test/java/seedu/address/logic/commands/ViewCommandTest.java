package seedu.address.logic.commands;

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

public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeValidIndex_success() {
        Patient patientToView = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PATIENT_SUCCESS, Messages.format(patientToView));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.setPatient(model.getFilteredPatientList().get(0), patientToView);
        expectedModel.updateRecordList(patientToView);
        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndex_throwsCommandException() {
        Index indexOutOfBounds = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(indexOutOfBounds);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexRecordList_success() {

        Patient patientToView = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PATIENT_SUCCESS,
                Messages.format(patientToView));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        try {
            viewCommand.execute(model);
            viewCommand.execute(expectedModel);
        } catch (Exception ignore) {
            return;
        }

        sameRecordList(model, expectedModel);
        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexSamePatient_success() {

        Patient patientToView = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_PATIENT_SUCCESS,
                Messages.format(patientToView));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        try {
            viewCommand.execute(model);
            viewCommand.execute(expectedModel);
        } catch (Exception ignore) {
            return;
        }

        samePatientViewed(model, expectedModel);
        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {

        ViewCommand viewCommand1 = new ViewCommand(INDEX_FIRST_PATIENT);
        ViewCommand viewCommand2 = new ViewCommand(INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertTrue(viewCommand1.equals(viewCommand1));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PATIENT);
        assertTrue(viewCommand1.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewCommand1.equals(1));

        // null -> returns false
        assertFalse(viewCommand1.equals(null));

        // different patient -> returns false
        assertFalse(viewCommand1.equals(viewCommand2));
    }

    private void sameRecordList(Model model1, Model model2) {
        assertTrue(model1.getRecordList().equals(model2.getRecordList()));
    }

    private void samePatientViewed(Model model1, Model model2) {
        assertTrue(model1.getPatientBeingViewed().equals(model2.getPatientBeingViewed()));
    }
}
