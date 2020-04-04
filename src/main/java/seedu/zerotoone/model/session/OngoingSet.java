package seedu.zerotoone.model.session;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

/**
 * Represents a Exercise Set in the exercise list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OngoingSet {
    /*
     * The first character of the exercise set must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final ExerciseSet set;
    public final ExerciseName exerciseName;
    public final int index;

    /**
     * Every field must be present and not null.
     */
    public OngoingSet(ExerciseSet set, ExerciseName name, int index) {
        requireAllNonNull(set, name);
        this.set = set;
        this.exerciseName = name;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public Weight getWeight() {
        return set.weight;
    }

    public NumReps getNumReps() {
        return set.numReps;
    }

    public ExerciseName getExerciseName() {
        return this.exerciseName;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Weight: ")
                .append(getWeight())
                .append(" Number of repetitions: ")
                .append(getNumReps());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OngoingSet)) {
            return false;
        }

        OngoingSet otherExerciseSet = (OngoingSet) other;
        return otherExerciseSet.getWeight().equals(getWeight())
                && otherExerciseSet.getNumReps().equals(getNumReps());
    }

    @Override
    public int hashCode() {
        return Objects.hash(set, exerciseName);
    }

}
