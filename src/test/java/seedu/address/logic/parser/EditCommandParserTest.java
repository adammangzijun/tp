package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_DUST;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALLERGY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BLOODTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_DUST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Phone;
import seedu.address.model.shared.Name;
import seedu.address.testutil.EditPatientDescriptorBuilder;

public class EditCommandParserTest {

    private static final String ALLERGY_EMPTY = " " + PREFIX_ALLERGIES;

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_AGE_DESC, Age.MESSAGE_CONSTRAINTS); // invalid age
        assertParseFailure(parser, "1" + INVALID_BLOODTYPE_DESC, BloodType.MESSAGE_CONSTRAINTS); // invalid bloodType
        assertParseFailure(parser, "1" + INVALID_ALLERGY_DESC, Allergy.MESSAGE_CONSTRAINTS); // invalid allergy

        // invalid email followed by valid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC + VALID_PHONE_AMY, Email.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code
        // Patient} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + ALLERGY_DESC_DUST + ALLERGY_DESC_PEANUTS + ALLERGY_EMPTY,
                Allergy.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ALLERGY_DESC_DUST + ALLERGY_EMPTY + ALLERGY_DESC_PEANUTS,
                Allergy.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ALLERGY_EMPTY + ALLERGY_DESC_DUST + ALLERGY_DESC_PEANUTS,
                Allergy.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY
                + VALID_GENDER_AMY + VALID_AGE_AMY + VALID_BLOODTYPE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PATIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ALLERGY_DESC_DUST
                + EMAIL_DESC_AMY + GENDER_DESC_AMY + NAME_DESC_AMY + ALLERGY_DESC_PEANUTS
                + AGE_DESC_AMY + BLOODTYPE_DESC_AMY;

        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY).withPhone(VALID_PHONE_AMY).withGender(VALID_GENDER_AMY)
                .withAge(VALID_AGE_AMY).withBloodType(VALID_BLOODTYPE_AMY)
                .withAllergies(VALID_ALLERGY_DUST, VALID_ALLERGY_PEANUTS).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY + PHONE_DESC_AMY;

        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withEmail(VALID_EMAIL_AMY)
                .withPhone(VALID_PHONE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PATIENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // age
        userInput = targetIndex.getOneBased() + AGE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withAge(VALID_AGE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // bloodType
        userInput = targetIndex.getOneBased() + BLOODTYPE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withBloodType(VALID_BLOODTYPE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + ALLERGY_DESC_DUST;
        descriptor = new EditPatientDescriptorBuilder().withAllergies(VALID_ALLERGY_DUST).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + GENDER_DESC_AMY + EMAIL_DESC_AMY
                + ALLERGY_DESC_DUST + PHONE_DESC_AMY + GENDER_DESC_AMY + EMAIL_DESC_AMY
                + ALLERGY_DESC_DUST
                + PHONE_DESC_BOB + GENDER_DESC_AMY + EMAIL_DESC_BOB + ALLERGY_DESC_PEANUTS;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_GENDER));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_GENDER_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_GENDER_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_GENDER));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PATIENT;
        String userInput = targetIndex.getOneBased() + ALLERGY_EMPTY;

        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withAllergies().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
