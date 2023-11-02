package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditRecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Condition;
import seedu.address.model.record.Medication;

/**
 * Parses input arguments and creates a new EditRecordCommand object
 */
public class EditRecordCommandParser implements Parser<EditRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRecordCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_CONDITION, PREFIX_MEDICATION);

        Index patientIndex;
        Index recordIndex;

        try {
            String preamble = argMultimap.getPreamble();
            patientIndex = ParserUtil.parsePatientIndex(preamble);
            recordIndex = ParserUtil.parseRecordIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecordCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE);

        EditRecordCommand.EditRecordDescriptor editRecordDescriptor = new EditRecordCommand.EditRecordDescriptor();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editRecordDescriptor.setDateTime(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE).get()));
        }
        parseConditionsForEdit(argMultimap.getAllValues(PREFIX_CONDITION))
                .ifPresent(editRecordDescriptor::setConditions);

        parseMedicationForEdit(argMultimap.getAllValues(PREFIX_MEDICATION))
                .ifPresent(editRecordDescriptor::setMedications);

        if (!editRecordDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecordCommand(patientIndex, recordIndex, editRecordDescriptor);
    }

    /**
     * Parses {@code Collection<String> condtions} into a {@code Set<Condition>} if {@code conditions} is non-empty.
     * If {@code conditions} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Condition>} containing zero condition.
     */
    private Optional<List<Condition>> parseConditionsForEdit(Collection<String> conditions) throws ParseException {
        assert conditions != null;

        if (conditions.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> conditionSet = conditions.size() == 1
                && conditions.contains("") ? Collections.emptySet() : conditions;
        return Optional.of(ParserUtil.parseConditions(conditionSet));
    }
    /**
     * Parses {@code Collection<String> medications} into a {@code Set<Medication>} if {@code medications} is non-empty.
     * If {@code medications} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Medication>} containing zero medication.
     */
    private Optional<List<Medication>> parseMedicationForEdit(Collection<String> medications) throws ParseException {
        assert medications != null;
        if (medications.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> medicationSet = medications.size() == 1
                && medications.contains("") ? Collections.emptySet() : medications;
        return Optional.of(ParserUtil.parseMedications(medicationSet));
    }
}
