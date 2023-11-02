package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.*;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Condition;
import seedu.address.model.record.Medication;
import seedu.address.model.record.Record;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.shared.DateTime;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Nric("S1234569A"),
                    new Email("alexyeoh@example.com"), new Phone("87438807"),
                    new Gender("M"), new Age(12), new BloodType("A+"),
                    getAllergySet("Peanuts"),
                    getRecordList(new Record(new DateTime("01-01-2001 1200"),
                            getConditionList("Fever"),
                            getMedicationList("Tylenol"), null, 0)),
                    getAppointmentList(new Appointment(new Name("Eye Exam"),
                            new DateTime("01-01-2001 1200"),
                            new Nric("S1234569A"))),
                    true),
            new Patient(new Name("Bernice Yu"), new Nric("T1234567A"),
                    new Email("berniceyu@example.com"), new Phone("99272758"),
                    new Gender("F"), new Age(31), new BloodType("B+"),
                    getAllergySet("Dust", "Peanuts"),
                    new UniqueRecordList(), new UniqueAppointmentList(), false),
            new Patient(new Name("Charlotte Oliveiro"), new Nric("S7654321A"),
                    new Email("charlotte@example.com"), new Phone("93210283"),
                    new Gender("F"), new Age(12), new BloodType("AB+"),
                    getAllergySet("Dust"),
                    new UniqueRecordList(), new UniqueAppointmentList(), false),
            new Patient(new Name("David Li"), new Nric("T1234567C"),
                    new Email("lidavid@example.com"), new Phone("91031282"),
                    new Gender("M"), new Age(33), new BloodType("O-"),
                    getAllergySet("Pollen"), new UniqueRecordList(),
                    new UniqueAppointmentList(), false),
            new Patient(new Name("Irfan Ibrahim"), new Nric("S5671234A"),
                    new Email("irfan@example.com"), new Phone("92492021"),
                    new Gender("M"), new Age(21), new BloodType("B-"),
                    getAllergySet("Fur"), new UniqueRecordList(),
                    new UniqueAppointmentList(), false),
            new Patient(new Name("Roy Balakrishnan"), new Nric("T1234567Z"),
                    new Email("royb@example.com"), new Phone("92624417"),
                    new Gender("M"), new Age(24), new BloodType("B+"),
                    getAllergySet("Grass"), new UniqueRecordList(),
                    new UniqueAppointmentList(), false)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePatient : getSamplePatients()) {
            sampleAb.addPatient(samplePatient);
        }

        return sampleAb;
    }

    /**
     * Returns an allergy set containing the list of strings given.
     */
    public static Set<Allergy> getAllergySet(String... strings) {
        return Arrays.stream(strings)
                .map(Allergy::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list of conditions containing the list of strings given.
     */
    public static List<Condition> getConditionList(String... strings) {
        return Arrays.stream(strings)
                .map(Condition::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of medications containing the list of strings given.
     */
    public static List<Medication> getMedicationList(String... strings) {
        return Arrays.stream(strings)
                .map(Medication::new)
                .collect(Collectors.toList());
    }

    public static UniqueRecordList getRecordList(Record... records) {
        UniqueRecordList recordList = new UniqueRecordList();
        recordList.setRecords(Arrays.asList(records));
        return recordList;
    }

    public static UniqueAppointmentList getAppointmentList(Appointment... appointments) {
        UniqueAppointmentList appointmentList = new UniqueAppointmentList();
        appointmentList.setAppointments(Arrays.asList(appointments));
        return appointmentList;
    }
}
