package seedu.zerotoone.model.session;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

class OngoingSessionTest {
    private ExerciseSet baseSet = new ExerciseSet(new Weight("5"), new NumReps("1"));
    private OngoingSet baseOngoingSet = new OngoingSet(baseSet, new ExerciseName("Test"), 1);
    private List<ExerciseSet> listSet = Collections.singletonList(baseSet);
    private Exercise base = new Exercise(new ExerciseName("Test"), listSet);
    private LocalDateTime testDateTime = LocalDateTime.of(2015,
            Month.JULY, 29, 19, 30, 40);

    @Test
    public void constructor_nullExercise_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new OngoingSession(null, testDateTime));
    }

    @Test
    public void getExerciseName() {
        OngoingSession ongoingSession = new OngoingSession(base, testDateTime);
        ExerciseName expectedName = new ExerciseName("Test");
        Assertions.assertEquals(expectedName, ongoingSession.getExerciseName());
    }

    @Test
    public void done() {
        OngoingSession ongoingSession = new OngoingSession(base, testDateTime);
        CompletedSet expectedSet = new CompletedSet(baseOngoingSet, true);
        Assertions.assertEquals(expectedSet, ongoingSession.done());
    }

    @Test
    public void skip() {
        OngoingSession ongoingSession = new OngoingSession(base, testDateTime);
        CompletedSet expectedSet = new CompletedSet(baseOngoingSet, false);
        Assertions.assertEquals(expectedSet, ongoingSession.skip());
    }

    @Test
    public void equals() {
        OngoingSession ongoingSession = new OngoingSession(base, testDateTime);
        LocalDateTime testDateTimeTwo = LocalDateTime.of(2015,
                Month.JULY, 29, 11, 30, 40);
        OngoingSession ongoingSessionTwo = new OngoingSession(base, testDateTimeTwo);

        // same obj
        Assertions.assertEquals(ongoingSession, ongoingSession);

        // same value
        OngoingSession ongoingSessionCopy = new OngoingSession(base, testDateTime);
        Assertions.assertEquals(ongoingSession, ongoingSessionCopy);

        // null value
        Assertions.assertNotEquals(null, ongoingSession);

        // invalid type
        Assertions.assertNotEquals(1, ongoingSession);

        // different value -> returns false
        Assertions.assertNotEquals(ongoingSession, ongoingSessionTwo);
    }

    @Test
    void testToString() {
        OngoingSession ongoingSession = new OngoingSession(base, testDateTime);
        String expected = "Test" + " " + testDateTime.toString();
        Assertions.assertEquals(expected, ongoingSession.toString());
    }
}
