package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Phone;
import seedu.address.model.record.Condition;
import seedu.address.model.record.Medication;
import seedu.address.model.shared.DateTime;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INPUT = "Index input is not in the format of number/number";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PATIENT_INDEX = "Patient index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_RECORD_INDEX = "Record index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndexes} for edit record command into a patient's
     * {@code Index}
     * and returns it. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified patient index is invalid (not
     *                        non-zero unsigned integer).
     */
    public static Index parsePatientIndex(String oneBasedIndexes) throws ParseException {
        // Check if input matches the format of two numbers separated by a slash
        if (!oneBasedIndexes.matches("\\d+/\\d+")) {
            throw new ParseException(MESSAGE_INVALID_INPUT);
        }
        String trimmedIndex = oneBasedIndexes.split("/")[0].trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_PATIENT_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndexes} for edit record command into a record's
     * {@code Index}
     * and returns it. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified patient index is invalid (not
     *                        non-zero unsigned integer).
     */
    public static Index parseRecordIndex(String oneBasedIndexes) throws ParseException {
        String trimmedIndex = oneBasedIndexes.split("/")[1].trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_RECORD_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric.toUpperCase());
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String gender} into an {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String age} into an {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        int trimmedAge = 0;
        try {
            trimmedAge = Integer.parseInt(age.trim());

        } catch (NumberFormatException e) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }

        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code String bloodType} into an {@code BloodType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     */
    public static BloodType parseBloodType(String bloodType) throws ParseException {
        requireNonNull(bloodType);
        String trimmedBloodType = bloodType.trim();
        if (!BloodType.isValidBloodType(trimmedBloodType)) {
            throw new ParseException(BloodType.MESSAGE_CONSTRAINTS);
        }
        return new BloodType(trimmedBloodType);
    }

    /**
     * Parses a {@code String allergy} into an {@code Allergy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     */
    public static Allergy parseAllergy(String allergy) throws ParseException {
        requireNonNull(allergy);
        String trimmedAllergy = allergy.trim();
        if (!Allergy.isValidAllergy(trimmedAllergy)) {
            throw new ParseException(Allergy.MESSAGE_CONSTRAINTS);
        }
        return new Allergy(trimmedAllergy);
    }

    /**
     * Parses a {@code Collection<String> allergies} into an {@code Set<Allergy>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     */
    public static Set<Allergy> parseAllergies(Collection<String> allergies) throws ParseException {
        requireNonNull(allergies);
        final Set<Allergy> allergiesSet = new HashSet<>();
        for (String allergy : allergies) {
            allergiesSet.add(parseAllergy(allergy));
        }
        return allergiesSet;
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String dateTime} into an {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        return new DateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String condition} into an {@code Condition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code condition} is invalid.
     */
    public static Condition parseCondition(String condition) throws ParseException {
        requireNonNull(condition);
        String trimmedCondition = condition.trim();
        if (!Condition.isValidCondition(trimmedCondition)) {
            throw new ParseException(Condition.MESSAGE_CONSTRAINTS);
        }
        return new Condition(trimmedCondition);
    }

    /**
     * Parses a {@code Collection<String> condtions} into an
     * {@code List<Condition>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code condition} is invalid.
     */
    public static List<Condition> parseConditions(Collection<String> conditions) throws ParseException {
        requireNonNull(conditions);
        final List<Condition> conditionsList = new ArrayList<>();
        for (String condition : conditions) {
            conditionsList.add(parseCondition(condition));
        }
        return conditionsList;
    }

    /**
     * Parses a {@code String medication} into an {@code Medication}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medication} is invalid.
     */
    public static Medication parseMedication(String medication) throws ParseException {
        requireNonNull(medication);
        String trimmedMedication = medication.trim();
        if (!Medication.isValidMedication(trimmedMedication)) {
            throw new ParseException(Medication.MESSAGE_CONSTRAINTS);
        }
        return new Medication(trimmedMedication);
    }

    /**
     * Parses a {@code Collection<String> medications} into an
     * {@code List<Medication>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medication} is invalid.
     */
    public static List<Medication> parseMedications(Collection<String> medications) throws ParseException {
        requireNonNull(medications);
        final List<Medication> medicationList = new ArrayList<>();
        for (String medication : medications) {
            medicationList.add(parseMedication(medication));
        }
        return medicationList;
    }
}
