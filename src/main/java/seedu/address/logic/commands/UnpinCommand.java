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
 * Unpins the patient identified using it's displayed index from the address book.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpins the patient identified by the index number used in the displayed pinned list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPIN_PATIENT_SUCCESS = "Unpinned Patient: %1$s";

    private final Index targetIndex;

    public UnpinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownPinnedList = model.getPinnedPatientList();

        if (targetIndex.getZeroBased() >= lastShownPinnedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToUnpin = lastShownPinnedList.get(targetIndex.getZeroBased());
        Patient unpinnedPatient = new Patient(patientToUnpin.getName(), patientToUnpin.getNric(), patientToUnpin.getEmail(),
                patientToUnpin.getPhone(), patientToUnpin.getGender(), patientToUnpin.getAge(),
                patientToUnpin.getBloodType(), patientToUnpin.getAllergies(), patientToUnpin.getRecords(),
                patientToUnpin.getAppointments(), false);

        model.setPatient(patientToUnpin, unpinnedPatient);
        return new CommandResult(String.format(MESSAGE_UNPIN_PATIENT_SUCCESS, Messages.format(patientToUnpin)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnpinCommand)) {
            return false;
        }

        UnpinCommand otherUnpinCommand = (UnpinCommand) other;
        return targetIndex.equals(otherUnpinCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

