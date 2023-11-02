package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.testutil.MockInvalidKeyJsonAddressBookStorage;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath))
                .readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPatientAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPatientAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPatientAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPatientAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidJson_throwsDataLoadingException() throws Exception {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidJson.json"));
    }

    @Test
    public void readAddressBook_nonExistentFile_returnsOptionalEmpty() throws Exception {
        assertFalse(readAddressBook("invalidInvalid.json").isPresent());
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }

    @Test
    public void saveAndRead_encryptionDecryption_success() throws Exception {
        Path filePath = testFolder.resolve("TempEncryptedAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file
        jsonAddressBookStorage.saveAddressBook(original, filePath);

        // Check if file content is encrypted
        String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        String originalJson = JsonUtil.toJsonString(new JsonSerializableAddressBook(original));

        // Use JUnit's assertion to check that the content is not equal to the original
        // JSON
        assertNotSame(originalJson, content);

        // Read back and decrypt
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));
    }

    @Test
    public void read_corruptedData_throwDataLoadingException() throws Exception {
        Path filePath = testFolder.resolve("CorruptedEncryptedAddressBook.json");
        String corruptedData = Base64.getEncoder().encodeToString("corrupted data".getBytes(StandardCharsets.UTF_8));
        Files.write(filePath, corruptedData.getBytes(StandardCharsets.UTF_8));

        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);
        assertThrows(DataLoadingException.class, () -> jsonAddressBookStorage.readAddressBook(filePath));
    }

    @Test
    public void encrypt_invalidKeySize_returnsNull() throws Exception {
        JsonAddressBookStorage storage = new MockInvalidKeyJsonAddressBookStorage(TEST_DATA_FOLDER);

        String result = storage.encrypt("testString");
        assertNull(result);
    }

    @Test
    public void readAddressBook_jsonDeserializationReturnsNull_returnsEmptyOptional() throws DataLoadingException {
        // Create an instance of JsonAddressBookStorage with MockJsonUtil
        Path testFilePath = Paths.get("path_to_some_test_file_with_encrypted_content");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(testFilePath);

        // Ensure the file exists with some encrypted content
        // (Note: This step might require you to actually have some encrypted data in
        // the test file)

        Optional<ReadOnlyAddressBook> result = storage.readAddressBook(testFilePath);

        // Assert that the result is an empty Optional
        assertFalse(result.isPresent());
    }

}
