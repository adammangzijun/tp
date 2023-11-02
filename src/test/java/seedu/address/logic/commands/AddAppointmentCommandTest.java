package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PatientBuilder;

public class AddAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(INDEX_FIRST_PATIENT, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {

        Patient patientToAddAppointment = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Appointment validAppointment = new AppointmentBuilder().withNric(patientToAddAppointment.getNric().toString())
                .build();

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(INDEX_FIRST_PATIENT, validAppointment);

        UniqueAppointmentList newAppointmentList = new UniqueAppointmentList();
        newAppointmentList.setAppointments(patientToAddAppointment.getAppointments());
        newAppointmentList.add(validAppointment);

        Patient editedPatient = new PatientBuilder(patientToAddAppointment).withAppointments(newAppointmentList).build();

        String expectedMessage = String.format(AddAppointmentCommand.MESSAGE_SUCCESS,
                Messages.format(validAppointment, editedPatient));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.resetAppointmentList();

        assertCommandSuccess(addAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Appointment validAppointment = new AppointmentBuilder().build();
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(outOfBoundIndex, validAppointment);

        assertCommandFailure(addAppointmentCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        Patient patientToAddAppointment = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Appointment validAppointment = new AppointmentBuilder().withNric(patientToAddAppointment.getNric().toString())
                .build();

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(INDEX_FIRST_PATIENT, validAppointment);

        UniqueAppointmentList newAppointmentList = new UniqueAppointmentList();
        newAppointmentList.setAppointments(patientToAddAppointment.getAppointments());
        newAppointmentList.add(validAppointment);

        Patient editedPatient = new PatientBuilder(patientToAddAppointment).withAppointments(newAppointmentList).build();

        String expectedMessage = String.format(AddAppointmentCommand.MESSAGE_SUCCESS,
                Messages.format(validAppointment, editedPatient));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPatientAtIndex(expectedModel, INDEX_FIRST_PATIENT);
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.resetAppointmentList();

        assertCommandSuccess(addAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Appointment validAppointment = new AppointmentBuilder().build();
        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPatientList().size());

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(outOfBoundIndex, validAppointment);

        assertCommandFailure(addAppointmentCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateAppointmentUnfilteredList_failure() {
        Appointment appointment = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased())
                .getAppointments().asUnmodifiableObservableList().get(0);
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(INDEX_FIRST_PATIENT, appointment);

        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
    }

    @Test
    public void equals() {
        Appointment eyeExam = new AppointmentBuilder().withName("Eye Exam").build();
        Appointment earExam = new AppointmentBuilder().withName("Ear Exam").build();

        final AddAppointmentCommand standardCommand = new AddAppointmentCommand(INDEX_FIRST_PATIENT, eyeExam);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new HelpCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddAppointmentCommand(INDEX_SECOND_PATIENT, eyeExam)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddAppointmentCommand(INDEX_FIRST_PATIENT, earExam)));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Appointment appointment = new AppointmentBuilder().build();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(targetIndex, appointment);
        String expected = new ToStringBuilder(addAppointmentCommand)
                .add("toAdd", appointment)
                .toString();
        assertEquals(expected, addAppointmentCommand.toString());
    }
}
