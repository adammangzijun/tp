package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Pins the patient identified using it's displayed index from the address book.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the patient identified by the index number used in the displayed patient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PATIENT_SUCCESS = "Pinned Patient: %1$s";

    private final Index targetIndex;

    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToPin = lastShownList.get(targetIndex.getZeroBased());
        Patient pinnedPatient = new Patient(patientToPin.getName(), patientToPin.getNric(), patientToPin.getEmail(),
                patientToPin.getPhone(), patientToPin.getGender(), patientToPin.getAge(), patientToPin.getBloodType(),
                patientToPin.getAllergies(), patientToPin.getRecords(), patientToPin.getAppointments(), true);

        model.setPatient(patientToPin, pinnedPatient);
        return new CommandResult(String.format(MESSAGE_PIN_PATIENT_SUCCESS, Messages.format(patientToPin)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PinCommand)) {
            return false;
        }

        PinCommand otherPinCommand = (PinCommand) other;
        return targetIndex.equals(otherPinCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

