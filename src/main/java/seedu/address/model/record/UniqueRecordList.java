package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.record.exceptions.DuplicateRecordException;
import seedu.address.model.record.exceptions.RecordNotFoundException;

/**
 * A list of records that enforces uniqueness between its elements and does not
 * allow nulls.
 * A record is considered unique by comparing using
 * {@code Record#equals(Object)}. As such, adding, updating, removal of
 * records uses Record#equals(Object) for equality to ensure that the
 * record being added, updated, removed is
 * unique in terms of identity in the UniqueRecordList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Record#equals(Object)
 */
public class UniqueRecordList implements Iterable<Record> {
    private final ObservableList<Record> internalList = FXCollections.observableArrayList();
    private final ObservableList<Record> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent record as the given argument.
     */
    public boolean contains(Record toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns a record at a specified index.
     */
    public Record get(int index) {
        return internalList.get(index);
    }

    /**
     * Returns the size of the internalList
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Adds a record to the list
     */
    public void add(Record recordToAdd) {
        requireNonNull(recordToAdd);
        if (contains(recordToAdd)) {
            throw new DuplicateRecordException();
        }
        internalList.add(recordToAdd);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the list.
     * The record identity of {@code editedRecord} must not be the same as another
     * existing record in the list.
     */

    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecordNotFoundException();
        }

        if (!target.equals(editedRecord) && contains(editedRecord)) {
            throw new DuplicateRecordException();
        }

        internalList.set(index, editedRecord);
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(Record recordToRemove) {
        requireNonNull(recordToRemove);
        if (!internalList.remove(recordToRemove)) {
            throw new RecordNotFoundException();
        }
    }

    public void setRecords(UniqueRecordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setRecords(List<Record> records) {
        requireAllNonNull(records);
        if (!recordsAreUnique(records)) {
            throw new DuplicateRecordException();
        }

        internalList.setAll(records);
    }

    public ObservableList<Record> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Record> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueRecordList)) {
            return false;
        }

        UniqueRecordList otherUniqueRecordList = (UniqueRecordList) other;
        return internalList.equals(otherUniqueRecordList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code records} contains only unique records.
     */
    private boolean recordsAreUnique(List<Record> records) {
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {
                if (records.get(i).equals(records.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
