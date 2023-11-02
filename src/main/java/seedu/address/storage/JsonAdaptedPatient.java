package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.*;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

/**
 * Jackson-friendly version of {@link Patient}.
 */
class JsonAdaptedPatient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    private final String name;
    private final String nric;
    private final String phone;
    private final String email;
    private final String gender;
    private final Integer age;
    private final String bloodType;
    private final List<JsonAdaptedAllergy> allergies = new ArrayList<>();
    private final List<JsonAdaptedRecord> records = new ArrayList<>();
    private final Boolean isPinned;
    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPatient} with the given patient details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("name") String name, @JsonProperty("nric") String nric,
                              @JsonProperty("email") String email, @JsonProperty("phone") String phone,
                              @JsonProperty("gender") String gender,
                              @JsonProperty("age") Integer age, @JsonProperty("bloodType") String bloodType,
                              @JsonProperty("allergies") List<JsonAdaptedAllergy> allergies,
                              @JsonProperty("records") List<JsonAdaptedRecord> records,
                              @JsonProperty("appointments") List<JsonAdaptedAppointment> appointments,
                              @JsonProperty("isPinned") Boolean isPinned) {

        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.bloodType = bloodType;
        if (allergies != null) {
            this.allergies.addAll(allergies);
        }
        if (records != null) {
            this.records.addAll(records);
        }
        this.isPinned = isPinned;
        if (appointments != null) {
            this.appointments.addAll(appointments);
        }
    }

    /**
     * Converts a given {@code Patient} into this class for Jackson use.
     */
    public JsonAdaptedPatient(Patient source) {
        name = source.getName().fullName;
        nric = source.getNric().nric;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        gender = source.getGender().gender;
        age = source.getAge().age;
        bloodType = source.getBloodType().bloodType;
        allergies.addAll(source.getAllergies().stream()
                .map(JsonAdaptedAllergy::new)
                .collect(Collectors.toList()));
        records.addAll(source.getRecords().asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedRecord::new)
                .collect(Collectors.toList()));
        appointments.addAll(source.getAppointments().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedAppointment::new).collect(Collectors.toList()));
        isPinned = source.isPinned();
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's
     * {@code Patient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted patient.
     */
    public Patient toModelType() throws IllegalValueException {
        final List<Allergy> allergiesList = new ArrayList<>();
        for (JsonAdaptedAllergy allergy : allergies) {
            allergiesList.add(allergy.toModelType());
        }

        final UniqueRecordList modelRecords = new UniqueRecordList();
        for (JsonAdaptedRecord record : records) {
            modelRecords.add(record.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (nric == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, seedu.address.model.shared.Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(seedu.address.model.shared.Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }
        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        final Age modelAge = new Age(age);

        if (bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BloodType.class.getSimpleName()));
        }
        if (!BloodType.isValidBloodType(bloodType)) {
            throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
        }
        final BloodType modelBloodType = new BloodType(bloodType);

        final Set<Allergy> modelAllergies = new HashSet<>(allergiesList);

        final UniqueAppointmentList modelAppointments = new UniqueAppointmentList();
        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointments) {
            Appointment appointment = jsonAdaptedAppointment.toModelType();
            modelAppointments.add(appointment);
        }

        return new Patient(modelName, modelNric, modelEmail, modelPhone, modelGender,
                modelAge, modelBloodType, modelAllergies, modelRecords, modelAppointments, isPinned);
    }
}
