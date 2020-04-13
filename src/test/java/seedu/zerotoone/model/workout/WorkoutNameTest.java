package seedu.zerotoone.model.workout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WorkoutNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WorkoutName(null));
    }

    @Test
    public void constructor_invalidWorkoutName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new WorkoutName(""));
    }

    @Test
    public void isValidWorkoutName() {
        // null Workout Name
        assertThrows(NullPointerException.class, () -> WorkoutName.isValidWorkoutName(null));

        // invalid Workout Name
        assertFalse(WorkoutName.isValidWorkoutName("")); // empty string
        assertFalse(WorkoutName.isValidWorkoutName("^")); // only non-alphanumeric characters
        assertFalse(WorkoutName.isValidWorkoutName("strength*")); // contains non-alphanumeric characters

        // valid Workout Name
        assertTrue(WorkoutName.isValidWorkoutName("strength training")); // alphabets only
        assertTrue(WorkoutName.isValidWorkoutName("2 times daily workout")); // alphanumeric characters
        assertTrue(WorkoutName.isValidWorkoutName("Abs Training")); // with capital letters
        assertTrue(WorkoutName.isValidWorkoutName("Push and pull workout plan")); // long names
    }
}
