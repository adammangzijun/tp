package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Patient {

    // Identity fields
    private final Name name;
    private final Nric nric;
    private final Phone phone;
    private final Email email;
    private final Gender gender;
    private final Age age;
    private final BloodType bloodType;
    private final Set<Allergy> allergies = new HashSet<>();
    private final UniqueRecordList records = new UniqueRecordList();
    private final UniqueAppointmentList appointments = new UniqueAppointmentList();
    private boolean isPinned;

    /**
     * Constructs a Patient with the specified attributes.
     *
     * @param name         The name of the patient.
     * @param nric         The NRIC of the patient.
     * @param email        The email address of the patient.
     * @param phone        The phone number of the patient.
     * @param gender       The gender of the patient.
     * @param age          The age of the patient.
     * @param bloodType    The blood type of the patient.
     * @param allergies    A set of allergies associated with the patient.
     * @param records      The medical records of the patient.
     * @param appointments The appointments of the patient.
     * @param isPinned     A boolean indicating whether the patient is pinned.
     */
    public Patient(Name name, Nric nric, Email email, Phone phone, Gender gender, Age age,
                   BloodType bloodType, Set<Allergy> allergies, UniqueRecordList records, UniqueAppointmentList appointments,
                   boolean isPinned) {
        requireAllNonNull(name, phone, email, gender, age, allergies, appointments, isPinned);
        this.name = name;
        this.nric = nric;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.bloodType = bloodType;
        this.allergies.addAll(allergies);
        this.records.setRecords(records);
        this.appointments.setAppointments(appointments);
        this.isPinned = isPinned;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public Age getAge() {
        return age;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies);
    }

    public UniqueRecordList getRecords() {
        return this.records;
    }

    public UniqueAppointmentList getAppointments() {
        return this.appointments;
    }

    /**
     * Returns true if the same appointment as {@code appointment} exists in the
     * patient.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Returns true if both patients have the same nric.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getNric().equals(getNric());
    }

    /**
     * Adds a record to the patient
     */
    public void addRecord(Record record) {
        requireNonNull(record);
        this.records.add(record);
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return name.equals(otherPatient.name)
                && nric.equals(otherPatient.nric)
                && phone.equals(otherPatient.phone)
                && email.equals(otherPatient.email)
                && gender.equals(otherPatient.gender)
                && age.equals(otherPatient.age)
                && bloodType.equals(otherPatient.bloodType)
                && allergies.equals(otherPatient.allergies)
                && records.equals(otherPatient.records)
                && appointments.equals(otherPatient.appointments)
                && isPinned == otherPatient.isPinned;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, email, phone, gender, age, bloodType, allergies);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("email", email)
                .add("phone", phone)
                .add("gender", gender)
                .add("age", age)
                .add("bloodType", bloodType)
                .add("allergies", allergies)
                .add("records", records)
                .add("appointments", appointments)
                .add("isPinned", isPinned)
                .toString();
    }
}
