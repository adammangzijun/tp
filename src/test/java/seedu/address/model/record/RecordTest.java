package seedu.address.model.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.shared.DateTime;
import seedu.address.model.shared.Name;

public class RecordTest {

    private List<Condition> validConditions = new ArrayList<>(Arrays.asList(new Condition("Fever"),
            new Condition("Cold")));
    private List<Medication> validMedications = new ArrayList<>(Arrays.asList(new Medication("Tylenol"),
            new Medication("Ibuprofen")));
    private Record record = new Record(new DateTime("09-10-2023 1800"), validConditions, validMedications,
            null, 1);
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Record(null, null, null, null, null));
        assertThrows(NullPointerException.class, () ->
            new Record(new DateTime("09-10-2023 1800"), null, null, null, null));
        List<Condition> validConditions = new ArrayList<>(Arrays.asList(new Condition("Fever"), new Condition("Cold")));
        assertThrows(NullPointerException.class, () -> new Record(null, validConditions, null, null, null));
    }
    @Test
    public void equals() {
        assertTrue(record.equals(record));
        Record anotherRecord = new Record(new DateTime("09-10-2023 1800"), validConditions, validMedications, null, 1);
        assertTrue(record.equals(anotherRecord));
        assertFalse(record.equals(new Name("Alice Pauline")));
        Record anotherRecord2 = new Record(new DateTime("10-10-2023 1800"), validConditions, validMedications, null, 1);
        assertFalse(record.equals(anotherRecord2));
    }

    @Test
    public void toString_success() {
        String expected = Record.class.getCanonicalName()
                + "{dateTime=" + record.getDateTime()
                + ", conditions=" + record.getConditions()
                + ", medications=" + record.getMedications()
                + ", filePath=" + record.getFilePath()
                + ", personIndex=" + record.getPatientIndex()
                + "}";

        assertEquals(expected, record.toString());
    }
}
