package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FIRST_REC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_DIARRHEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_HEAT_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_SLEEP_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_THYROID_CHECK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditRecordCommand.EditRecordDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.testutil.EditRecordDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.RecordBuilder;

public class EditRecordCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient patientToEdit = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        UniqueRecordList oldList = patientToEdit.getRecords();
        Record recordToEdit = oldList.asUnmodifiableObservableList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record editedRecord = new RecordBuilder(recordToEdit)
                .withDateTime(VALID_DATETIME_THYROID_CHECK)
                .withConditions(VALID_CONDITION_DIARRHEA).withPatientIndex(0).build();
        UniqueRecordList newList = new UniqueRecordList();
        newList.setRecords(oldList);
        newList.setRecord(recordToEdit, editedRecord);

        Patient editedPatient = new PatientBuilder(patientToEdit).withRecords(newList).build();
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(recordToEdit)
                .withDateTime(VALID_DATETIME_THYROID_CHECK)
                .withConditions(VALID_CONDITION_DIARRHEA).withPatientIndex(0).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT, INDEX_FIRST_RECORD,
                descriptor);

        String expectedMessage = String.format(EditRecordCommand.MESSAGE_EDIT_RECORD_SUCCESS,
                Messages.format(editedRecord, editedPatient));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.updateRecordList(editedPatient);

        assertCommandSuccess(editRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        UniqueRecordList oldList = firstPatient.getRecords();
        Record recordToEdit = oldList.asUnmodifiableObservableList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record editedRecord = new RecordBuilder(recordToEdit)
                .withDateTime(VALID_DATETIME_SLEEP_STUDY).build();
        UniqueRecordList newList = new UniqueRecordList();
        newList.setRecords(oldList);
        newList.setRecord(recordToEdit, editedRecord);

        PatientBuilder personInList = new PatientBuilder(firstPatient);
        Patient editedPatient = personInList.withRecords(newList).build();
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withDateTime(VALID_DATETIME_SLEEP_STUDY).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT, INDEX_FIRST_RECORD,
                descriptor);

        String expectedMessage = String.format(EditRecordCommand.MESSAGE_EDIT_RECORD_SUCCESS, Messages.format(
                editedRecord, editedPatient));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.updateRecordList(editedPatient);

        assertCommandSuccess(editRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT, INDEX_FIRST_RECORD,
                new EditRecordCommand.EditRecordDescriptor());

        assertCommandFailure(editRecordCommand, model, EditRecordCommand.MESSAGE_DUPLICATE_RECORD);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient patientInFilteredList = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        UniqueRecordList oldList = patientInFilteredList.getRecords();
        Record recordToEdit = oldList.asUnmodifiableObservableList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record editedRecord = new RecordBuilder(recordToEdit).withConditions(VALID_CONDITION_HEAT_STROKE)
                .withDateTime(VALID_DATETIME_THYROID_CHECK)
                .withPatientIndex(0).build();
        UniqueRecordList newList = new UniqueRecordList();
        newList.setRecords(oldList);
        newList.setRecord(recordToEdit, editedRecord);

        Patient editedPatient = new PatientBuilder(patientInFilteredList).withRecords(newList).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT, INDEX_FIRST_RECORD,
                new EditRecordDescriptorBuilder().withConditions(VALID_CONDITION_HEAT_STROKE)
                        .withDateTime(VALID_DATETIME_THYROID_CHECK)
                        .withPatientIndex(0).build());
        String expectedMessage = String.format(EditRecordCommand.MESSAGE_EDIT_RECORD_SUCCESS,
                Messages.format(editedRecord, editedPatient));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPatientAtIndex(expectedModel, INDEX_FIRST_PATIENT);
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.updateRecordList(editedPatient);

        assertCommandSuccess(editRecordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRecordUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        UniqueRecordList uniqueRecordList = firstPatient.getRecords();
        Record firstRecord = uniqueRecordList.asUnmodifiableObservableList()
                .get(INDEX_FIRST_RECORD.getZeroBased());
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(firstRecord)
                .build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT,
                INDEX_FIRST_RECORD, descriptor);
        assertCommandFailure(editRecordCommand, model, EditRecordCommand.MESSAGE_DUPLICATE_RECORD);
    }

    @Test
    public void execute_duplicateRecordFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient patientInList = model.getAddressBook().getPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        UniqueRecordList uniqueRecordList = patientInList.getRecords();
        Record firstRecord = uniqueRecordList.asUnmodifiableObservableList()
                .get(INDEX_FIRST_RECORD.getZeroBased());
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT, INDEX_FIRST_RECORD,
                new EditRecordDescriptorBuilder(firstRecord).build());

        assertCommandFailure(editRecordCommand, model, EditRecordCommand.MESSAGE_DUPLICATE_RECORD);
    }

    @Test
    public void execute_invalidRecordIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredPatientList().get(
                INDEX_FIRST_PATIENT.getZeroBased()).getRecords().asUnmodifiableObservableList().size());
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withDateTime(VALID_DATETIME_THYROID_CHECK).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT, outOfBoundIndex,
                descriptor);

        assertCommandFailure(editRecordCommand, model, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withDateTime(VALID_DATETIME_THYROID_CHECK).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(outOfBoundIndex, INDEX_FIRST_RECORD,
                descriptor);

        assertCommandFailure(editRecordCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void toStringTest() {
        EditRecordDescriptor editedFirstRecord = new EditRecordDescriptorBuilder(
                DESC_FIRST_REC)
                        .withDateTime(VALID_DATETIME_THYROID_CHECK).build();
        EditRecordCommand editRecordCommand = new EditRecordCommand(INDEX_FIRST_PATIENT,
                INDEX_FIRST_RECORD, editedFirstRecord);
        String expected = EditRecordCommand.class.getCanonicalName() + "{patientIndex=" + INDEX_FIRST_PATIENT
                + ", "
                + "recordIndex=" + INDEX_FIRST_RECORD + ", "
                + "editRecordDescriptor=" + editedFirstRecord + "}";

        assertEquals(expected, editRecordCommand.toString());
    }

}
