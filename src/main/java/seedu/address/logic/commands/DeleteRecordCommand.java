package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;

/**
 * Deletes a record identified using it's displayed patient index and record
 * index from the address book.
 */
public class DeleteRecordCommand extends Command {
    public static final String COMMAND_WORD = "deleterecord";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the record identified by the index number used "
            + "in the displayed patient list and record list.\n"
            + "Parameters: PATIENT INDEX/RECORD INDEX (Both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1/1";

    public static final String MESSAGE_DELETE_RECORD_SUCCESS = "Deleted Record: %1$s";

    private final Index targetPatientIndex;
    private final Index targetRecordIndex;

    /**
     * Creates DeleteRecordCommand object
     */
    public DeleteRecordCommand(Index targetPatientIndex, Index targetRecordIndex) {
        this.targetPatientIndex = targetPatientIndex;
        this.targetRecordIndex = targetRecordIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (targetPatientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }
        Patient targetPatient = lastShownList.get(targetPatientIndex.getZeroBased());
        UniqueRecordList newRecordsList = new UniqueRecordList();
        newRecordsList.setRecords(targetPatient.getRecords());

        if (targetRecordIndex.getZeroBased() >= newRecordsList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record targetRecord = newRecordsList.get(targetRecordIndex.getZeroBased());
        newRecordsList.remove(targetRecord);
        Patient patientWithDeletedRecord = new Patient(targetPatient.getName(), targetPatient.getNric(),
                targetPatient.getEmail(), targetPatient.getPhone(), targetPatient.getGender(), targetPatient.getAge(),
                targetPatient.getBloodType(), targetPatient.getAllergies(), newRecordsList,
                targetPatient.getAppointments(), targetPatient.isPinned());
        model.setPatient(targetPatient, patientWithDeletedRecord);
        model.updateRecordList(patientWithDeletedRecord);
        return new CommandResult(String.format(MESSAGE_DELETE_RECORD_SUCCESS,
                Messages.format(targetRecord, targetPatient)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DeleteRecordCommand)) {
            return false;
        }

        DeleteRecordCommand otherDeleteRecordCommand = (DeleteRecordCommand) other;
        return this.targetPatientIndex.equals(otherDeleteRecordCommand.targetPatientIndex)
                && this.targetRecordIndex.equals(otherDeleteRecordCommand.targetRecordIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPatientIndex", targetPatientIndex)
                .add("targetRecordIndex", targetRecordIndex)
                .toString();
    }
}
