package seedu.address.testutil;

import seedu.address.model.record.Record;

/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {
    public static final Record FEVER0 = new RecordBuilder()
            .withDateTime("09-10-2023 1800")
            .withConditions("Fever")
            .withPatientIndex(0)
            .withMedications("Tylenol")
            .build();

    public static final Record FEVER_AND_COLD0 = new RecordBuilder()
            .withDateTime("09-09-2023 1800")
            .withConditions("Fever", "Cold")
            .withPatientIndex(0)
            .withMedications("Tylenol")
            .build();

    public static final Record FEVER_AND_COLD1 = new RecordBuilder()
            .withDateTime("09-09-2023 1800")
            .withConditions("Fever", "Cold")
            .withMedications("Tylenol")
            .withPatientIndex(1)
            .build();

    public static final Record ALLERGIC_REACTION2 = new RecordBuilder()
            .withDateTime("23-10-2022 1130")
            .withConditions("Allergic Reaction")
            .withPatientIndex(2)
            .withMedications("Pepto-Bismol")
            .build();

    public static final Record FEVER_AND_COLD2 = new RecordBuilder()
            .withDateTime("09-09-2023 1800")
            .withConditions("Fever", "Cold")
            .withPatientIndex(2)
            .withMedications("Pepto-Bismol")
            .build();

    public static final Record HEADACHE3 = new RecordBuilder()
            .withDateTime("24-12-2023 1200")
            .withConditions("Headache")
            .withPatientIndex(3)
            .withMedications("Tylenol")
            .build();

}
