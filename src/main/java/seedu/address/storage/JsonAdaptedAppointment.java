package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.DateTime;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

/**
 * Jackson-friendly version of {@link Patient}.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String name;
    private final String dateTime;
    private final String nric;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given patient details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("name") String name,
            @JsonProperty("dateTime") String dateTime, @JsonProperty("nric") String nric) {
        this.name = name;
        this.dateTime = dateTime;
        this.nric = nric;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        name = source.getName().fullName;
        dateTime = source.getDateTime().toString();
        nric = source.getNric().toString();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's
     * {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelname = new Name(name);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        return new Appointment(modelname, modelDateTime, modelNric);
    }
}
