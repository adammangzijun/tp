package seedu.address.model.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecords.ALLERGIC_REACTION2;
import static seedu.address.testutil.TypicalRecords.FEVER0;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.patient.UniquePatientList;
import seedu.address.model.record.exceptions.DuplicateRecordException;
import seedu.address.model.record.exceptions.RecordNotFoundException;

public class UniqueRecordListTest {
    private final UniqueRecordList uniqueRecordList = new UniqueRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecordList.contains(null));
    }

    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueRecordList.contains(FEVER0));
    }

    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecordList.add(FEVER0);
        assertTrue(uniqueRecordList.contains(FEVER0));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecordList.add(null));
    }

    @Test
    public void add_duplicateRecord_throwsDuplicateRecordException() {
        uniqueRecordList.add(FEVER0);
        assertThrows(DuplicateRecordException.class, () -> uniqueRecordList.add(FEVER0));
    }

    @Test
    public void setPatient_nullTargetRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecordList.setRecord(null, FEVER0));
    }

    @Test
    public void setPatient_nullEditedRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecordList.setRecord(FEVER0, null));
    }

    @Test
    public void setPatient_targetPatientNotInList_throwsRecordNotFoundException() {
        assertThrows(RecordNotFoundException.class, () -> uniqueRecordList.setRecord(FEVER0, FEVER0));
    }

    @Test
    public void setRecord_editedRecordIsSameRecord_success() {
        uniqueRecordList.add(FEVER0);
        uniqueRecordList.setRecord(FEVER0, FEVER0);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(FEVER0);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecord_editedRecordIsDifferentRecord_success() {
        uniqueRecordList.add(FEVER0);
        uniqueRecordList.setRecord(FEVER0, ALLERGIC_REACTION2);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(ALLERGIC_REACTION2);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setPatient_editedRecordIsTheSame_throwsDuplicateRecordException() {
        uniqueRecordList.add(FEVER0);
        uniqueRecordList.add(ALLERGIC_REACTION2);
        assertThrows(DuplicateRecordException.class, () -> uniqueRecordList.setRecord(FEVER0, ALLERGIC_REACTION2));
    }

    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecordList.remove(null));
    }

    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        assertThrows(RecordNotFoundException.class, () -> uniqueRecordList.remove(FEVER0));
    }

    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecordList.add(FEVER0);
        uniqueRecordList.remove(FEVER0);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullUniqueRecordList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecordList.setRecords((UniqueRecordList) null));
    }

    @Test
    public void setRecords_uniqueRecordList_replacesOwnListWithProvidedUniqueRecordList() {
        uniqueRecordList.add(FEVER0);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(ALLERGIC_REACTION2);
        uniqueRecordList.setRecords(expectedUniqueRecordList);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecordList.setRecords((List<Record>) null));
    }

    @Test
    public void setPatients_list_replacesOwnListWithProvidedList() {
        uniqueRecordList.add(FEVER0);
        List<Record> recordList = Collections.singletonList(FEVER0);
        uniqueRecordList.setRecords(recordList);
        UniqueRecordList expectedUniqueRecordList = new UniqueRecordList();
        expectedUniqueRecordList.add(FEVER0);
        assertEquals(expectedUniqueRecordList, uniqueRecordList);
    }

    @Test
    public void setRecords_listWithDuplicateRecords_throwsDuplicateRecordException() {
        List<Record> listWithDuplicateRecords = Arrays.asList(FEVER0, FEVER0);
        assertThrows(DuplicateRecordException.class, () -> uniqueRecordList.setRecords(listWithDuplicateRecords));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueRecordList
            .asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueRecordList.asUnmodifiableObservableList().toString(), uniqueRecordList.toString());
    }
    @Test
    public void equals() {
        assertTrue(uniqueRecordList.equals(uniqueRecordList));
        assertFalse(uniqueRecordList.equals(new UniquePatientList()));
    }

}
