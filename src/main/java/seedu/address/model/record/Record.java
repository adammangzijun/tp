package seedu.address.model.record;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.EditRecordCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.shared.DateTime;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

/**
 * Record of condition of a patient and date and time in which a patient visits
 * the doctor
 */
public class Record {

    private final List<Condition> conditions = new ArrayList<>();
    private final List<Medication> medications = new ArrayList<>();
    private final DateTime dateTime;
    private Path filePath;
    private final Integer patientIndex;

    /**
     * Constructs a record object
     */

    public Record(DateTime dateTime, List<Condition> conditions, List<Medication> medications, Path filePath,
            Integer patientIndex) {
        requireAllNonNull(dateTime, conditions);
        this.dateTime = dateTime;
        this.conditions.addAll(conditions);
        this.medications.addAll(medications);
        this.filePath = filePath;
        this.patientIndex = patientIndex;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public int getPatientIndex() {
        return patientIndex;
    }

    public List<Condition> getConditions() {
        return Collections.unmodifiableList(conditions);
    }

    public Path getFilePath() {
        return filePath; // Getter for the file path
    }

    // Set the filePath. This is the new setter method
    public void setFilePath(Path filePath, int displayedIndex) {
        requireAllNonNull(filePath); // Ensure the provided filePath is not null
        this.filePath = filePath;
        Model model = ModelManager.getInstance();
        EditRecordCommand.EditRecordDescriptor editRecordDescriptor = new EditRecordCommand.EditRecordDescriptor();
        editRecordDescriptor.setFilePath(filePath);
        try {
            Storage storage = StorageManager.getInstance();
            storage.saveAddressBook(model.getAddressBook());
        } catch (Exception c) {
            c.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(dateTime, conditions, filePath, patientIndex);
    }

    public List<Medication> getMedications() {
        return Collections.unmodifiableList(medications);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;
        return dateTime.equals(otherRecord.dateTime)
                && conditions.equals(otherRecord.conditions)
                && Objects.equals(filePath, otherRecord.filePath)
                && patientIndex.equals(otherRecord.patientIndex)
                && medications.equals(otherRecord.medications);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("dateTime", dateTime)
                .add("conditions", conditions)
                .add("medications", medications)
                .add("filePath", filePath)
                .add("patientIndex", patientIndex)
                .toString();
    }

}
