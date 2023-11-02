package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Phone;
import seedu.address.model.record.Condition;
import seedu.address.model.record.Medication;
import seedu.address.model.shared.Name;


public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_GENDER = " ";
    private static final String INVALID_AGE = " ";
    private static final String INVALID_BLOODTYPE = "D";
    private static final String INVALID_ALLERGY = "#dust";
    private static final String INVALID_CONDITION = "Fever*";
    private static final String INVALID_MEDICATION = "Tylenol+";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_GENDER = "M";
    private static final int VALID_AGE = 10;
    private static final String VALID_AGE_STRING = "10";
    private static final String VALID_BLOODTYPE = "A+";
    private static final String VALID_ALLERGY_1 = "Nuts";
    private static final String VALID_ALLERGY_2 = "Coconut";
    private static final String VALID_CONDITION = "Fever";
    private static final String VALID_MEDICATION = "Tylenol";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PATIENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PATIENT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseGender_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGender((String) null));
    }

    @Test
    public void parseGender_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGender(INVALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithoutWhitespace_returnsGender() throws Exception {
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(VALID_GENDER));
    }

    @Test
    public void parseGender_validValueWithWhitespace_returnsTrimmedGender() throws Exception {
        String genderWithWhitespace = WHITESPACE + VALID_GENDER + WHITESPACE;
        Gender expectedGender = new Gender(VALID_GENDER);
        assertEquals(expectedGender, ParserUtil.parseGender(genderWithWhitespace));
    }

    @Test
    public void parseAge_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAge((String) null));
    }

    @Test
    public void parseAge_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAge(INVALID_AGE));
    }

    @Test
    public void parseAge_validValueWithoutWhitespace_returnsAge() throws Exception {
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(VALID_AGE_STRING));
    }

    @Test
    public void parseAge_validValueWithWhitespace_returnsTrimmedAge() throws Exception {
        String ageWithWhitespace = WHITESPACE + VALID_AGE + WHITESPACE;
        Age expectedAge = new Age(VALID_AGE);
        assertEquals(expectedAge, ParserUtil.parseAge(ageWithWhitespace));
    }

    @Test
    public void parseBloodType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseBloodType((String) null));
    }

    @Test
    public void parseBloodType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseBloodType(INVALID_BLOODTYPE));
    }

    @Test
    public void parseBloodType_validValueWithoutWhitespace_returnsBloodType() throws Exception {
        BloodType expectedBloodType = new BloodType(VALID_BLOODTYPE);
        assertEquals(expectedBloodType, ParserUtil.parseBloodType(VALID_BLOODTYPE));
    }

    @Test
    public void parseBloodType_validValueWithWhitespace_returnsTrimmedBloodType() throws Exception {
        String bloodTypeWithWhitespace = WHITESPACE + VALID_BLOODTYPE + WHITESPACE;
        BloodType expectedBloodType = new BloodType(VALID_BLOODTYPE);
        assertEquals(expectedBloodType, ParserUtil.parseBloodType(bloodTypeWithWhitespace));
    }

    @Test
    public void parseAllergy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAllergy(null));
    }

    @Test
    public void parseAllergy_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAllergy(INVALID_ALLERGY));
    }

    @Test
    public void parseAllergy_validValueWithoutWhitespace_returnsAllergy() throws Exception {
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY_1);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(VALID_ALLERGY_1));
    }

    @Test
    public void parseAllergy_validValueWithWhitespace_returnsTrimmedAllergy() throws Exception {
        String allergyWithWhitespace = WHITESPACE + VALID_ALLERGY_1 + WHITESPACE;
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY_1);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(allergyWithWhitespace));
    }

    @Test
    public void parseAllergies_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAllergies(null));
    }

    @Test
    public void parseAllergies_collectionWithInvalidAllergies_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAllergies(Arrays.asList(VALID_ALLERGY_1,
                INVALID_ALLERGY)));
    }

    @Test
    public void parseAllergies_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseAllergies(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseAllergies_collectionWithValidAllergies_returnsAllergySet() throws Exception {
        Set<Allergy> actualAllergySet = ParserUtil.parseAllergies(Arrays.asList(VALID_ALLERGY_1, VALID_ALLERGY_2));
        Set<Allergy> expectedAllergySet = new HashSet<Allergy>(Arrays.asList(new Allergy(VALID_ALLERGY_1),
                new Allergy(VALID_ALLERGY_2)));

        assertEquals(expectedAllergySet, actualAllergySet);
    }
    @Test
    public void parseCondition_validCondition_returnsCondition() throws Exception {
        Condition expectedCondition = new Condition(VALID_CONDITION);
        assertEquals(expectedCondition, ParserUtil.parseCondition(VALID_CONDITION));
    }
    @Test
    public void parseCondition_validValueWithWhitespace_returnsTrimmedCondition() throws Exception {
        String conditionWithWhitespace = WHITESPACE + VALID_CONDITION + WHITESPACE;
        Condition expectedCondition = new Condition(VALID_CONDITION);
        assertEquals(expectedCondition, ParserUtil.parseCondition(conditionWithWhitespace));
    }
    @Test
    public void parseCondition_withInvalidCondition_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCondition(INVALID_CONDITION));
    }

    @Test
    public void parseMedication_validMedication_returnsMedication() throws Exception {
        Medication expectedMedication = new Medication(VALID_MEDICATION);
        assertEquals(expectedMedication, ParserUtil.parseMedication(VALID_MEDICATION));
    }
    @Test
    public void parseCondition_withNullCondition_throwsParseException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCondition(null));
    }
    @Test
    public void parseConditions_listWithValidConditions_returnsConditionsList() throws Exception {
        List<Condition> conditionList = ParserUtil.parseConditions(Arrays.asList(VALID_CONDITION));
        List<Condition> expectedConditionList = new ArrayList<>(Arrays.asList(new Condition(VALID_CONDITION)));

        assertEquals(expectedConditionList, conditionList);
    }
    @Test
    public void parseConditions_listWithInvalidCondition_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseConditions(Arrays.asList(VALID_CONDITION,
                INVALID_CONDITION)));
    }
    @Test
    public void parseMedication_validValueWithWhitespace_returnsTrimmedMedication() throws Exception {
        String medicationWithWhitespace = WHITESPACE + VALID_MEDICATION + WHITESPACE;
        Medication expectedMedication = new Medication(VALID_MEDICATION);
        assertEquals(expectedMedication, ParserUtil.parseMedication(medicationWithWhitespace));
    }

    @Test
    public void parseMedication_withInvalidMedication_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedication(INVALID_MEDICATION));
    }

    @Test
    public void parseMedication_withNullMedication_throwsParseException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedication(null));
    }

    @Test
    public void parseMedications_listWithValidMedications_returnsMedicationsList() throws Exception {
        List<Medication> medicationList = ParserUtil.parseMedications(Arrays.asList(VALID_MEDICATION));
        List<Medication> expectedMedicationList = new ArrayList<>(Arrays.asList(new Medication(VALID_MEDICATION)));

        assertEquals(expectedMedicationList, medicationList);
    }

    @Test
    public void parseMedications_listWithInvalidMedications_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedications(Arrays.asList(VALID_MEDICATION,
                INVALID_MEDICATION)));
    }
}
