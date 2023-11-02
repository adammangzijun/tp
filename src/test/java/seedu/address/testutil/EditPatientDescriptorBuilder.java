package seedu.address.testutil;

import java.util.Set;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.model.patient.*;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditPatientDescriptor objects.
 */
public class EditPatientDescriptorBuilder {

    private EditPatientDescriptor descriptor;

    public EditPatientDescriptorBuilder() {
        descriptor = new EditCommand.EditPatientDescriptor();
    }

    public EditPatientDescriptorBuilder(EditPatientDescriptor descriptor) {
        this.descriptor = new EditPatientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPatientDescriptor} with fields containing
     * {@code patient}'s details
     */
    public EditPatientDescriptorBuilder(Patient patient) {
        descriptor = new EditCommand.EditPatientDescriptor();
        descriptor.setName(patient.getName());
        descriptor.setNric(patient.getNric());
        descriptor.setEmail(patient.getEmail());
        descriptor.setPhone(patient.getPhone());
        descriptor.setGender(patient.getGender());
        descriptor.setAge(patient.getAge());
        descriptor.setBloodType(patient.getBloodType());
        descriptor.setAllergies(patient.getAllergies());
    }

    /**
     * Sets the {@code Name} of the {@code EditPatientDescriptor} that we are
     * building.
     */
    public EditPatientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code EditPatientDescriptor} that we are
     * building.
     */
    public EditPatientDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new Nric(nric));
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code EditPatientDescriptor} that we are
     * building to null.
     */
    public EditPatientDescriptorBuilder withNullNric() {
        descriptor.setNric(null);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPatientDescriptor} that we are
     * building.
     */
    public EditPatientDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPatientDescriptor} that we are
     * building.
     */
    public EditPatientDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditPatientDescriptor} that we are
     * building.
     */
    public EditPatientDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code EditPatientDescriptor} that we are
     * building.
     */
    public EditPatientDescriptorBuilder withAge(int age) {
        descriptor.setAge(new Age(age));
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code EditPatientDescriptor} that we are
     * building.
     */
    public EditPatientDescriptorBuilder withBloodType(String bloodType) {
        descriptor.setBloodType(new BloodType(bloodType));
        return this;
    }

    /**
     * Parses the {@code allergies} into a {@code Set<Allergy>} and set it to the
     * {@code EditPatientDescriptor}
     * that we are building.
     */
    public EditPatientDescriptorBuilder withAllergies(String... allergies) {
        Set<Allergy> allergySet = SampleDataUtil.getAllergySet(allergies);
        descriptor.setAllergies(allergySet);
        return this;
    }

    public EditPatientDescriptor build() {
        return descriptor;
    }
}
