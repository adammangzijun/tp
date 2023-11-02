package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.index.Index.fromZeroBased;
import static seedu.address.logic.Messages.MESSAGE_RECORDS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.RecordContainsKeywordsPredicate;

public class FindRecordCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // ViewCommand needs to be executed first for FindRecordCommand
    private ViewCommand viewCommand = new ViewCommand(fromZeroBased(3));
    private CommandResult cr = viewCommand.execute(model);

    public FindRecordCommandTest() throws CommandException {
    }

    @Test
    public void equals() {
        RecordContainsKeywordsPredicate firstPredicate =
                new RecordContainsKeywordsPredicate(Arrays.asList("Tylenol", "Pepto-Bismol"));
        RecordContainsKeywordsPredicate secondPredicate =
                new RecordContainsKeywordsPredicate(Arrays.asList("Tylenol"));

        FindRecordCommand findRecordCommand1 = new FindRecordCommand(firstPredicate);
        FindRecordCommand findRecordCommand2 = new FindRecordCommand(secondPredicate);
        assertTrue(findRecordCommand1.equals(findRecordCommand1));

        FindRecordCommand findRecordCommand1Copy = new FindRecordCommand(firstPredicate);
        assertTrue(findRecordCommand1.equals(findRecordCommand1Copy));
        assertFalse(findRecordCommand1.equals(findRecordCommand2));
        assertFalse(findRecordCommand1.equals(firstPredicate));
    }

    @Test
    public void execute_zeroKeywords_noPatientFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, 0);
        RecordContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindRecordCommand findRecordCommand = new FindRecordCommand(predicate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        viewCommand.execute(expectedModel);
        findRecordCommand.execute(expectedModel);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(findRecordCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleKeywords_singleRecordsFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_RECORDS_LISTED_OVERVIEW, 3);
        RecordContainsKeywordsPredicate predicate = preparePredicate("Tylenol");
        FindRecordCommand findRecordCommand = new FindRecordCommand(predicate);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        viewCommand.execute(expectedModel);
        findRecordCommand.execute(expectedModel);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(findRecordCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void toStringMethod() {
        RecordContainsKeywordsPredicate predicate = preparePredicate("Tylenol");
        FindRecordCommand command = new FindRecordCommand(predicate);
        String expected = FindRecordCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code RecordContainsKeywordsPredicate}.
     */
    private RecordContainsKeywordsPredicate preparePredicate(String userInput) {
        return new RecordContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
