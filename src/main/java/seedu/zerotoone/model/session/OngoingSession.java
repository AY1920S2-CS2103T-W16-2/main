//@@author gb3h
package seedu.zerotoone.model.session;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;

/**
 * Represents a single Ongoing Session.
 */
public class OngoingSession {

    // Identity fields
    private final LocalDateTime startTime;
    private final ExerciseName exerciseName;
    private final Queue<OngoingSet> exerciseQueue = new LinkedList<>();
    private final Queue<CompletedSet> exerciseDone = new LinkedList<>();

    /**
     * Exercise field must be non null.
     */
    public OngoingSession(Exercise exercise, LocalDateTime startTime) {
        requireAllNonNull(exercise, startTime);
        this.exerciseName = exercise.getExerciseName();
        int i = 0;
        for (ExerciseSet s: exercise.getExerciseSets()) {
            this.exerciseQueue.add(new OngoingSet(s, exerciseName, i));
            i++;
        }
        this.startTime = startTime;
    }

    public ExerciseName getExerciseName() {
        return this.exerciseName;
    }

    /**
     * Completes the top exercise that is left in the exerciseQueue and puts it into the done list.
     * @return set: the done CompletedSet
     */
    public CompletedSet done() {
        OngoingSet ongoingSet = exerciseQueue.poll();
        CompletedSet set = new CompletedSet(ongoingSet, true);
        exerciseDone.offer(set);
        return set;
    }

    /**
     * Skips the top exercise that is left in the exerciseQueue and puts it into the done list.
     * @return set: the skipped CompletedSet
     */
    public CompletedSet skip() {
        OngoingSet ongoingSet = exerciseQueue.poll();
        CompletedSet set = new CompletedSet(ongoingSet, false);
        exerciseDone.offer(set);
        return set;
    }

    /**
     *  Returns true if there are still sets remaining in the queue.
     */
    public boolean hasSetLeft() {
        return !exerciseQueue.isEmpty();
    }

    public Optional<OngoingSet> peek() {
        return Optional.ofNullable(exerciseQueue.peek());
    }

    public Optional<CompletedSet> last() {
        return Optional.ofNullable(exerciseDone.peek());
    }

    public List<OngoingSet> getRemaining() {
        return Collections.unmodifiableList(new LinkedList<>(this.exerciseQueue));
    }

    /**
     * Ends a Session (prematurely if queue is still filled) with a endTime, and labelling
     * incomplete sets as unfinished.
     * @param endTime the time of completion
     * @return returns a new immutable CompletedExercise.
     */
    public CompletedExercise finish(LocalDateTime endTime) {
        while (this.hasSetLeft()) {
            exerciseDone.offer(new CompletedSet(exerciseQueue.poll(), false));
        }
        return new CompletedExercise(this.exerciseName, new LinkedList<>(exerciseDone),
                startTime, endTime);
    }

    /**
     * Returns true if both OngoingSessions have the same identity and data fields.
     * This defines a stronger notion of equality between two OngoingSessions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OngoingSession)) {
            return false;
        }

        OngoingSession otherOngoingSession = (OngoingSession) other;
        return otherOngoingSession.getExerciseName().equals(getExerciseName())
                && otherOngoingSession.getRemaining().equals(getRemaining())
                && otherOngoingSession.getDateTime().equals(getDateTime());
    }

    private LocalDateTime getDateTime() {
        return this.startTime;
    }

    @Override
    public String toString() {
        return this.getExerciseName().fullName + " " + this.startTime.toString();
    }
}
