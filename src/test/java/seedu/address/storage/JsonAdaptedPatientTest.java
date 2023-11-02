package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Phone;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "S*234567A";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_GENDER = "G";
    private static final Integer INVALID_AGE = 0;
    private static final String INVALID_BLOODTYPE = "C";
    private static final String INVALID_ALLERGY = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final Integer VALID_AGE = BENSON.getAge().age;
    private static final String VALID_BLOODTYPE = BENSON.getBloodType().toString();

    private static final List<JsonAdaptedAllergy> VALID_ALLERGIES = BENSON.getAllergies().stream()
            .map(JsonAdaptedAllergy::new)
            .collect(Collectors.toList());
    private static final Boolean VALID_ISPINNED = BENSON.isPinned();
    private static final List<JsonAdaptedRecord> VALID_RECORDS = BENSON.getRecords().asUnmodifiableObservableList()
            .stream()
            .map(JsonAdaptedRecord::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAppointment> VALID_APPOINTMENTS = BENSON.getAppointments()
            .asUnmodifiableObservableList().stream().map(JsonAdaptedAppointment::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(BENSON);
        assertEquals(BENSON, patient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(INVALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(null, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, INVALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, null, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, INVALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, null,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, INVALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, null, VALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                INVALID_GENDER, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                null, VALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS, VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, INVALID_AGE, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, null, VALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS, VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidBloodType_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, INVALID_BLOODTYPE, VALID_ALLERGIES, VALID_RECORDS,
                VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = BloodType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullBloodType_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, null, VALID_ALLERGIES, VALID_RECORDS, VALID_APPOINTMENTS,
                VALID_ISPINNED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BloodType.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidAllergies_throwsIllegalValueException() {
        List<JsonAdaptedAllergy> invalidAllergies = new ArrayList<>(VALID_ALLERGIES);
        invalidAllergies.add(new JsonAdaptedAllergy(INVALID_ALLERGY));
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_EMAIL, VALID_PHONE,
                VALID_GENDER, VALID_AGE, VALID_BLOODTYPE, invalidAllergies, VALID_RECORDS,
                VALID_APPOINTMENTS, VALID_ISPINNED);
        assertThrows(IllegalValueException.class, patient::toModelType);
    }

}
