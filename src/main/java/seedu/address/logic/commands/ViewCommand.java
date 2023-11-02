package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Views patient detailed information at the specified index
 * and list medical records of the patient.
 */

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": display the detailed information of the patient identified by the index given"
            + " and list all the existing medical records of patient.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PATIENT_SUCCESS = "View Patient: %1$s";

    private final Index targetIndex;

    /**
     * Constructor for ViewCommand class
     * @param targetIndex the patient Index to view
     */
    public ViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToView = lastShownList.get(targetIndex.getZeroBased());
        model.updateRecordList(patientToView);
        return new CommandResult(String.format(MESSAGE_VIEW_PATIENT_SUCCESS, Messages.format(patientToView)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand v = (ViewCommand) other;
        return this.targetIndex.equals(v.targetIndex);
    }
}
