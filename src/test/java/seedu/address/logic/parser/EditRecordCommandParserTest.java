package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONDITION_DESC_DIARRHEA;
import static seedu.address.logic.commands.CommandTestUtil.CONDITION_DESC_HEAT_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_SLEEP_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_THYROID_CHECK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONDITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDICATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEDICATION_DESC_DIARRHEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_DIARRHEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_HEAT_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_SLEEP_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICATION_DIARRHEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICATION_HEAT_STROKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditRecordCommand;
import seedu.address.model.record.Condition;
import seedu.address.model.shared.DateTime;
import seedu.address.testutil.EditRecordDescriptorBuilder;


public class EditRecordCommandParserTest {
    private static final String CONDITION_EMPTY = " " + PREFIX_CONDITION;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRecordCommand.MESSAGE_USAGE);

    private EditRecordCommandParser parser = new EditRecordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DATETIME_SLEEP_STUDY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1/1", EditRecordCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5/1" + DATETIME_DESC_SLEEP_STUDY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0/1" + DATETIME_DESC_THYROID_CHECK, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1/1" + INVALID_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS); // invalid dateTime
        assertParseFailure(parser, "1/1" + INVALID_CONDITION_DESC, Condition.MESSAGE_CONSTRAINTS); // invalid condition

        // invalid dateTime followed by valid condition
        assertParseFailure(parser, "1/1" + INVALID_DATETIME_DESC
                + VALID_CONDITION_HEAT_STROKE + VALID_MEDICATION_HEAT_STROKE, DateTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Patient} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1/1" + CONDITION_DESC_HEAT_STROKE + CONDITION_DESC_DIARRHEA + CONDITION_EMPTY,
                Condition.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1/1" + CONDITION_DESC_HEAT_STROKE + CONDITION_EMPTY + CONDITION_DESC_DIARRHEA,
                Condition.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1/1" + CONDITION_EMPTY + CONDITION_DESC_HEAT_STROKE + CONDITION_DESC_DIARRHEA,
                Condition.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1/1" + INVALID_DATETIME_DESC + INVALID_CONDITION_DESC + INVALID_MEDICATION_DESC,
                DateTime.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index patientIndex = INDEX_FIRST_PATIENT;
        Index recordIndex = INDEX_FIRST_RECORD;
        String userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased()
                + DATETIME_DESC_SLEEP_STUDY + CONDITION_DESC_DIARRHEA + MEDICATION_DESC_DIARRHEA;

        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withDateTime(VALID_DATETIME_SLEEP_STUDY)
                .withConditions(VALID_CONDITION_DIARRHEA)
                .withMedications(VALID_MEDICATION_DIARRHEA).build();
        EditRecordCommand expectedCommand = new EditRecordCommand(patientIndex, recordIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index patientIndex = INDEX_FIRST_PATIENT;
        Index recordIndex = INDEX_FIRST_RECORD;
        String userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased() + DATETIME_DESC_SLEEP_STUDY;

        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withDateTime(VALID_DATETIME_SLEEP_STUDY).build();
        EditRecordCommand expectedCommand = new EditRecordCommand(patientIndex, recordIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // dateTime
        Index patientIndex = INDEX_FIRST_PATIENT;
        Index recordIndex = INDEX_FIRST_RECORD;
        String userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased() + DATETIME_DESC_SLEEP_STUDY;
        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withDateTime(VALID_DATETIME_SLEEP_STUDY).build();
        EditRecordCommand expectedCommand = new EditRecordCommand(patientIndex, recordIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // condition
        userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased() + CONDITION_DESC_DIARRHEA;
        descriptor = new EditRecordDescriptorBuilder().withConditions(VALID_CONDITION_DIARRHEA).build();
        expectedCommand = new EditRecordCommand(patientIndex, recordIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        Index patientIndex = INDEX_FIRST_PATIENT;
        Index recordIndex = INDEX_FIRST_RECORD;
        String userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased()
                + INVALID_DATETIME_DESC + DATETIME_DESC_THYROID_CHECK;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid followed by valid
        userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased()
                + DATETIME_DESC_THYROID_CHECK + INVALID_DATETIME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // mulltiple valid fields repeated
        userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased()
                + DATETIME_DESC_THYROID_CHECK + DATETIME_DESC_SLEEP_STUDY + CONDITION_DESC_DIARRHEA
                + CONDITION_DESC_HEAT_STROKE;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple invalid values
        userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased()
                + INVALID_DATETIME_DESC + INVALID_DATETIME_DESC + INVALID_CONDITION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_resetConditions_success() {
        Index patientIndex = INDEX_FIRST_PATIENT;
        Index recordIndex = INDEX_FIRST_RECORD;
        String userInput = patientIndex.getOneBased() + "/" + recordIndex.getOneBased() + CONDITION_EMPTY;

        EditRecordCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withConditions().build();
        EditRecordCommand expectedCommand = new EditRecordCommand(patientIndex, recordIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
