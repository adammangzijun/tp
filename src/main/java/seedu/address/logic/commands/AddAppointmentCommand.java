package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.Nric;

/**
 * Adds an appointment to the address book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addappointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Eye Examination "
            + PREFIX_DATE + "18-09-2023 1800 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book";

    private final Index index;
    private final Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Index index, Appointment appointment) {
        requireNonNull(index);
        requireNonNull(appointment);
        this.toAdd = appointment;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = lastShownList.get(index.getZeroBased());
        Nric patientId = patient.getNric();
        Appointment newAppointment = new Appointment(toAdd.getName(), toAdd.getDateTime(), patientId);
        if (patient.hasAppointment(newAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        UniqueAppointmentList newAppointmentList = new UniqueAppointmentList();
        newAppointmentList.setAppointments(patient.getAppointments());
        newAppointmentList.add(newAppointment);

        Patient newPatient = new Patient(patient.getName(), patient.getNric(), patient.getEmail(),
                patient.getPhone(), patient.getGender(), patient.getAge(), patient.getBloodType(),
                patient.getAllergies(), patient.getRecords(), newAppointmentList, patient.isPinned());

        model.setPatient(patient, newPatient);

        model.resetAppointmentList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newAppointment, newPatient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        AddAppointmentCommand otherAddCommand = (AddAppointmentCommand) other;
        return toAdd.equals(otherAddCommand.toAdd) && index.equals(otherAddCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
