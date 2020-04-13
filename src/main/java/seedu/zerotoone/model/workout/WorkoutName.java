package seedu.zerotoone.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;

/**
 * Represents a Workout's Name in the workout list.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class WorkoutName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the workout name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "(?!^[0-9]+$)^[A-Za-z0-9 ]+$";

    public final String fullName;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public WorkoutName(String name) {
        logger.info("Creating workout name");

        requireNonNull(name);
        checkArgument(isValidWorkoutName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidWorkoutName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutName // instanceof handles nulls
                && fullName.equals(((WorkoutName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
