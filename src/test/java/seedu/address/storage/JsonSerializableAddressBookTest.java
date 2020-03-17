package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalExercises;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_EXERCISES_FILE = TEST_DATA_FOLDER.resolve("typicalExercisesAddressBook.json");
    private static final Path INVALID_EXERCISE_FILE = TEST_DATA_FOLDER.resolve("invalidExerciseAddressBook.json");
    private static final Path DUPLICATE_EXERCISE_FILE = TEST_DATA_FOLDER.resolve("duplicateExerciseAddressBook.json");

    @Test
    public void toModelType_typicalExercisesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXERCISES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalExercisesAddressBook = TypicalExercises.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalExercisesAddressBook);
    }

    @Test
    public void toModelType_invalidExerciseFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_EXERCISE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateExercises_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXERCISE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_EXERCISE,
                dataFromFile::toModelType);
    }

}
