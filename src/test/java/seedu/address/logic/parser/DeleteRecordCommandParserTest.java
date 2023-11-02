package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteRecordCommand;

public class DeleteRecordCommandParserTest {

    private DeleteRecordCommandParser parser = new DeleteRecordCommandParser();
    @Test
    public void parse_invalidUserInput_failure() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecordCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "1/1", new DeleteRecordCommand(INDEX_FIRST_PATIENT, INDEX_FIRST_RECORD));
    }
}
