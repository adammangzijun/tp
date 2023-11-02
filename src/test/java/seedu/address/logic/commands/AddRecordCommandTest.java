package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecords.ALLERGIC_REACTION2;
import static seedu.address.testutil.TypicalRecords.FEVER0;
import static seedu.address.testutil.TypicalRecords.FEVER_AND_COLD0;
import static seedu.address.testutil.TypicalRecords.FEVER_AND_COLD2;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.testutil.PatientBuilder;

public class AddRecordCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientToAddRecord = model.getFilteredPatientList().get(INDEX_THIRD_PATIENT.getZeroBased());
        UniqueRecordList records = new UniqueRecordList();
        records.setRecords(patientToAddRecord.getRecords());
        records.add(FEVER_AND_COLD2);

        Patient patientWithAddedRecord = new PatientBuilder(patientToAddRecord).withRecords(records).build();
        AddRecordCommand addRecordCommand = new AddRecordCommand(INDEX_THIRD_PATIENT, FEVER_AND_COLD2);

        String expectedMessage = String.format(AddRecordCommand.MESSAGE_SUCCESS,
                Messages.format(FEVER_AND_COLD2, patientWithAddedRecord));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(2), patientWithAddedRecord);
        expectedModel.updateRecordList(patientWithAddedRecord);
        assertCommandSuccess(addRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        AddRecordCommand addRecordCommand = new AddRecordCommand(outOfBoundIndex, ALLERGIC_REACTION2);

        assertCommandFailure(addRecordCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient patientToAddRecord = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        UniqueRecordList records = new UniqueRecordList();
        records.setRecords(patientToAddRecord.getRecords());
        records.add(FEVER_AND_COLD0);

        Patient patientWithAddedRecord = new PatientBuilder(patientToAddRecord).withRecords(records).build();
        AddRecordCommand addRecordCommand = new AddRecordCommand(INDEX_FIRST_PATIENT, FEVER_AND_COLD0);

        String expectedMessage = String.format(AddRecordCommand.MESSAGE_SUCCESS,
                Messages.format(FEVER_AND_COLD0, patientWithAddedRecord));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPatientAtIndex(expectedModel, INDEX_FIRST_PATIENT);

        expectedModel.setPatient(model.getFilteredPatientList().get(0), patientWithAddedRecord);
        expectedModel.updateRecordList(patientWithAddedRecord);
        assertCommandSuccess(addRecordCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPatientList().size());

        AddRecordCommand addRecordCommand = new AddRecordCommand(outOfBoundIndex, FEVER0);

        assertCommandFailure(addRecordCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddRecordCommand addRecordCommand1 = new AddRecordCommand(INDEX_FIRST_PATIENT, FEVER0);
        AddRecordCommand addRecordCommand2 = new AddRecordCommand(INDEX_FIRST_PATIENT, FEVER0);
        AddRecordCommand addRecordCommand3 = new AddRecordCommand(INDEX_THIRD_PATIENT, ALLERGIC_REACTION2);

        assertTrue(addRecordCommand1.equals(addRecordCommand1));
        assertTrue(addRecordCommand1.equals(addRecordCommand2));
        assertFalse(addRecordCommand1.equals(1));
        assertFalse(addRecordCommand1.equals(null));
        assertFalse(addRecordCommand1.equals(addRecordCommand3));

    }

    @Test
    public void toStringMethod() {
        AddRecordCommand addRecordCommand = new AddRecordCommand(INDEX_FIRST_PATIENT, FEVER0);
        String expected = AddRecordCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PATIENT + ", "
                + "record=" + FEVER0 + "}";

        assertEquals(expected, addRecordCommand.toString());

    }
}
