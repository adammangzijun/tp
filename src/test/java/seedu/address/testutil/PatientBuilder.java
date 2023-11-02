package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.*;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_NRIC = "Z1234567Z";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_GENDER = "F";
    public static final int DEFAULT_AGE = 21;
    public static final String DEFAULT_BLOODTYPE = "O+";
    public static final String DEFAULT_ALLERGY = "Ants";

    private Name name;
    private Nric nric;
    private Email email;
    private Phone phone;
    private Gender gender;
    private Age age;
    private BloodType bloodType;
    private Set<Allergy> allergies;
    private UniqueRecordList records;
    private UniqueAppointmentList appointments;
    private boolean isPinned;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        email = new Email(DEFAULT_EMAIL);
        phone = new Phone(DEFAULT_PHONE);
        gender = new Gender(DEFAULT_GENDER);
        age = new Age(DEFAULT_AGE);
        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        allergies = new HashSet<>();
        records = new UniqueRecordList();
        appointments = new UniqueAppointmentList();
        isPinned = false;
    }

    /**
     * Initializes the PatientBuilder with the data of {@code personToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        nric = patientToCopy.getNric();
        email = patientToCopy.getEmail();
        phone = patientToCopy.getPhone();
        gender = patientToCopy.getGender();
        age = patientToCopy.getAge();
        bloodType = patientToCopy.getBloodType();
        allergies = new HashSet<>(patientToCopy.getAllergies());
        records = new UniqueRecordList();
        records.setRecords(patientToCopy.getRecords());
        appointments = new UniqueAppointmentList();
        appointments.setAppointments(patientToCopy.getAppointments());
        ;
        isPinned = patientToCopy.isPinned();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Patient} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAge(int age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code Patient} that we are building.
     */
    public PatientBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Parses the {@code allergies} into a {@code Set<Allergy>} and set it to the
     * {@code Patient} that we are building.
     */
    public PatientBuilder withAllergies(String... allergies) {
        this.allergies = SampleDataUtil.getAllergySet(allergies);
        return this;
    }

    /**
     * Sets the {@code records} of the {@code Patient} that we are building.
     */
    public PatientBuilder withRecords(Record... records) {
        this.records = SampleDataUtil.getRecordList(records);
        return this;
    }

    /**
     * Sets the {@code records} of the {@code Patient} that we are building.
     */
    public PatientBuilder withRecords(UniqueRecordList records) {
        this.records = records;
        return this;
    }

    /**
     * Sets the {@code isPinned} of the {@code Patient} that we are building.
     */
    public PatientBuilder withIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
        return this;
    }

    /**
     * Sets the {@code appointments} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAppointments(UniqueAppointmentList appointment) {
        this.appointments = appointment;
        return this;
    }

    /**
     * Sets the {@code appointments} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAppointments(Appointment... appointments) {
        this.appointments = SampleDataUtil.getAppointmentList(appointments);
        return this;
    }

    public Patient build() {
        return new Patient(name, nric, email, phone, gender, age, bloodType, allergies, records, appointments, isPinned);
    }
}
