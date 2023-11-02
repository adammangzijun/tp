package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_DUST;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALLERGY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BLOODTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_DUST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.patient.*;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;
import seedu.address.testutil.PatientBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BOB).withAllergies(VALID_ALLERGY_DUST).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + NRIC_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + BLOODTYPE_DESC_BOB
                + ALLERGY_DESC_DUST,
                new AddCommand(expectedPatient));

        // multiple tags - all accepted
        Patient expectedPatientMultipleTags = new PatientBuilder(BOB).withAllergies(VALID_ALLERGY_DUST,
                VALID_ALLERGY_PEANUTS)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                        + AGE_DESC_BOB + BLOODTYPE_DESC_BOB + ALLERGY_DESC_DUST
                        + ALLERGY_DESC_PEANUTS,
                new AddCommand(expectedPatientMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + BLOODTYPE_DESC_BOB + ALLERGY_DESC_DUST;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple genders
        assertParseFailure(parser, GENDER_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // multiple ages
        assertParseFailure(parser, AGE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // multiple bloodTypes
        assertParseFailure(parser, BLOODTYPE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + NAME_DESC_AMY + PREFIX_GENDER + PREFIX_AGE + PREFIX_BLOODTYPE
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_NRIC, PREFIX_EMAIL,
                        PREFIX_PHONE,
                        PREFIX_GENDER, PREFIX_AGE, PREFIX_BLOODTYPE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid gender
        assertParseFailure(parser, INVALID_GENDER_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid age
        assertParseFailure(parser, INVALID_AGE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // invalid bloodType
        assertParseFailure(parser, INVALID_BLOODTYPE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid gender
        assertParseFailure(parser, validExpectedPersonString + INVALID_GENDER_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GENDER));

        // invalid age
        assertParseFailure(parser, validExpectedPersonString + INVALID_AGE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AGE));

        // invalid bloodtype
        assertParseFailure(parser, validExpectedPersonString + INVALID_BLOODTYPE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Patient expectedPatient = new PatientBuilder(AMY).withAllergies().build();
        assertParseSuccess(parser, NAME_DESC_AMY + NRIC_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_AMY
                + GENDER_DESC_AMY + AGE_DESC_AMY + BLOODTYPE_DESC_AMY,
                new AddCommand(expectedPatient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + VALID_PHONE_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_EMAIL_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + VALID_GENDER_BOB + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                expectedMessage);

        // missing age prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_EMAIL_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + VALID_AGE_BOB + BLOODTYPE_DESC_BOB,
                expectedMessage);

        // missing bloodType prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_EMAIL_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + VALID_AGE_BOB + VALID_BLOODTYPE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_EMAIL_BOB + VALID_PHONE_BOB
                + VALID_GENDER_BOB + VALID_AGE_BOB + VALID_BLOODTYPE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                        + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_NRIC_DESC + EMAIL_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                        + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                Nric.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + INVALID_PHONE_DESC + GENDER_DESC_BOB
                        + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + INVALID_EMAIL_DESC + PHONE_DESC_BOB + GENDER_DESC_BOB
                        + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB + INVALID_GENDER_DESC
                        + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                Gender.MESSAGE_CONSTRAINTS);

        // invalid age
        assertParseFailure(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                        + INVALID_AGE_DESC + BLOODTYPE_DESC_BOB,
                Age.MESSAGE_CONSTRAINTS);

        // invalid bloodType
        assertParseFailure(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                        + AGE_DESC_BOB + INVALID_BLOODTYPE_DESC,
                BloodType.MESSAGE_CONSTRAINTS);

        // invalid Allergy
        assertParseFailure(parser,
                NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                        + AGE_DESC_BOB + BLOODTYPE_DESC_BOB + INVALID_ALLERGY_DESC,
                Allergy.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + INVALID_GENDER_DESC + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_BOB + NRIC_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                        + GENDER_DESC_BOB + AGE_DESC_BOB + BLOODTYPE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
