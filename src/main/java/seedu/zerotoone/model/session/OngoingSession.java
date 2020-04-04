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
 * Represents a single Session.
 */
public class OngoingSession {

    // Identity fields
    private final LocalDateTime startTime;
    private final ExerciseName exerciseName;
    private final Queue<ExerciseSet> exerciseQueue = new LinkedList<>();
    private final Queue<SessionSet> exerciseDone = new LinkedList<>();

    /**
     * Every field must be present and not null.
     */
    public OngoingSession(Exercise exercise, LocalDateTime startTime) {
        requireAllNonNull(exercise);
        this.exerciseName = exercise.getExerciseName();
        this.exerciseQueue.addAll(exercise.getExerciseSets());
        this.startTime = startTime;
    }

    public ExerciseName getExerciseName() {
        return this.exerciseName;
    }

    /**
     * Completes the top exercise that is left in the exerciseQueue and puts it into the done list.
     * @return set: the done SessionSet
     */
    public SessionSet done() {
        SessionSet set = new SessionSet(exerciseQueue.poll(), true);
        exerciseDone.offer(set);
        return set;
    }

    /**
     * Skips the top exercise that is left in the exerciseQueue and puts it into the done list.
     * @return set: the skipped SessionSet
     */
    public SessionSet skip() {
        SessionSet set = new SessionSet(exerciseQueue.poll(), false);
        exerciseDone.offer(set);
        return set;
    }

    /**
     *  Returns true if there are still sets remaining in the queue.
     */
    public boolean hasSetLeft() {
        return !exerciseQueue.isEmpty();
    }

    public Optional<ExerciseSet> peek() {
        return Optional.ofNullable(exerciseQueue.peek());
    }

    public Optional<SessionSet> last() {
        return Optional.ofNullable(exerciseDone.peek());
    }

    public List<ExerciseSet> getRemaining() {
        return Collections.unmodifiableList(new LinkedList<>(this.exerciseQueue));
    }

    /**
     * Ends a Session (prematurely if queue is still filled) with a endTime, and labelling
     * incomplete sets as unfinished.
     * @param endTime the time of completion
     * @return returns a new immutable CompletedSession.
     */
    public Session finish(LocalDateTime endTime) {
        while (this.hasSetLeft()) {
            exerciseDone.offer(new SessionSet(exerciseQueue.poll(), false));
        }
        return new Session(this.exerciseName, new LinkedList<>(exerciseDone),
                startTime, endTime);
    }
}
