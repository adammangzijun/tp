package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalRecords.FEVER0;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRecordCommand;

public class AddRecordCommandParserTest {
    private AddRecordCommandParser parser = new AddRecordCommandParser();

    @Test
    public void parse_validArgs_returnsAddRecordCommand() {
        assertParseSuccess(parser, "1 d/09-10-2023 1800 c/Fever m/Tylenol",
                new AddRecordCommand(INDEX_FIRST_PATIENT, FEVER0));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRecordCommand.MESSAGE_USAGE));
    }
}
