package seedu.zerotoone.model.session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

class OngoingSetTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        ExerciseSet base = new ExerciseSet(new Weight("5"), new NumReps("1"));
        Assertions.assertThrows(NullPointerException.class, () ->
                new OngoingSet(base, null, 1));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        ExerciseSet base = new ExerciseSet(new Weight("5"), new NumReps("1"));
        Assertions.assertThrows(NullPointerException.class, () ->
                new OngoingSet(base, new ExerciseName("Test"), null));
    }

    @Test
    public void constructor_nullExercise_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new OngoingSet(null, new ExerciseName("Test"), null));
    }

    @Test
    public void getWeight() {
        ExerciseSet base = new ExerciseSet(new Weight("5"), new NumReps("1"));
        OngoingSet setOneOne = new OngoingSet(base, new ExerciseName("Test"), 1);
        Weight expectedWeight = new Weight("5");
        Assertions.assertEquals(expectedWeight, setOneOne.getWeight());
    }

    @Test
    public void getNumReps() {
        ExerciseSet base = new ExerciseSet(new Weight("5"), new NumReps("1"));
        OngoingSet setOneOne = new OngoingSet(base, new ExerciseName("Test"), 1);
        NumReps expectedNumReps = new NumReps("1");
        Assertions.assertEquals(expectedNumReps, setOneOne.getNumReps());
    }

    @Test
    public void equals() {
        ExerciseSet base = new ExerciseSet(new Weight("1"), new NumReps("1"));
        OngoingSet setOneOne = new OngoingSet(base, new ExerciseName("Test"), 1);
        ExerciseSet baseTwo = new ExerciseSet(new Weight("1"), new NumReps("2"));
        OngoingSet setOneTwo = new OngoingSet(baseTwo, new ExerciseName("Test"), 1);
        OngoingSet setTwoOne = new OngoingSet(base, new ExerciseName("Test2"), 1);

        // same obj
        Assertions.assertEquals(setOneOne, setOneOne);

        // same value
        OngoingSet setOneOneCopy = new OngoingSet(base, new ExerciseName("Test"), 1);
        Assertions.assertEquals(setOneOne, setOneOneCopy);

        // null value
        Assertions.assertNotEquals(null, setOneOne);

        // invalid type
        Assertions.assertNotEquals(1, setOneOne);

        // different value -> returns false
        Assertions.assertNotEquals(setOneOne, setOneTwo);
        Assertions.assertNotEquals(setOneOne, setTwoOne);
    }

    @Test
    void testToString() {
        ExerciseSet base = new ExerciseSet(new Weight("5"), new NumReps("1"));
        OngoingSet setOneOne = new OngoingSet(base, new ExerciseName("Test"), 1);
        String expected = " Weight: 5 Number of repetitions: 1";
        Assertions.assertEquals(expected, setOneOne.toString());
    }
}
