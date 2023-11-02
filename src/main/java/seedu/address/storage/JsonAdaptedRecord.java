package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.record.Condition;
import seedu.address.model.record.Medication;
import seedu.address.model.record.Record;
import seedu.address.model.shared.DateTime;

/**
 * Jackson-friendly version of {@link Record}.
 */
public class JsonAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";
    private final String dateTime;
    private final List<JsonAdaptedCondition> conditions = new ArrayList<>();
    private final String filePath;
    private final Integer patientIndex;
    private final List<JsonAdaptedMedication> medications = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdoptedRecord} with the given record details.
     */
    @JsonCreator
    public JsonAdaptedRecord(@JsonProperty("dateTime") String dateTime,
            @JsonProperty("condition") List<JsonAdaptedCondition> conditions,
            @JsonProperty("medication") List<JsonAdaptedMedication> medications,
            @JsonProperty("filePath") String filePath,
            @JsonProperty("patientIndex") Integer patientIndex) {
        this.dateTime = dateTime;
        if (conditions != null) {
            this.conditions.addAll(conditions);
        }
        this.filePath = filePath;
        this.patientIndex = patientIndex;
        if (medications != null) {
            this.medications.addAll(medications);
        }
    }

    /**
     * Converts a given {@code Record} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record source) {
        this.dateTime = source.getDateTime().toString();
        this.patientIndex = source.getPatientIndex();
        this.filePath = source.getFilePath() == null ? null : source.getFilePath().toString();
        this.conditions.addAll(source.getConditions().stream()
                .map(JsonAdaptedCondition::new)
                .collect(Collectors.toList()));
        this.medications.addAll(source.getMedications().stream()
                .map(JsonAdaptedMedication::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's
     * {@code Patient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted patient.
     */
    public Record toModelType() throws IllegalValueException {
        final List<Condition> conditionsList = new ArrayList<>();
        final List<Medication> medicationsList = new ArrayList<>();

        for (JsonAdaptedCondition condition : conditions) {
            if (condition == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Condition.class.getSimpleName()));
            }
            conditionsList.add(condition.toModelType());
        }

        for (JsonAdaptedMedication medication : medications) {
            if (medication == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Medication.class.getSimpleName()));
            }
            medicationsList.add(medication.toModelType());
        }

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }

        final DateTime modelDateTime = new DateTime(dateTime);
        final List<Medication> modelMedications = new ArrayList<>(medicationsList);
        final List<Condition> modelConditions = new ArrayList<>(conditionsList);
        if (filePath == null) {
            return new Record(modelDateTime, modelConditions, modelMedications, null, patientIndex);
        } else {
            Path modelFilePath = Paths.get(filePath);
            return new Record(modelDateTime, modelConditions, modelMedications, modelFilePath, patientIndex);
        }
    }
}
