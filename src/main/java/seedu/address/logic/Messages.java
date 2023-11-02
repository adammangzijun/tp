package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX =
            "The appointment index provided is invalid";
    public static final String MESSAGE_INVALID_RECORD_DISPLAYED_INDEX = "The record index provided is invalid";
    public static final String MESSAGE_PATIENTS_LISTED_OVERVIEW = "%1$d patients listed!";
    public static final String MESSAGE_RECORDS_LISTED_OVERVIEW = "%1$d records listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code patient} for display to the user.
     */
    public static String format(Patient patient) {
        final StringBuilder builder = new StringBuilder();
        builder.append(patient.getName())
                .append("; NRIC: ")
                .append(patient.getNric())
                .append("; Email: ")
                .append(patient.getEmail())
                .append("; Phone: ")
                .append(patient.getPhone())
                .append("; Gender: ")
                .append(patient.getGender())
                .append("; Age: ")
                .append(patient.getAge())
                .append("; BloodType: ")
                .append(patient.getBloodType())
                .append("; Allergies: ");
        patient.getAllergies().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code appointment} for display to the user.
     */
    public static String format(Appointment appointment, Patient patient) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Patient: ")
                .append(patient.getName())
                .append("; Appointment: ")
                .append(appointment.getName())
                .append("; Date & Time: ")
                .append(appointment.getDateTime());

        return builder.toString();
    }

    /**
     * Formats the {@code record} for display to the user.
     */
    public static String format(Record record, Patient patient) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Patient: ")
                .append(patient.getName())
                .append("; Conditions: ")
                .append(record.getConditions())
                .append("; Date & Time: ")
                .append(record.getDateTime())
                .append("; Medications: ")
                .append(record.getMedications());
        return builder.toString();
    }
}
