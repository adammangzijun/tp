package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.RecordContainsKeywordsPredicate;


/**
 * Finds and lists all records of a patient in address book that contain any of the argument keywords.
 * Keyword matching is not case-sensitive.
 */
public class FindRecordCommand extends Command {
    public static final String COMMAND_WORD = "searchrecord";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all records of a patient that contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + "Tylenol";
    private final RecordContainsKeywordsPredicate predicate;
    public FindRecordCommand(RecordContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RECORDS_LISTED_OVERVIEW, model.getFilteredRecordList().size())
        );
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof FindRecordCommand)) {
            return false;
        }

        FindRecordCommand otherFindRecordCommand = (FindRecordCommand) other;
        return this.predicate.equals(otherFindRecordCommand.predicate);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
