package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.*;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

/**
 * Edits the details of an existing patient in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "editpatient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the patient identified "
            + "by the index number used in the displayed patient list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_BLOODTYPE + "BLOODTYPE "
            + "[" + PREFIX_ALLERGIES + "ALLERGY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PATIENT_SUCCESS = "Edited Patient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the address book.";

    private final Index index;
    private final EditPatientDescriptor editPatientDescriptor;

    /**
     * @param index
     *            of the patient in the filtered patient list to edit
     * @param editPatientDescriptor
     *            details to edit the patient with
     */
    public EditCommand(Index index, EditPatientDescriptor editPatientDescriptor) {
        requireNonNull(index);
        requireNonNull(editPatientDescriptor);

        this.index = index;
        this.editPatientDescriptor = new EditPatientDescriptor(editPatientDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());
        Patient editedPatient = createEditedPatient(patientToEdit, editPatientDescriptor);

        if (!patientToEdit.isSamePatient(editedPatient) && model.hasPatient(editedPatient)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PATIENT_SUCCESS,
                Messages.format(editedPatient)));
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code editPatientDescriptor}.
     */
    private static Patient createEditedPatient(Patient patientToEdit, EditPatientDescriptor editPatientDescriptor) {
        assert patientToEdit != null;

        Name updatedName = editPatientDescriptor.getName().orElse(patientToEdit.getName());
        Nric updatedNric = editPatientDescriptor.getNric().orElse(patientToEdit.getNric());
        Email updatedEmail = editPatientDescriptor.getEmail().orElse(patientToEdit.getEmail());
        Phone updatedPhone = editPatientDescriptor.getPhone().orElse(patientToEdit.getPhone());
        Gender updatedGender = editPatientDescriptor.getGender().orElse(patientToEdit.getGender());
        Age updatedAge = editPatientDescriptor.getAge().orElse(patientToEdit.getAge());
        BloodType updatedBloodType = editPatientDescriptor.getBloodType().orElse(patientToEdit.getBloodType());
        Set<Allergy> updatedAllergies = editPatientDescriptor.getAllergies().orElse(patientToEdit.getAllergies());
        UniqueRecordList updatedRecords = patientToEdit.getRecords();
        UniqueAppointmentList updatedAppointments = patientToEdit.getAppointments();
        Boolean updatedisPinned = patientToEdit.isPinned();

        return new Patient(updatedName, updatedNric, updatedEmail, updatedPhone, updatedGender,
                updatedAge, updatedBloodType, updatedAllergies, updatedRecords, updatedAppointments, updatedisPinned);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPatientDescriptor.equals(otherEditCommand.editPatientDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPatientDescriptor", editPatientDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the patient with. Each non-empty field value will
     * replace the
     * corresponding field value of the patient.
     */
    public static class EditPatientDescriptor {
        private Name name;
        private Nric nric;
        private Email email;
        private Phone phone;
        private Gender gender;
        private Age age;
        private BloodType bloodType;
        private Set<Allergy> allergies;

        public EditPatientDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code allergies} is used internally.
         */
        public EditPatientDescriptor(EditPatientDescriptor toCopy) {
            setName(toCopy.name);
            setNric(toCopy.nric);
            setEmail(toCopy.email);
            setPhone(toCopy.phone);
            setGender(toCopy.gender);
            setAge(toCopy.age);
            setBloodType(toCopy.bloodType);
            setAllergies(toCopy.allergies);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, email, phone, gender, age, bloodType, allergies);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public void setBloodType(BloodType bloodType) {
            this.bloodType = bloodType;
        }

        /**
         * Returns an unmodifiable Allergy set, which throws
         * {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code allergies} is null.
         */
        public Optional<Set<Allergy>> getAllergies() {
            return (allergies != null) ? Optional.of(Collections.unmodifiableSet(allergies)) : Optional.empty();
        }

        /**
         * Sets {@code allergies} to this object's {@code tags}.
         * A defensive copy of {@code allergies} is used internally.
         */
        public void setAllergies(Set<Allergy> allergies) {
            this.allergies = (allergies != null) ? new HashSet<>(allergies) : null;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPatientDescriptor)) {
                return false;
            }

            EditPatientDescriptor otherEditPatientDescriptor = (EditPatientDescriptor) other;
            return Objects.equals(name, otherEditPatientDescriptor.name)
                    && Objects.equals(nric, otherEditPatientDescriptor.nric)
                    && Objects.equals(email, otherEditPatientDescriptor.email)
                    && Objects.equals(phone, otherEditPatientDescriptor.phone)
                    && Objects.equals(gender, otherEditPatientDescriptor.gender)
                    && Objects.equals(age, otherEditPatientDescriptor.age)
                    && Objects.equals(bloodType, otherEditPatientDescriptor.bloodType)
                    && Objects.equals(allergies, otherEditPatientDescriptor.allergies);
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
                    .toString();
        }
    }
}
