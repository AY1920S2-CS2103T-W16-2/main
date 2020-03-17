package seedu.zerotoone.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.zerotoone.model.exercise.Exercise;

/**
 * A utility class containing a list of {@code Exercise} objects to be used in tests.
 */
public class TypicalExercises {

    public static final Exercise BENCH_PRESS = new ExerciseBuilder().withName("Bench Press")
            .withExerciseSet("30", "3", "180").build();
    public static final Exercise CRUNCHES = new ExerciseBuilder().withName("Crunches")
            .withExerciseSet("30", "3", "180").build();
    public static final Exercise DEADLIFT = new ExerciseBuilder().withName("Deadlift")
            .withExerciseSet("30", "5", "60").build();
    public static final Exercise SQUAT = new ExerciseBuilder().withName("Squat")
            .withExerciseSet("20", "6", "60").build();
    public static final Exercise LEG_PRESS = new ExerciseBuilder().withName("Leg Press")
            .withExerciseSet("15", "2", "120").build();
    public static final Exercise LEG_CURL = new ExerciseBuilder().withName("Leg Curl")
            .withExerciseSet("20", "4", "60").build();
    public static final Exercise HAMMER_CURL = new ExerciseBuilder().withName("Hammer Curl")
            .withExerciseSet("25", "4", "120").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExercises() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    // REMEMBER TO UNCOMMENT.
    // public static AddressBook getTypicalAddressBook() {
    //     AddressBook ab = new AddressBook();
    //     for (Exercise exercise : getTypicalExercises()) {
    //         ab.addExercise(exercise);
    //     }
    //     return ab;
    // }

    public static List<Exercise> getTypicalExercises() {
        return new ArrayList<>(Arrays.asList(
                BENCH_PRESS, CRUNCHES, DEADLIFT, SQUAT, LEG_PRESS, LEG_CURL, HAMMER_CURL));
    }
}
