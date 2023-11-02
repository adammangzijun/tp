package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_SLEEP_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SLEEP_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_SLEEP_STUDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SLEEP_STUDY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.shared.DateTime;
import seedu.address.model.shared.Name;

public class AddAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddAppointmentCommand.MESSAGE_USAGE);
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_DESC_SLEEP_STUDY + DATETIME_DESC_SLEEP_STUDY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_SLEEP_STUDY + DATETIME_DESC_SLEEP_STUDY,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_SLEEP_STUDY + DATETIME_DESC_SLEEP_STUDY,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + DATETIME_DESC_SLEEP_STUDY,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + NAME_DESC_SLEEP_STUDY + INVALID_DATETIME_DESC,
                DateTime.MESSAGE_CONSTRAINTS); // invalid datetime

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATETIME_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PATIENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_SLEEP_STUDY + DATETIME_DESC_SLEEP_STUDY;

        Appointment appointment = new Appointment(new Name(VALID_NAME_SLEEP_STUDY),
                new DateTime(VALID_DATETIME_SLEEP_STUDY), null);

        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(targetIndex, appointment);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
